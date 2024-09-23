<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
	a{text-decoration: none;}
	.active{
		background-color: yellow;
	}
</style>
<script type="text/javascript">
//전체선택박스구현
function allSel(bool){
	var chks=document.getElementsByName("chk");// [chk,chk,chk,chk..]
	for(var i=0;i<chks.length;i++){
		chks[i].checked=bool;//true면 체크, false 체크해제
	}
}

//체크박스의 체크여부 확인하고, submit 실행하기
//true를 반환하면 submit, false submit X
function isAllCheck(){
	if(confirm("정말 삭제할꺼야?")){
		var count=0;
		var chks=document.getElementsByName("chk");//[chk,chk,chk,chk..]
		for(var i=0;i<chks.length;i++){
			if(chks[i].checked){//체크여부확인: 체크되면 true
				count++;
			}
		}
		if(count==0){
			alert("최소 하나이상 체크하세요");
			return false;
		}
	}
	return true;
}
</script>
</head>
<body>
<jsp:useBean id="util" class="com.hk.ans.util.Util" />
<h1>답변형 게시판</h1>
<div id="container">
	<h2>글목록조회</h2>
	<form action="mulDel.board" method="post" onsubmit="return isAllCheck()">
	<table border="1">
		<tr>
			<th><input type="checkbox" name="all" onclick="allSel(this.checked)" /> </th>
			<th>번호</th>
			<th>작성자</th>
			<th>제목</th>
			<th>조회수</th>
			<th>작성일</th>
			<th>삭제여부</th>
			<th>refer</th>
			<th>step</th>
			<th>depth</th>
		</tr>
		<c:set var="lists" value="${list}" />
		<c:choose>
			<c:when test="${empty lists}">
				<tr>
					<td colspan="10">--작성된 글이 없습니다.--</td>
				</tr>
			</c:when>
			<c:otherwise>
				<c:forEach items="${lists}" var="dto">
					<tr>
						<td>
							<input type="checkbox" name="chk" value="${dto.seq}"/>
						</td>
						<td>${dto.seq}</td>
						<td>${dto.id}</td>
						<td>                                                                          
						<c:set var="title" 
						value="${fn:length(dto.title)>10?fn:substring(dto.title,0,10)+='...':dto.title}" />
						<c:choose>
							<c:when test="${dto.delflag=='Y'}">
								---삭제된 글입니다.---
							</c:when>
							<c:otherwise>
<%-- 								<c:forEach begin="1" end="${dto.depth}" var="i"  step="1"> --%>
<!-- 									&nbsp;&nbsp;&nbsp;&nbsp; -->
<%-- 									<c:if test="${i==dto.depth}"> --%>
<!-- 										<img width="10px" height="10px" src="img/arrow_icon.png" alt="답글"/> -->
<%-- 									</c:if> --%>
<%-- 								</c:forEach> --%>
								<jsp:setProperty property="arrowNbsp" name="util" value="${dto.depth}"/>
								<jsp:getProperty property="arrowNbsp" name="util"/>
								<a href="detailBoard.board?seq=${dto.seq}">${title}</a>
							</c:otherwise>
						</c:choose>
						</td>
						<td>${dto.readCount}</td>
						<td><fmt:formatDate value="${dto.regDate}" pattern="yyyy년MM월dd" /> </td>
						<td>${dto.delflag}</td>
						<td>${dto.refer}</td>
						<td>${dto.step}</td>
						<td>${dto.depth}</td>
					</tr>
				</c:forEach>				
			</c:otherwise>
		</c:choose>
		<tr>
			<td colspan="10" style="text-align: center;">
				
<%-- 				<c:forEach begin="1" end="${pcount}" var="i" step="1"> --%>
<%-- 					<a ${(sessionScope.pnum == i||param.pnum == i)?"class='active'":""}  href="boardList.board?pnum=${i}">${i}</a>&nbsp;&nbsp; --%>
<%-- 				</c:forEach> --%>
				<a href="#">◀</a>
				<c:forEach begin="1" end="${pcount}" var="i" step="1">
					<a ${(sessionScope.pnum == i||param.pnum == i)?"class='active'":""}  href="boardList.board?pnum=${i}">${i}</a>&nbsp;&nbsp;
				</c:forEach>
				<a href="#">▶</a>
			</td>
		</tr>
		<tr>
			<td colspan="10">
				<button type="button" onclick="location.href='insertForm.board'">글추가</button>
				<button type="submit">삭제</button>
			</td>
		</tr>
	</table>
	</form>
</div>
<%-- <jsp:useBean id="util" class="com.hk.ans.util.Util"/> --%>
<%-- <jsp:setProperty property="id" name="util" value="${dto.depth}"/> --%>
<!-- setId()실행 -->
<%-- <jsp:getProperty property="id" name="util"/> --%>
<!-- getId()실행 -->
<!-- Util util=new Util() -->
<!-- util.method() -->
</body>
</html>

















