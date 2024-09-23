<%@page import="java.util.List"%>
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
<%
	List<UserDto> list = (List<UserDto>)request.getAttribute("list");
%>
<body>
<div id="container">
	<div class="main">
		<div class="lead">
			<span>님이 로그인 함</span> |
			<span><a href="userController.jsp?command=getAllUserList">회원전체조회</a></span> |
			<span><a href="userController.jsp?command=getUserList">회원정보[등급]수정</a></span>
			<span><a href="userController.jsp?command=logout">로그아웃</a></span>
		</div>
		<div class="contents">
			<h1>관리자 페이지</h1>
			<h2>회원전체목록</h2>
			<div id="userAllList">
				<table class="table">
					<tr>
						<th>회원번호</th>
						<th>아 이 디</th>
						<th>이    름</th>
						<th>주    소</th>
						<th>이 메 일</th>
						<th>회원등급</th>
						<th>탈퇴여부</th>
						<th>가 입 일</th>
					</tr>
					<%
						if(list==null || list.size() == 0){
							out.print("<tr>"
										+"<td colspan='8'>--회원이존재하지않습니다.--</td>"
										+	"</tr>");
							
						}else{
							for(UserDto dto:list){ //향상된 for문
								
							%>
							<tr>
								<td><%=dto.getSeq()%></td>
								<td><%=dto.getId()%></td>
								<td><%=dto.getName()%></td>
								<td><%=dto.getAddress()%></td>
								<td><%=dto.getEmail()%></td>
								<td><%=dto.getRole()%></td>
								<td><%=dto.getEnabled().equals("Y")?"가입중일수도":"탈퇴했을수도"%></td>
								<td><%=dto.getRegDate()%></td>
							</tr>
							<%
							}
						}
					%>
				</table>
			</div>
		</div>
	</div>
</div>
<%@include file="footer.jsp" %>
</body>
</html>


