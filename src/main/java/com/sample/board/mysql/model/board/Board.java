package com.sample.board.mysql.model.board;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Description : Boards DB Model
 * Version : V1.0
 * Author : Demian.khj
 * Create Date : 2020-02-25
 * Update : None
 */
@Data
public class Board {
    private Long          id;               // PK 게시글 번호
    private Long          accountId;        // 계정 번호 (account_managements.id)
    private String        title;            // 게시글 제목
    private String        contents;         // 게시글 내용
    private Integer       status;           // 상태 (1:등록 요청, 2:등록 완료, 3:블라인드)
    private LocalDateTime registeredAt;     // 등록 일시
    private LocalDateTime modifiedAt;       // 수정 일시

    public Board() {}

    public Board(Long accountId, String title, String contents) {
        this.accountId = accountId;
        this.title     = title;
        this.contents  = contents;
    }
}
