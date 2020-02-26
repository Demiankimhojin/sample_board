package com.sample.board.model.api;

import com.sample.board.enums.ApiStatusCode;
import java.util.HashMap;

/**
 * Description : API Result Response Models
 * Version : V1.0
 * Author : Demian.khj
 * Create Date : 2020-02-24
 * Update : None
 */
public class Result extends HashMap {
    private final static String RESULT_CODE = "resultCode";
    private final static String RESULT_MSG  = "resultMsg";

    public Result() {
        this.put(RESULT_CODE, 0);
        this.put(RESULT_MSG, "성공");
    }

    public Result(ApiStatusCode result) {
        this.put(RESULT_CODE, result.getStatusCode());
        this.put(RESULT_MSG, result.getDesc());
    }

    public void setResult(ApiStatusCode result) {
        this.setResultCode(result.getStatusCode());
        this.setResultMsg(result.getDesc());
    }

    public int getResultCode() { return (int) this.get(RESULT_CODE); }
    public String getResultMsg() { return (String) this.get(RESULT_MSG); }

    public void setResultCode(int resultCode) {
        this.put(RESULT_CODE, resultCode);
    }

    public void setResultMsg(String resultMsg) {
        this.put(RESULT_MSG, resultMsg);
    }

    public Result addObject(String attributeName, Object attributeValue) {
        if (attributeValue != null) {
            this.put(attributeName, attributeValue);
        }
        return this;
    }
}
