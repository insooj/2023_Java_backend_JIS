<%
response.setHeader("Pragma", "no-cache"); //HTTP 1.0
response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
response.setHeader("Cache-Control", "no-store"); //HTTP 1.1
response.setDateHeader("Expires", 0L); // Do not cache in proxy server
%>
<%@page import="com.hk.user.dtos.UserDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<% request.setCharacterEncoding("utf-8"); %>
<% response.setContentType("text/html; charset=utf-8"); %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="layout2.css" />
</head>
<%
// 	UserDto ldto=(UserDto)session.getAttribute("ldto");
	
	//로그인 정보가 없는 경우 화면 처리--> 로그인 정보가 null인경우 오류가 발생하기 때문
// 	if(ldto==null){
// 		pageContext.forward("index.jsp");
// 	}
%>
<c:if test="${sessionScope.ldto==null}">
	<jsp:forward page="index.jsp" />
</c:if>
<body>
<nav class="navbar">
	<div id="navbar">
		<ul class="navbar-nav">
			<c:choose>
				<c:when test="${sessionScope.ldto.role eq 'ADMIN'}">
					<li><a href="admin_main.jsp">HOME</a></li>
				</c:when>
				<c:when test="${sessionScope.ldto.role eq 'USER'}">
					<li><a href="user_main.jsp">HOME</a></li>
				</c:when>
			</c:choose>	
			<li>ABOUT</li>
			<li>CONTECT</li>
		</ul>
	</div>
</nav>
</body>
</html>



