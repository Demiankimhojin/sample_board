package com.sample.board.mysql.model.board;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Description : Block Words DB Model
 * Version : V1.0
 * Author : Demian.khj
 * Create Date : 2020-02-25
 * Update : None
 */
@Data
public class BlockWord {
    private Long          id;               // PK
    private String        blockWord;        // 금칙어
    private Integer       status;           // 상태 (1:정상, 2:미사용)
    private LocalDateTime registeredAt;     // 등록 일시
    private LocalDateTime modifiedAt;       // 수정 일시
}
