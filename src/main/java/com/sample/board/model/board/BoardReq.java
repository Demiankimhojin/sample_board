package com.sample.board.model.board;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Description : Boards Response Models
 * Version : V1.0
 * Author : Demian.khj
 * Create Date : 2020-02-25
 * Update : None
 */
public class BoardReq {
    @Data
    public static class BoardListRequest {
        private String  searchText;
        private Integer pageSize;
        private Integer pageNo;
    }

    @Data
    public static class BoardDetailRequest {
        private String authToken;
        private Long   contentId;
    }

    @Data
    public static class BoardWriteRequest {
        private String authToken;
        private String title;
        private String contents;

        public boolean checkValidation() {
            if (this.authToken == null || this.authToken.isEmpty()) {
                return false;
            }

            if (this.title == null || this.title.isEmpty()) {
                return false;
            }

            if (this.contents == null || this.contents.isEmpty()) {
                return false;
            }

            return true;
        }
    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    public static class BoardUpdateRequest extends BoardWriteRequest {
        private Long boardNo;

        public boolean checkBoardNo() {
            if (this.boardNo == null || this.boardNo.equals(0)) {
                return false;
            }

            return true;
        }
    }
}
