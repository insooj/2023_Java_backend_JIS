<%@page import="java.util.List"%>
<%@include file="header.jsp" %>
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
<script type="text/javascript">
	//등급변경을 위한 회원상세정보조회
	function roleForm(id){
		location.href="roleForm.user?id="+id;
	}
	
</script>
</head>
<%
// 	List<UserDto> list=(List<UserDto>)request.getAttribute("list");
%>
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
			<h2>회원목록조회[등급수정]</h2>
			<div id="userAllList">
				<table class="table">
					<tr>
						<th>회원번호</th>
						<th>아이디</th>
						<th>이름</th>
						<th>회원등급</th>
						<th>가입일</th>
					</tr>
					<c:choose>
						<c:when test="${empty list}">
							<tr>
								<td colspan="5">--가입된 회원이 없습니다.--</td>
							</tr>
						</c:when>
						<c:otherwise>
							<c:forEach items="${list}" var="dto">
								<tr>
									<td>${dto.seq}</td>
									<td>${dto.id}</td>
									<td>${dto.name}</td>
									<td>
										${dto.role}
										<c:choose>
											<c:when test="${dto.id==ldto.id}">
												<small style="color:red;">자신의 등급은 수정불가</small>
											</c:when>
											<c:otherwise>                      
												<button type="button" onclick="roleForm('${dto.id}')">변경</button>
											</c:otherwise>               
										</c:choose>	
									</td>
									<td>${dto.regDate}</td>
								</tr>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</table>
			</div>
		</div>
	</div>
</div>
<%@include file="footer.jsp" %>
</body>
</html>













