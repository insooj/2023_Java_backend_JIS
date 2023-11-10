<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<%
// 	String msg=request.getParameter("msg");
%>
<body>
<h1>시스템오류입니다. 관리자에게 문의하세요</h1>
<h2><a href="index.jsp">메인</a></h2>
<h3>오류내용:${param.msg}</h3>
<%-- <h3>오류내용:<%=msg%></h3> --%>
</body>
</html>











