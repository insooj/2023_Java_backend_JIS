<%@page import="com.hk.login.dtos.HkDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<% request.setCharacterEncoding("utf-8"); %>
<% response.setContentType("text/html; charset=utf-8"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
</script>
</head>
<body>
<div id="container">
	<div class="main">
		<div class="lead">
			<span>${sessionScope.ldto.id}[${sessionScope.ldto.role}]님이 로그인 함</span> |
			<span><a href="myinfo.do?id=${sessionScope.ldto.id}">나의정보</a></span> |
			<span><a href="boardList.do">게시판</a></span> |
			<span><a href="logout.do">로그아웃</a></span>
		</div>
		<div class="contents">
			<h1>사용자 페이지</h1>
			
		</div>
	</div>
</div>
</body>
</html>













