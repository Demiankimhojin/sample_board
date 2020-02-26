package com.sample.board.controller;

import com.sample.board.enums.ApiStatusCode;
import com.sample.board.model.account.AccountReq.*;
import com.sample.board.model.api.Result;
import com.sample.board.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Description : Authenticate Controller
 * Version : V1.0
 * Author : Demian.khj
 * Create Date : 2020-02-24
 * Update : None
 */
@RestController
@RequestMapping(value = {"/auth"})
public class AuthController {
    AuthService authService;

    @Autowired
    public AuthController (AuthService authService) {
        this.authService = authService;
    }

    /**
     * Account Registration API
     * @param accountRegisterReq
     * @return API Result
     */
    @PostMapping(value = {"/register"})
    public Result Register(@RequestBody AccountRequest accountRegisterReq) {
        Result _result = new Result();

        try {
            _result = authService.registerAccountManagement(accountRegisterReq);
            if (_result.getResultCode() != 0) {
                System.out.println("/auth/register registerAccountManagement Error : " + _result.getResultMsg());
                return _result;
            }
        } catch (Exception Ex) {
            System.out.println("/auth/register Exception Error : " + Ex.toString());
            _result.setResult(ApiStatusCode.ACCOUNT_REGISTER_EXCEPTION);
        }

        return _result;
    }

    /**
     * Account Login Auth API
     * @param accountAuthReq
     * @return API Result
     */
    @PostMapping(value = {"/login"})
    public Result LogIn(@RequestBody AccountRequest accountAuthReq) {
        Result _result = new Result();

        try {
            _result = authService.authAccount(accountAuthReq);
            if (_result.getResultCode() != 0) {
                System.out.println("/auth/login authAccount Error : " + _result.getResultMsg());
                return _result;
            }
        } catch (Exception Ex) {
            System.out.println("/auth/login Exception Error : " + Ex.toString());
            _result.setResult(ApiStatusCode.ACCOUNT_LOGIN_EXCEPTION);
        }

        return _result;
    }
}
