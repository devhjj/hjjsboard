# HJJ's board
- 목적: 개인 게시판 실습
- 기술 스택: Spring boot, mybatis, mariadb, js, bootstrap, git
- 상세 내용:
```
1. 글쓰기, 삭제, 조회 기능
- 오픈소스 'simplemde'를 사용하여 글을 작성하거나 볼 때 Markdown 적용
(https://github.com/sparksuite/simplemde-markdown-editor)
- ip를 이용하여 조회수 체크

2. 댓글 기능
- REST 방식으로 구현

3. 로그인 기능
- Spring security를 사용하여 로그인 기능 구현 (csrf 적용)
- 회원 별 권한을 부여하여 조회 기능은 회원만 가능하고, 글쓰기, 삭제는 관리자만 가능하도록 변경 

4. 회원가입 기능
- mail의 링크를 통해 가입 인증되도록 회원가입 기능 구현 (gmail smtp 사용)
- Ajax와 정규식으로 유효성 검사 (javascript, jQuery 사용)

5. 캘린더 기능
- 오픈 소스인 'fullcalendar'를 사용하여 REST 방식으로 구현
(https://github.com/fullcalendar/fullcalendar)
```

## Description
- Purpose: For personal bulletin board practice
- Technologies Used: Spring boot, mybatis, mariadb, js, bootstrap, git
- Details:
```
1. Write, Delete, View
- Use OSS(Open Source Software) 'simplemde' to apply Markdown when writing or viewing articles
(https://github.com/sparksuite/simplemde-markdown-editor)
- Use ip address to check the number of views

2. Comment
- Implemented in REST method

3. Sign in
- Implement sign in function using Spring security (+ csrf)
- By granting permission for each member, the inquiry function is only available for members, 
and only the administrator can write and delete.

4. Sign up
- Implement registration is authenticated through a link in the mail (use gmail smtp)
- Validation with Ajax and regular expressions (use javascript, jQuery)

5. Calendar
- Use OSS 'fullcalendar' to implement calendar in REST method
(https://github.com/fullcalendar/fullcalendar)
```

### developed by devhjj
