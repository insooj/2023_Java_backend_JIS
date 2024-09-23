<%@include file="../header.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>답변형게시판</h1>
<div id="container">
	<h2>새글추가하기</h2>
	<form action="insertBoard.do" method="post">
		<table class="table table-striped">
			<tr>
				<th>작성자[ID]</th>
				<td><input class="form-control" type="text" name="id" pattern="^[a-zA-Z]+$" required="required"/></td>
			</tr>
			<tr>
				<th>글제목</th>
				<td><input class="form-control" type="text" name="title" required="required"/></td>
			</tr>
			<tr>
				<th>글내용</th>
				<td><textarea class="form-control" rows="10" cols="60" name="content" required="required"></textarea> </td>
			</tr>
			<tr>
				<td colspan="2">
					<input class="btn btn-primary" type="submit" value="등록"/>
					<input class="btn btn-primary" type="button" value="목록" 
							onclick="location.href='boardList.do'"/>
				</td>
			</tr>
		</table>
	</form>
</div>
</body>
</html>
<%@include file="../footer.jsp" %>







