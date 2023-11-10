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
<script type="text/javascript">
	function roleForm(id) {
		location.href="userController.jsp?command=roleForm&id="+id;
	}
</script>
<link rel="stylesheet" href="css/layout1.css" />
</head>
<%
	List<UserDto> list = (List<UserDto>)request.getAttribute("list");
%>
<body>
<div id="container">
	<div class="main">
		<div class="lead">
			<span><%=ldto.getId()%>[<%=ldto.getRole()%>]님이 로그인 함</span> |
			<span><a href="userController.jsp?command=getAllUserList">회원전체조회</a></span> |
			<span><a href="userController.jsp?command=getUserList">회원정보[등급]수정</a></span>
			<span><a href="userController.jsp?command=logout">로그아웃</a></span>
		</div>
		<div class="contents">
			<h1>관리자 페이지</h1>
			<h2>회원목록조회[수정]</h2>
			<div id="userAllList">
				<table class="table">
					<tr>
						<th>회원번호</th>
						<th>아 이 디</th>
						<th>이    름</th>
						<th>주    소</th>
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
								<td>
									<%=dto.getRole()%>
									
									<%
										if(!dto.getId().equals(ldto.getId())){
											%>
											<button type="button" onclick="roleForm('<%=dto.getId()%>')">변경</button>
											<%
										}
									%>
								</td>
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


