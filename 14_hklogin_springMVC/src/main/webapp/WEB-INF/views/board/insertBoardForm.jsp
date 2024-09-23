<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%request.setCharacterEncoding("utf-8"); %>
<%response.setContentType("text/html; charset=utf-8"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>새글 작성하기</title>
</head>
<body>
<h1>새글 작성하기</h1>
<form action="insertBoard.do" method="post">

	<table border="1">
		<tr>
			<th>작성자(ID)</th>
			<td><input type="text" name="id" required="required" /></td>
		</tr>
		<tr>
			<th>글제목</th>
			<td><input type="text" name="title" required="required" /></td>
		</tr>
		<tr>
			<th>글내용</th>
			<td><textarea rows="10" cols="60" name="content" required="required"></textarea></td>
		</tr>
		<tr>
			<td colspan="2">
				<input type="submit" value="등록" />
				<input type="button" value="글목록"
						onclick="location.href='boardList.do'" />
			</td>
		</tr>
	</table>
</form>
</body>
</html>


















