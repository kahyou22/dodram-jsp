<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@ include file="/WEB-INF/includes/init.jsp" %>
     <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>신선한 식탁을 즐기다, 도드람몰입니다.</title>
    <link rel="icon" href="${ctx}/assets/img/main/favicon.png" type="image/x-icon" />
    <link rel="stylesheet" href="${ctx}/assets/css/layout.css" />
    <link rel="stylesheet" href="${ctx}/assets/css/mypage/mypage_claim.css">
    <link rel="stylesheet" href="${ctx}/assets/css/mypage/login.css">
    <link
      rel="stylesheet"
      as="style"
      crossorigin
      href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard@v1.3.9/dist/web/static/pretendard.min.css"
    />
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
</head>
<body>
<%@ include file="/WEB-INF/includes/header.jsp" %>

<main>
    <div class="login-wrap">
        <h1>로그인</h1>
    
        <div class="login-box">
            <h2>회원 로그인</h2>
    
            <div class="login-form">
                <div class="input-area">
                    <input type="text" placeholder="아이디">
                    <input type="password" placeholder="비밀번호">

                      <label class="save-id">
                        <input type="checkbox"> 아이디 저장
                    </label>
                   
                </div>
    
                <div><button class="btn-login">로그인</button></div>
            </div>
        </div>
    </div>
</main>

<%@ include file="/WEB-INF/includes/footer.jsp" %>
<%@ include file="/WEB-INF/includes/sideMenu.jsp" %>
</body>
</html>
