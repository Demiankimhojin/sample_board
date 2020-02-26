package com.sample.board.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Description : API Status Code Enum Management
 * Version : V1.0
 * Author : Demian.khj
 * Create Date : 2020-02-24
 * Update : None
 */
public enum  ApiStatusCode {
    // 요청 성공
    SUCCEED(0, "성공"),

    // 계정 관련 에러 : -1100 ~ -1199
    // 회원 가입 에러
    ACCOUNT_ID_DUPLICATED(-1100, "이미 존재하는 아이디 입니다."),
    ACCOUNT_REGISTER_EXCEPTION(-1119, "계정 생성에 실패하였습니다."),

    // 로그인 실패 에러
    ACCOUNT_LOGIN_FAIL_NOT_FOUND(-1121, "로그인 정보를 찾을 수 없습니다."),
    ACCOUNT_LOGIN_FAIL_BLOCKED(-1122, "접근이 제한된 사용자 입니다."),
    ACCOUNT_LOGIN_FAIL_QUICK(-1123, "탈퇴한 사용자 입니다."),
    ACCOUNT_LOGIN_EXCEPTION(-1129, "로그인에 실패하였습니다."),

    // 계정 공용
    ACCOUNT_PASSWORD_HASH_ERROR(-1198, "알 수 없는 에러가 발생하였습니다."),

    // 게시판 관련 에러 : -1200 ~ -1299
    // 게시글 리스트 조회 에러
    BOARD_LIST_EXCEPTION(-1219, "게시글 내역을 조회하지 못하였습니다."),

    // 게시글 상세 조회 에러
    BOARD_DETAIL_NOT_EXIST(-1221, "게시글 정보가 존재하지 않습니다."),
    BOARD_DETAIL_DELETE(-1222, "삭제된 게시글 입니다."),
    BOARD_DETAIL_BLIND(-1223, "블라인드 처리된 게시글 입니다."),
    BOARD_DETAIL_NOT_AUTHOR(-1224, "대기 상태시 본인만 확인할 수 있습니다."),
    BOARD_DETAIL_EXCEPTION(-1229, "게시글 상세 정보를 조회하지 못하였습니다."),

    // 게시글 등록 에러
    BOARD_WRITE_REQUIRED_PARAM_EMPTY(-1231, "필수값이 없습니다."),
    BOARD_WRITE_DB_ERROR(-1232, "게시글 등록에 실패하였습니다."),
    BOARD_WRITE_EXCEPTION(-1239, "게시글 등록에 실패하였습니다."),

    // 게시글 수정 상세 조회 에러
    BOARD_UPDATE_DETAIL_NOT_EXIST(-1241, "게시글 정보가 존재하지 않습니다."),
    BOARD_UPDATE_DETAIL_DELETE(-1242, "삭제된 게시글 입니다."),
    BOARD_UPDATE_DETAIL_NOT_AUTHOR(-1243, "작성자만 수정이 가능합니다."),
    BOARD_UPDATE_DETAIL_EXCEPTION(-1249, "게시글 상세 정보를 조회하지 못하였습니다."),

    // 게시글 수정 에러
    BOARD_UPDATE_REQUIRED_PARAM_EMPTY(-1251, "필수값이 없습니다."),
    BOARD_UPDATE_CONTENT_PARAM_EMPTY(-1252, "필수값이 없습니다."),
    BOARD_UPDATE_DB_ERROR(-1253, "게시글 수정에 실패하였습니다."),
    BOARD_UPDATE_EXCEPTION(-1259, "게시글 수정에 실패하였습니다."),

    // 게시글 삭제 에러
    BOARD_DELETE_NOT_EXIST(-1261, "게시글 정보가 존재하지 않습니다."),
    BOARD_DELETE_NOT_AUTHOR(-1262, "작성자만 삭제 가능합니다."),
    BOARD_DELETE_DB_EXCEPTION(-1263, "게시글 삭제에 실패하였습니다."),
    BOARD_DELETE_EXCEPTION(-1269, "게시글 삭제에 실패하였습니다."),

    // 공용 에러 : -9000 ~ 9999
    SERIALIZE_EXCEPTION(-9901, "알 수 없는 에러가 발생하였습니다."),
    DESERIALIZE_EXCEPTION(-9902, "알 수 없는 에러가 발생하였습니다."),
    EXCEPTION_ERROR(-9999, "시스템 에러 발생")
    ;

    private int statusCode;
    private String desc;

    ApiStatusCode(int statusCode, String desc) {
        setStatusCode(statusCode);
        setDesc(desc);
    }

    // 스테이터스 코드를 받아서 본래 뭔지 찾을수 있도록 룩업 테이블을 구성한다.
    private static final Map<Integer, ApiStatusCode> lookup = new HashMap<>();

    static {
        for (ApiStatusCode item : EnumSet.allOf(ApiStatusCode.class))
            lookup.put(item.getStatusCode(), item);
    }

    public static ApiStatusCode getApiStatusCode(Integer statusCode) {
        return lookup.get(statusCode);
    }


    public int getStatusCode() {
        return statusCode;
    }

    public String getDesc() {
        return desc;
    }

    private void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    private void setDesc(String desc) {
        this.desc = desc;
    }
}
