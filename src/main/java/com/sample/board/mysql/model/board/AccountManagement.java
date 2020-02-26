package com.sample.board.mysql.model.board;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Description : Account Managements DB Model
 * Version : V1.0
 * Author : Demian.khj
 * Create Date : 2020-02-24
 * Update : None
 */
@Data
public class AccountManagement {
    private Long          id;               // PK 계정 번호
    private String        loginId;          // 로그인 아이디
    private String        loginPwd;         // 로그인 패스워드
    private Integer       status;           // 상태 (1:정상, 2:블락, 3:탈퇴)
    private Integer       loginFailCnt;     // 로그인 실패 횟수
    private LocalDateTime registeredAt;     // 등록 일시
    private LocalDateTime modifiedAt;       // 수정 일시
    private LocalDateTime lastLoginAt;      // 마지막 로그인 일시
}
