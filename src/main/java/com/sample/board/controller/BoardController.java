package com.sample.board.controller;

import com.sample.board.enums.ApiStatusCode;
import com.sample.board.model.api.Result;
import com.sample.board.model.board.BoardReq.*;
import com.sample.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * Description : Sample Board Controller
 * Version : V1.0
 * Author : Demian.khj
 * Create Date : 2020-02-25
 * Update : None
 */
@RestController
@RequestMapping(value = {"/board"})
public class BoardController {
    BoardService boardService;

    @Autowired
    public BoardController (BoardService boardService) {
        this.boardService = boardService;
    }

    /**
     * Board List API
     * @param boardListRequest
     * @return API Result
     */
    @GetMapping("/list")
    public Result GetBoardList(BoardListRequest boardListRequest) {
        Result _result = new Result();

        try {
            _result = boardService.listBoardContent(boardListRequest);
            if (_result.getResultCode() != 0) {
                System.out.println("/board/list listBoardContent Error : " + _result.getResultMsg());
                return _result;
            }
        } catch (Exception Ex) {
            System.out.println("/board/list Exception Error : " + Ex.toString());
            _result.setResult(ApiStatusCode.BOARD_LIST_EXCEPTION);
            _result.addObject("totalCount", 0);
            _result.addObject("data", new ArrayList());
        }

        return _result;
    }

    /**
     * Board Detail Information API
     * @param boardDetailRequest
     * @return API Result
     */
    @PostMapping("/detail")
    public Result GetBoardDetail(@RequestBody BoardDetailRequest boardDetailRequest) {
        Result _result = new Result();

        try {
            _result = boardService.getBoardDetail(boardDetailRequest);
            if (_result.getResultCode() != 0) {
                System.out.println("/board/detail getBoardDetail Error : " + _result.getResultMsg());
                return _result;
            }
        } catch (Exception Ex) {
            System.out.println("/board/detail Exception Error : " + Ex.toString());
            _result.setResult(ApiStatusCode.BOARD_DETAIL_EXCEPTION);
        }

        return _result;
    }

    /**
     * Board Write API
     * @param boardWriteRequest
     * @return
     */
    @PostMapping("/write")
    public Result WriteBoard(@RequestBody BoardWriteRequest boardWriteRequest) {
        Result _result = new Result();

        try {
            _result = boardService.writeBoardContent(boardWriteRequest);
            if (_result.getResultCode() != 0) {
                System.out.println("/board/write writeBoardContent Error : " + _result.getResultMsg());
                return _result;
            }
        } catch (Exception Ex) {
            System.out.println("/board/write Exception Error : " + Ex.toString());
            _result.setResult(ApiStatusCode.BOARD_WRITE_EXCEPTION);
        }

        return _result;
    }

    /**
     * Board Detail Information API for Update
     * @param boardDetailRequest
     * @return
     */
    @PostMapping("/update_view")
    public Result UpdateViewBoard(@RequestBody BoardDetailRequest boardDetailRequest) {
        Result _result = new Result();

        try {
            _result = boardService.getUpdateViewBoardDetail(boardDetailRequest);
            if (_result.getResultCode() != 0) {
                System.out.println("/board/update_view getUpdateViewBoardDetail Error : " + _result.getResultMsg());
                return _result;
            }
        } catch (Exception Ex) {
            System.out.println("/board/update_view Exception Error : " + Ex.toString());
            _result.setResult(ApiStatusCode.BOARD_UPDATE_DETAIL_EXCEPTION);
        }

        return _result;
    }

    /**
     * Board Update API
     * @param boardUpdateRequest
     * @return
     */
    @PostMapping("/update")
    public Result UpdateBoard(@RequestBody BoardUpdateRequest boardUpdateRequest) {
        Result _result = new Result();

        try {
            _result = boardService.updateBoardContent(boardUpdateRequest);
            if (_result.getResultCode() != 0) {
                System.out.println("/board/update updateBoardContent Error : " + _result.getResultMsg());
                return _result;
            }
        } catch (Exception Ex) {
            System.out.println("/board/update Exception Error : " + Ex.toString());
            _result.setResult(ApiStatusCode.BOARD_UPDATE_EXCEPTION);
        }

        return _result;
    }
}
