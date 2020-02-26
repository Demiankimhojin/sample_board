package com.sample.board.model.api;

import lombok.Data;

/**
 * Description : API Auth Request Models
 * Version : V1.0
 * Author : Demian.khj
 * Create Date : 2020-02-25
 * Update : None
 */
@Data
public class AuthToken {
    private Long   accountId;
    private String loginId;

    public AuthToken() {
        this.accountId = new Long(0);
        this.loginId   = "";
    }

    public AuthToken(Long accountId, String loginId){
        this.accountId = accountId;
        this.loginId   = loginId;
    }
}
