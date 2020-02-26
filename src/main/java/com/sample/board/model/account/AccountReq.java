package com.sample.board.model.account;

import lombok.Data;

/**
 * Description : Account Managements Request Models
 * Version : V1.0
 * Author : Demian.khj
 * Create Date : 2020-02-24
 * Update : None
 */
public class AccountReq {
    @Data
    public static class AccountRequest {
        private String id;
        private String password;
    }
}
