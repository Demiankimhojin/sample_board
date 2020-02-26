package com.sample.board.model.board;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Description : Boards Response Models
 * Version : V1.0
 * Author : Demian.khj
 * Create Date : 2020-02-25
 * Update : None
 */
@Data
public class BoardRes {
    private Long          id;
    private Long          accountId;
    private String        loginId;
    private String        title;
    private String        contents;
    private Integer       status;
    private LocalDateTime registeredAt;
    private LocalDateTime modifiedAt;
}
