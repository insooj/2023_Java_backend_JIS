<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@include file="header.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
<script type="text/javascript">
	$(function(){
// 		alert("jquery 연결");
		$("#replyBtn").click(function(){
			$("#replyForm").toggle();//답글폼 보여주기
			document.getElementsByTagName("form")[0].reset();//JS 초기화
// 			$("#replyForm").reset()// X
		});
	});
</script>
<script type = "text/javascript">
	onload = function(){
		var sid = document.getElementsByTagName("td")[0].childNodes[0].nodeValue;
		console.log(sid);
		var str = "${sessionScope.ldto.id}";	
		console.log(str);	
		if(sid != str){
			document.getElementsByTagName("button")[1].style.display = 'none';
			document.getElementsByTagName("button")[2].style.display = 'none';
		}
	}

</script>
<style type="text/css">
	/*답글 폼 안보이게 처리*/
	#replyForm{
		display: none;
	}
</style>
</head>
<body>
<h1>답변형 게시판</h1>
<div id="container">
	<h2>상세보기</h2>
	<table border="1">
		<tr>
			<th>작성자</th>
			<td>${dto.id}</td>
		</tr>
		<tr>
			<th>제목</th>
			<td>${dto.title}</td>
		</tr>
		<tr>
			<th>내용</th>
			<td><textarea readonly="readonly" rows="10" cols="60">${dto.content}</textarea></td>
		</tr>
		<tr>
			<td colspan="2">
				<button type="button" id="replyBtn">답글</button>
				<button type="button"
						onclick="location.href='updateBoardForm.board?seq=${dto.seq}'">수정</button>
				<button type="button"
						onclick="location.href='mulDel.board?chk=${dto.seq}'">삭제</button>
				<button type="button"
				        onclick="location.href='boardList.board'">목록</button>
			</td>
		</tr>
	</table>
	<div id="replyForm">
		<h2>답글 작성하기</h2>
		<form action="replyBoard.board" method="post">
		<input type="hidden" name="seq" value="${dto.seq}"/>
		<table border="1">
		<tr>
			<th>작성자</th>
			<td><input name="id" type="text" value=${ldto.id} readonly="readonly" style="border: none;"/></td>
		</tr>
		<tr>
			<th>제목</th>
			<td><input type="text" name="title" required="required" /></td>
		</tr>
		<tr>
			<th>내용</th>
			<td><textarea name="content" required="required" rows="10" cols="60"></textarea></td>
		</tr>
		<tr>
			<td colspan="2">
				<button type="submit">답글등록</button>
				<button type="button"
				        onclick="location.href='boardList.board'">목록</button>
			</td>
		</tr>
	</table>
	</form>
	</div>
</div>
</body>
</html>










