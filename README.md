Sample Board API
==========================================
샘플 게시판을 위한 API 기능을 제공합니다.


Service Spec & Notice
------------------------------------------
## Spec
- API : Spring Boot (Java 1.8)
- DB : MySQL (기본 root 권한 필요, 필요시, application-mysql.yml 계정 설정 변경)
- View : Bootstrap 사용

## Notice
- Default Host Domain 사용 : http://localhost:9091
- /migrations/V000__create_smple_db.sql 로 MySQL DB 및 스키마 생성
- Html(View) 페이지에서 API를 호출하는 방식으로 구현, 같은 도메인을 사용하는 것으로 개발하여 Cross Domain Credential 미구현
- Sample 용 암호화 키 사용


API List
------------------------------------------
## 회원 관리
- 회원 가입 요청 : /auth/register
- 로그인 : /auth/login

## 게시판 관리
- 게시글 리스트 조회 : /board/list
- 게시글 상세 페이지 조회 : /board/detail
- 게시글 작성 : /board/write
- 게시글 수정 페이지 조회 : /board/update_view
- 게시글 수정 : /board/update


실행 방법
------------------------------------------
1. JVM 실행(Application 실행)
2. http://localhost:9091/home.html 접속