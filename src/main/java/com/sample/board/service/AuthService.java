package com.sample.board.service;

import com.sample.board.enums.ApiStatusCode;
import com.sample.board.model.account.AccountReq.*;
import com.sample.board.model.api.AuthToken;
import com.sample.board.model.api.Result;
import com.sample.board.mysql.mapper.board.AuthMapper;
import com.sample.board.mysql.model.board.AccountManagement;
import com.sample.board.util.CommonModule;
import com.sample.board.util.SecurityModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Description : Authenticate Service
 * Version : V1.0
 * Author : Demian.khj
 * Create Date : 2020-02-24
 * Update : None
 */
@Service
public class AuthService {
    // region Autowired Service
    AuthMapper authMapper;

    @Autowired
    public AuthService(AuthMapper authMapper) {
        this.authMapper = authMapper;
    }

    /**
     * Register Account Managements
     * @param accountRegisterReq
     * @return API Result
     */
    @Transactional(rollbackFor = { Exception.class })
    public Result registerAccountManagement(AccountRequest accountRegisterReq) {
        boolean _isDuplidate = false;
        String _password;
        Result _result       = new Result();

        // Check Duplicate Unique Key
        _isDuplidate = authMapper.checkAccountDuplicate(accountRegisterReq.getId());
        if (_isDuplidate) {
            _result.setResult(ApiStatusCode.ACCOUNT_ID_DUPLICATED);
            return _result;
        }

        // Hashing Password As SHA256
        _password = SecurityModule.SHA256Hash(accountRegisterReq.getPassword());
        if (_password.isEmpty()) {
            _result.setResult(ApiStatusCode.ACCOUNT_PASSWORD_HASH_ERROR);
            return _result;
        }

        // Insert New Account to DB
        authMapper.insertAccountManagements(accountRegisterReq.getId(), _password);

        return _result;
    }

    /**
     * Login Auth
     * @param accountAuthRequest
     * @return API Result
     */
    @Transactional(rollbackFor = Exception.class)
    public Result authAccount(AccountRequest accountAuthRequest) throws Exception {
        Integer           _rowCount;
        String            _password;
        String            _authToken;
        AccountManagement _accountInfo = new AccountManagement();
        Result            _result      = new Result();

        // Hashing Password As SHA256
        _password = SecurityModule.SHA256Hash(accountAuthRequest.getPassword());
        if (_password.isEmpty()) {
            _result.setResult(ApiStatusCode.ACCOUNT_PASSWORD_HASH_ERROR);
            return _result;
        }

        // Auth Account With ID
        _accountInfo = authMapper.getAccountInfo(accountAuthRequest.getId());
        if (_accountInfo == null) {
            _result.setResult(ApiStatusCode.ACCOUNT_LOGIN_FAIL_NOT_FOUND);
            return _result;
        }

        // region Validate Account Information Start
        if (_accountInfo.getStatus().equals(2)) {
            _result.setResult(ApiStatusCode.ACCOUNT_LOGIN_FAIL_BLOCKED);
            return _result;
        }

        if (_accountInfo.getStatus().equals(3)) {
            _result.setResult(ApiStatusCode.ACCOUNT_LOGIN_FAIL_QUICK);
            return _result;
        }

        if (!_accountInfo.getLoginPwd().equals(_password)) {
            // Update Login Fail Count
            authMapper.authFailAccount(_accountInfo.getId());

            _result.setResult(ApiStatusCode.ACCOUNT_LOGIN_FAIL_NOT_FOUND);
            return _result;
        }
        // endregion Validate Account Information End

        // Set Login Success
        _rowCount = authMapper.authAccount(_accountInfo.getId());
        if (!_rowCount.equals(1)) {
            throw new Exception(ApiStatusCode.ACCOUNT_LOGIN_EXCEPTION.getDesc());
        }

        // Serialize Auth Request To Json String
        _authToken = CommonModule.SerializeObjectToJson(new AuthToken(_accountInfo.getId(), accountAuthRequest.getId()));

        // Encrypt Auth Token As AES256
        _authToken = SecurityModule.AES256Encrypt(_authToken);

        _result.addObject("auth_token", _authToken);

        return _result;
    }
}
