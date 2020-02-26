package com.sample.board.util;

import com.sample.board.mysql.mapper.board.BlockWordMapper;
import com.sample.board.mysql.model.board.BlockWord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description : Cache Module
 * Version : V1.0
 * Author : Demian.khj
 * Create Date : 2020-02-26
 * Update : None
 */
@Service
@EnableScheduling
public class CacheModule {
    BlockWordMapper blockWordMapper;

    @Autowired
    CacheModule(BlockWordMapper blockWordMapper) {
        this.blockWordMapper = blockWordMapper;
    }

    public static final String BLOCK_WORD_KEY = "blockWordKey";

    /**
     * Get Pocket Money Event Information from DB
     * @return
     */
    @Cacheable(value = "blockWordList", key = "#root.target.BLOCK_WORD_KEY")
    public List<BlockWord> getBlockWordList() {
        return blockWordMapper.getBlockWordList();
    }

    /**
     * Delete Pocket Money Event Information from Cache
     */
    @CacheEvict(value = "blockWordList", key = "#root.target.BLOCK_WORD_KEY")
    @Scheduled(fixedRate = 1000 * 60 * 5)
    public void refreshBlockWord() {
    }
}
