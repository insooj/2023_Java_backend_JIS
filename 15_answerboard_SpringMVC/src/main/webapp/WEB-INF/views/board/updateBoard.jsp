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
<h1>답변형 게시판</h1>
<div id="container">
	<h2>수정하기</h2>
	<form action="updateBoard.do" method="post">
	<input type="hidden" name="seq" value="${dto.seq}"/>
	<table class="table table-striped">
		<tr>
			<th>작성자</th>
			<td>${dto.id}</td>
		</tr>
		<tr>
			<th>제목</th>
			<td><input class="form-control" type="text" name="title" value="${dto.title}" required="required"/></td>
		</tr>
		<tr>
			<th>내용</th>
			<td><textarea class="form-control" name="content" rows="10" cols="60" required="required">${dto.content}</textarea></td>
		</tr>
		<tr>
			<td colspan="2">
				<button class="btn btn-primary" type="submit">수정완료</button>
				<button class="btn btn-primary" type="button"
				        onclick="location.href='boardList.do'">목록</button>
			</td>
		</tr>
	</table>
	</form>
</div>
</body>
</html>
<%@include file="../footer.jsp" %>

