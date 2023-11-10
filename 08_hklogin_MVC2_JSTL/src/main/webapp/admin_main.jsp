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
<style type="text/css">
	@charset "UTF-8";

*{
	margin:0px;
	padding:0px;
}

#navbar{
	background-color:#3c3c3c; 
	height: 60px; 
}

.navbar ul{
	width: 800px;
	margin-left: 100px;
}

.navbar li{
	display: inline-block;
	margin-top:15px;
	font-size: 20px;
	color:white;
	margin-left:15px;
}

.navbar a{
	color:white;
	text-decoration: none;
}

.footer{
	position: absolute;
	left: 0px;
	right: 0px;
	bottom:0px;
	text-align: center;
	color:white;
	line-height: 50px;
	height: 50px;
	background-color: #3c3c3c;
}

form{
	width:800px;
	margin: 10% auto;
}

input{
	border: 1px solid rgb(192, 192, 192) ;
	width: 780px;
	height: 40px;
	border-radius: 5px;
	padding-left: 10px;
}

input, button{
	margin:2px;
}

.lead{
	text-align: right;
	padding: 10px;
}

.table{
	width:100%;
	border-collapse: collapse;
}

.table tr>th, .table tr>td {
	padding:5px;
} 

.table tr:nth-child(odd){
	background-color: #dcdcdc;
}

#myinfo,#userAllList{
	width:1000px;
	margin:0 auto;
}
</style>
</head>
<body>
<div id="container">
	<div class="main">
		<div class="lead">
			<span>${ldto.id}[${ldto.role}]님이 로그인 함</span> |
			<span><a href="getAllUserList.user">회원전체조회</a></span> |
			<span><a href="getUserList.user">회원정보[등급]수정</a></span> |
			<span><a href="logout.user">로그아웃</a></span>
		</div>
		<div class="contents">
			<h1>관리자 페이지</h1>
			
		</div>
	</div>
</div>
<%@include file="footer.jsp" %>
</body>
</html>













