<%@include file="../header.jsp" %>
<%@page import="java.time.LocalTime"%>
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
<link rel="stylesheet" href="./css/test.css?<%=LocalTime.now()%>" type="text/css" >
<style type="text/css">
/* 	@import url("css/layout1.css"); */
	a{text-decoration: none;}
	.active{
		background-color: yellow;
	}
/*  	table{  */
/*  		border:1px solid gray;  */
/*  		border-collapse: collapse;  */
/*  	}  */
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
//jquery ajax()사용하여 구현
$(function(){ //onload이벤트 기능 --> window.onload=function(){}
// 	alert("test");
	//상세내용 조회: ajax로 구현
	//해당 제목에 마우스 올리면 내용 보여주기
	$("td>a").hover(function(){
		//상세조회하려면 seq값이 필요함: detailBoard.do?seq=21
		//  a      td	  td      td	
		var seq=$(this).parent().prev().prev().text();
		$.ajax({
			url:"detailBoardAjax.do", //요청 URL
			method:"post", //전송방식
			data:{"seq":seq},//서버로 전송할 값
			dataType:"json",//받을 값의 타입
			async:false,//ajax메서드를 동기식으로 실행
			success:function(obj){//통신 성공하면 실행
				//obj={"dto":{seq:5,id:"hk",title:"제목"..}}
// 				alert(obj["dto"]["regDateStr"]);
				$("#regdate").val(obj.dto.regDateStr);
				$("#id").val(obj.dto.id);
				$("#title").val(obj.dto.title);
				$("#content").val(obj.dto.content);
			},error:function(){
				alert("통신실패");
			}
		});
	},function(){//마우스가 나갔을때..
		$("#regdate").val("");
		$("#id").val("");
		$("#title").val("");
		$("#content").val("");
	});
});
</script>
</head>
<body>
<jsp:useBean id="util" class="com.hk.ansboard.utils.Util" />
<h1>답변형 게시판</h1>
<div id="container">
	<h2>글목록조회</h2>
	<form action="mulDel.do" method="post" onsubmit="return isAllCheck()">
	<table class="table table-striped">
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
								<jsp:setProperty property="arrowNbsp" name="util" value="${dto.depth}"/>
								<jsp:getProperty property="arrowNbsp" name="util"/>
								<a href="detailBoard.do?seq=${dto.seq}">${title}</a>
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
<%-- 				<a href="boardList.do?pnum=${pMap.prePageNum}">◀</a> --%>
<%-- 				<c:forEach begin="${pMap.startPage}" end="${pMap.endPage}" var="i" step="1"> --%>
<%-- 					<a ${(sessionScope.pnum == i||param.pnum == i)?"class='active'":""}  href="boardList.do?pnum=${i}">${i}</a>&nbsp;&nbsp; --%>
<%-- 				</c:forEach> --%>
<%-- 				<a href="boardList.do?pnum=${pMap.nextPageNum}">▶</a> --%>
				<!-- 페이징 처리부분 시작 -->
				<nav>
				  <ul class="pagination">
				    <li ><a href="boardList.do?pnum=${pMap.prePageNum}" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
				    <c:forEach var="i" begin="${pMap.startPage}" end="${pMap.endPage}">
				    	<li ${sessionScope.pnum==i?"class='active'":""}><a href="boardList.do?pnum=${i}">${i}<span class="sr-only">(current)</span></a></li>
				    </c:forEach> 
				    <li ><a href="boardList.do?pnum=${pMap.nextPageNum}" aria-label="Next"><span aria-hidden="true">&raquo;</span></a></li>
				  </ul>
				</nav>
				<!-- 페이징 처리부분 종료 -->
			</td>
		</tr>
		<tr>
			<td colspan="10">
				<button class="btn btn-primary" type="button" onclick="location.href='insertForm1.do'">글추가</button>
				<button class="btn btn-primary"  type="submit">삭제</button>
			</td>
		</tr>
	</table>
	</form>
	<div id="ajaxForm">
		<label>작성일:</label><input class="form-control" type="text" id="regdate">
		<label>작성자:</label><input class="form-control" type="text" id="id">
		<label>제목:</label><input class="form-control" type="text" id="title">
		<label>내용:</label><textarea class="form-control" rows="3" cols="60" id="content"></textarea>
		<label></label>
	</div>
</div>

</body>
</html>
<%@include file="../footer.jsp" %>
















