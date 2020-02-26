CREATE DATABASE IF NOT EXISTS `sample_board` ;

USE sample_board;

-------------------------------------------------------
--- Description : 회원 관리 테이블
--- Author : demian.khj
--- Create Date : 2020-02-24
--- Update : Just Created.
-------------------------------------------------------
CREATE TABLE IF NOT EXISTS sample_board.account_managements (
  id                        BIGINT(20)          NOT NULL        AUTO_INCREMENT  COMMENT '아이디 PK',
  login_id                  VARCHAR(100)        NOT NULL                        COMMENT '로그인 아이디',
  login_pwd                 VARCHAR(512)        NOT NULL                        COMMENT '로그인 패스워드',
  status                    TINYINT             NOT NULL        DEFAULT 1       COMMENT '계정 상태 (1:정상, 2:블락, 3:탈퇴)',
  login_fail_cnt            TINYINT             NOT NULL        DEFAULT 0       COMMENT '로그인 실패 횟수',
  registered_at             DATETIME            NOT NULL        DEFAULT NOW()   COMMENT '회원 가입일',
  modified_at               DATETIME            NOT NULL        DEFAULT NOW()   COMMENT '회원 정보 수정일',
  last_login_at             DATETIME            NULL                            COMMENT '마지막 로그인 일시',
  PRIMARY KEY (id),
  UNIQUE INDEX ux_login_id (login_id),
  INDEX idx_login_id_login_pwd (login_id, login_pwd),
  INDEX idx_registered_at_status (registered_at, status),
  INDEX idx_modified_at_status (modified_at, status),
  INDEX idx_status (status)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT = '회원 관리 테이블';


-------------------------------------------------------
--- Description : 금칙어 관리 테이블
--- Author : demian.khj
--- Create Date : 2020-02-24
--- Update : Just Created.
-------------------------------------------------------
CREATE TABLE IF NOT EXISTS sample_board.block_words (
  id                        BIGINT(20)          NOT NULL        AUTO_INCREMENT  COMMENT '아이디 PK',
  block_word                VARCHAR(50)         NOT NULL                        COMMENT '금칙어',
  status                    TINYINT             NOT NULL        DEFAULT 1       COMMENT '상태 (1:정상, 2:미사용)',
  registered_at             DATETIME            NOT NULL        DEFAULT NOW()   COMMENT '등록 일시',
  modified_at               DATETIME            NOT NULL        DEFAULT NOW()   COMMENT '수정 일시',
  PRIMARY KEY (id),
  INDEX idx_status (status)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT = '금칙어 관리 테이블';


-------------------------------------------------------
--- Description : 게시판 관리 테이블
--- Author : demian.khj
--- Create Date : 2020-02-24
--- Update : Just Created.
-------------------------------------------------------
CREATE TABLE IF NOT EXISTS sample_board.boards (
  id                        BIGINT(20)          NOT NULL        AUTO_INCREMENT  COMMENT '아이디 PK',
  account_id                BIGINT(20)          NOT NULL                        COMMENT '계정 아이디',
  title                     VARCHAR(100)        NOT NULL                        COMMENT '게시글 제목',
  contents                  MEDIUMTEXT          NOT NULL                        COMMENT '게시글 내용',
  status                    TINYINT             NOT NULL        DEFAULT 1       COMMENT '상태 (1:등록 요청, 2:등록 완료, 3:블라인드, 4:삭제된 게시글)',
  registered_at             DATETIME            NOT NULL        DEFAULT NOW()   COMMENT '등록 일시',
  modified_at               DATETIME            NOT NULL        DEFAULT NOW()   COMMENT '수정 일시',
  PRIMARY KEY (id),
  INDEX idx_status_title (status, title),
  INDEX idx_registered_at_status (registered_at, status),
  INDEX idx_modified_at_status (modified_at, status)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT = '게시판 관리 테이블';