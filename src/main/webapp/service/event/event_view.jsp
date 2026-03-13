<%@ page language="java" contentType="text/html; charset=UTF-8"%>

<html>
<head>
<meta charset="UTF-8">
<title>이벤트 상세</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/service/event.css" />
</head>
<body>
<div class="event-detail">
    <h2>${event.title}</h2>
    <img src="${pageContext.request.contextPath}/assets/img/service/${event.img}" alt="${event.alt}" />
    <p>${event.content}</p>
    <p><strong>기간:</strong> ${event.date}</p>
    <a href="${pageContext.request.contextPath}/service/event/event_list.jsp" class="btn-back">목록</a>
</div>
</body>
</html>