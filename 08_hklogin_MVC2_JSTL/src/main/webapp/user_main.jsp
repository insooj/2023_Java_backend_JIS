<%@include file="header.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<% request.setCharacterEncoding("utf-8"); %>
<% response.setContentType("text/html; charset=utf-8"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/layout1.css" />
</head>
<body>
<div id="container">
	<div class="main">
		<div class="lead">
			<span>${sessionScope.ldto.name}[${sessionScope.ldto.role}]님이 로그인 함</span> |
			<span><a href="myinfo.user?id=${sessionScope.ldto.id}">나의정보</a></span> |
			<span><a href="logout.user">로그아웃</a></span>
		</div>
		<div class="contents">
			<h1>사용자 페이지</h1>
			
		</div>
	</div>
</div>
<%@include file="footer.jsp" %>
</body>
</html>


