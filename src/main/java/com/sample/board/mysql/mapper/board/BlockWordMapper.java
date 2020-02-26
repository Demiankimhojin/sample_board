package com.sample.board.mysql.mapper.board;

import com.sample.board.mysql.model.board.BlockWord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Description : Block Word Query Mapper
 * Version : V1.0
 * Author : Demian.khj
 * Create Date : 2020-02-25
 * Update : None
 */
@Repository
@Mapper
public interface BlockWordMapper {
    List<BlockWord> getBlockWordList();
}
