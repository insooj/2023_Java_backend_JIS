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
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="../css/layout1.css" />
</head>
<%
//현재 path 확인해보기
	System.out.println(request.getRequestURI());
	System.out.println(request.getRequestURI().substring(
				request.getContextPath().length()
			));
	UserDto ldto=null;
	
	String requestPath=
	request.getRequestURI().substring(request.getContextPath().length());
	
	if(!requestPath.equals("/user/registForm.jsp")){
		ldto=(UserDto)session.getAttribute("ldto");
	
		//로그인 정보가 없는 경우(로그아웃한 경우) 화면처리 
		if(ldto==null){
			pageContext.forward("/index.jsp");
		}		
	}
%>
<body>
<nav class="navbar">
	<div id="navbar">
		<ul class="navbar-nav">
			<li><a href="index.jsp">HOME</a></li>
			<li>ABOUT</li>
			<li>CONTECT</li>
		</ul>
	</div>
</nav>
</body>
</html>



