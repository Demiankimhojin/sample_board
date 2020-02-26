package com.sample.board.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.sample.board.enums.ApiStatusCode;
import com.sample.board.model.api.AuthToken;

import java.io.IOException;

/**
 * Description : Common Module
 * Version : V1.0
 * Author : Demian.khj
 * Create Date : 2020-02-25
 * Update : None
 */
public class CommonModule {
    /**
     * Serialize Object To Json String
     * @param object
     * @param <T>
     * @return Json String
     * @throws Exception
     */
    public static <T> String SerializeObjectToJson(T object) throws Exception {
        String       _jsonString;
        ObjectWriter _objectWriter;

        try {
            _objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
            _jsonString = _objectWriter.writeValueAsString(object);
        } catch (Exception Ex) {
            throw new Exception(ApiStatusCode.SERIALIZE_EXCEPTION.getDesc());
        }

        return _jsonString;
    }

    /**
     * Get Auth Token From AuthToken String
     * @param authToken
     * @return Auth Token
     */
    public static AuthToken GetAuthInfoFromToken(String authToken) throws Exception {
        String       _decToken;
        ObjectMapper _objectMapper = new ObjectMapper();

        _decToken = SecurityModule.AES256Decrypt(authToken);

        return _objectMapper.readValue(_decToken, AuthToken.class);
    }
}
