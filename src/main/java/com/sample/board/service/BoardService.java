package com.sample.board.service;

import com.sample.board.enums.ApiStatusCode;
import com.sample.board.model.api.AuthToken;
import com.sample.board.model.api.Result;
import com.sample.board.model.board.BoardReq.*;
import com.sample.board.model.board.BoardRes;
import com.sample.board.mysql.mapper.board.BoardMapper;
import com.sample.board.mysql.model.board.BlockWord;
import com.sample.board.mysql.model.board.Board;
import com.sample.board.util.CacheModule;
import com.sample.board.util.CommonModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Description : Sample Board Service
 * Version : V1.0
 * Author : Demian.khj
 * Create Date : 2020-02-24
 * Update : None
 */
@Service
public class BoardService {
    // region Autowired Service
    BoardMapper boardMapper;
    CacheModule cacheModule;

    @Autowired
    public BoardService(BoardMapper boardMapper,
                        CacheModule cacheModule) {
        this.boardMapper = boardMapper;
        this.cacheModule = cacheModule;
    }

    /**
     * Get Board List
     * @param boardListRequest
     * @return API Result with Board List
     */
    public Result listBoardContent(BoardListRequest boardListRequest) {
        Integer        _pageOffset;
        Long           _totalCount;
        List<BoardRes> _boardList;
        Result         _result     = new Result();

        // Get Board Total Count
        _totalCount = boardMapper.getBoardTotalCount(boardListRequest.getSearchText());

        // Get Page Offset
        _pageOffset = (boardListRequest.getPageNo() - 1) * boardListRequest.getPageSize();

        // Get Board List
        _boardList = boardMapper.getBoardList(boardListRequest.getSearchText(), _pageOffset, boardListRequest.getPageSize());

        _result.addObject("totalCount", _totalCount);
        _result.addObject("data", _boardList);

        return _result;
    }

    /**
     * Get Board Detail
     * @param boardDetailRequest
     * @return API Result with Board Information
     */
    public Result getBoardDetail(BoardDetailRequest boardDetailRequest) throws Exception {
        AuthToken _authToken;
        BoardRes  _board;
        Result    _result    = new Result();

        // Get Board Detail
        _board = getBoardDetail(boardDetailRequest.getContentId());
        if (_board == null) {
            _result.setResult(ApiStatusCode.BOARD_DETAIL_NOT_EXIST);
            return _result;
        }

        // Get Auth Token From Param
        if (boardDetailRequest.getAuthToken()  == null || boardDetailRequest.getAuthToken().isEmpty()) {
            _authToken = new AuthToken();
        } else {
            _authToken = CommonModule.GetAuthInfoFromToken(boardDetailRequest.getAuthToken());
        }

        // Check Content Validation
        // Check Status (Blind Content)
        if (_board.getStatus().equals(3) && !_board.getAccountId().equals(_authToken.getAccountId())) {
            _result.setResult(ApiStatusCode.BOARD_DETAIL_BLIND);
            return _result;
        }

        // Check Author
        if (_board.getStatus().equals(1) && !_board.getAccountId().equals(_authToken.getAccountId())) {
            _result.setResult(ApiStatusCode.BOARD_DETAIL_NOT_AUTHOR);
            return _result;
        }

        _result.addObject("data", _board);

        return _result;
    }

    /**
     * Write Board Content
     * @param boardWriteRequest
     * @return API Result
     */
    @Transactional(rollbackFor = { Exception.class })
    public Result writeBoardContent(BoardWriteRequest boardWriteRequest) throws Exception {
        Long      _contentId;
        AuthToken _authToken;
        Board     _board;
        Result    _result    = new Result();

        // Check Validate Request Param
        if (!boardWriteRequest.checkValidation()) {
            _result.setResult(ApiStatusCode.BOARD_WRITE_REQUIRED_PARAM_EMPTY);
            return _result;
        }

        // Get Auth Token From Param
        _authToken = CommonModule.GetAuthInfoFromToken(boardWriteRequest.getAuthToken());

        _board = new Board(_authToken.getAccountId(), boardWriteRequest.getTitle(), boardWriteRequest.getContents());

        // Write Board Contents
        boardMapper.insertBoardContent(_board);

        _contentId = _board.getId();
        if (_contentId.equals(0)) {
            throw new Exception(ApiStatusCode.BOARD_WRITE_DB_ERROR.getDesc());
        }

        // Check Block Word
        checkBlockWords(_contentId, boardWriteRequest.getTitle(), boardWriteRequest.getContents());

        return _result;
    }

