package com.sample.board.mysql.mapper.board;

import com.sample.board.model.board.BoardRes;
import com.sample.board.mysql.model.board.Board;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Description : Board Query Mapper
 * Version : V1.0
 * Author : Demian.khj
 * Create Date : 2020-02-25
 * Update : None
 */
@Repository
@Mapper
public interface BoardMapper {
    Long getBoardTotalCount(@Param("searchText") String searchText);

    List<BoardRes> getBoardList(@Param("searchText") String searchText,
                                @Param("offset") Integer offset,
                                @Param("pageSize") Integer pageSize);

    BoardRes getBoardDetail(@Param("contentId") Long contentId);

    Long insertBoardContent(Board board);

    Integer updateBoardContent(@Param("title") String title,
                               @Param("contents") String contents,
                               @Param("contentId") Long contentId,
                               @Param("accountId") Long accountId);

    Integer updateBoardContentStatus(@Param("contentId") Long contentId,
                                     @Param("status") Integer status);
}
