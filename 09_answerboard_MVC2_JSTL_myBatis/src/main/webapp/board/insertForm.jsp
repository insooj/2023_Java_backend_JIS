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
	<form action="insertBoard.board" method="post">
		<table border="1">
			<tr>
				<th>작성자[ID]</th>
				<td><input type="text" name="id" pattern="^[a-zA-Z]+$" required="required"/></td>
			</tr>
			<tr>
				<th>글제목</th>
				<td><input type="text" name="title" required="required"/></td>
			</tr>
			<tr>
				<th>글내용</th>
				<td><textarea rows="10" cols="60" name="content" required="required"></textarea> </td>
			</tr>
			<tr>
				<td colspan="2">
					<input type="submit" value="등록"/>
					<input type="button" value="목록" 
							onclick="location.href='boardList.board'"/>
				</td>
			</tr>
		</table>
	</form>
</div>
</body>
</html>