    /**
     * Get Board Detail for Update
     * @param boardDetailRequest
     * @return API Result with Board Information
     */
    public Result getUpdateViewBoardDetail(BoardDetailRequest boardDetailRequest) throws Exception {
        AuthToken _authToken;
        BoardRes  _board;
        Result    _result = new Result();

        // Get Board Detail
        _board = getBoardDetail(boardDetailRequest.getContentId());
        if (_board == null) {
            _result.setResult(ApiStatusCode.BOARD_UPDATE_DETAIL_NOT_EXIST);
            return _result;
        }

        // Get Auth Token From Param
        if (boardDetailRequest.getAuthToken() == null || boardDetailRequest.getAuthToken().isEmpty()) {
            _authToken = new AuthToken();
        } else {
            _authToken = CommonModule.GetAuthInfoFromToken(boardDetailRequest.getAuthToken());
        }

        // Check Content Validation
        // Check Status (Blind Content)
        if (_board.getStatus().equals(3)) {
            _result.setResult(ApiStatusCode.BOARD_UPDATE_DETAIL_BLIND);
            return _result;
        }

        // Check Author
        if (!_board.getAccountId().equals(_authToken.getAccountId())) {
            _result.setResult(ApiStatusCode.BOARD_UPDATE_DETAIL_NOT_AUTHOR);
            return _result;
        }

        _result.addObject("data", _board);

        return _result;
    }

    /**
     * Update Board Content
     * @param boardUpdateRequest
     * @return API Result
     */
    @Transactional(rollbackFor = { Exception.class })
    public Result updateBoardContent(BoardUpdateRequest boardUpdateRequest) throws Exception {
        Integer   _rowCount;
        AuthToken _authToken;
        Result    _result    = new Result();

        // Check Validate Request Param
        if (!boardUpdateRequest.checkValidation()) {
            _result.setResult(ApiStatusCode.BOARD_UPDATE_REQUIRED_PARAM_EMPTY);
            return _result;
        }

        if (!boardUpdateRequest.checkBoardNo()) {
            _result.setResult(ApiStatusCode.BOARD_UPDATE_CONTENT_PARAM_EMPTY);
            return _result;
        }

        // Get Auth Token From Param
        _authToken = CommonModule.GetAuthInfoFromToken(boardUpdateRequest.getAuthToken());

        // Update Board Contents
        _rowCount = boardMapper.updateBoardContent(boardUpdateRequest.getTitle(), boardUpdateRequest.getContents(), boardUpdateRequest.getBoardNo(), _authToken.getAccountId());
        if (!_rowCount.equals(1)) {
            _result.setResult(ApiStatusCode.BOARD_UPDATE_DB_ERROR);
            return _result;
        }

        // Check Block Word
        checkBlockWords(boardUpdateRequest.getBoardNo(), boardUpdateRequest.getTitle(), boardUpdateRequest.getContents());

        return _result;
    }

    /**
     * Get Board Detail
     * @param contentId
     * @return Board Detail
     */
    private BoardRes getBoardDetail(Long contentId) {
        BoardRes _board;

        // Get Board Detail
        _board = boardMapper.getBoardDetail(contentId);
        if (_board == null) {
            return null;
        }

        return _board;
    }

    /**
     * Check Block(Prohibit) Word (Title and Contents)
     * @param contentId
     * @param title
     * @param contents
     */
    @Async
    protected void checkBlockWords(Long contentId, String title, String contents) {
        boolean         _isValid       = true;
        List<BlockWord> _blockWordList = cacheModule.getBlockWordList();

        // Loop for Check Block Word
        // ** Simple Checking Logic
        // ** Skip Detail Check Word Checking
        // ** If you need then replace all space and specific characters before Contain Checking
        for (BlockWord _blockWord : _blockWordList) {
            if (title.contains(_blockWord.getBlockWord())) {
                _isValid = false;
                break;
            }

            if (contents.contains(_blockWord.getBlockWord())) {
                _isValid = false;
                break;
            }
        }

        if (_isValid) {
            // Update Content As Publish
            boardMapper.updateBoardContentStatus(contentId, 2);
        } else {
            // Update Content As Blind
            boardMapper.updateBoardContentStatus(contentId, 3);
        }
    }
}
