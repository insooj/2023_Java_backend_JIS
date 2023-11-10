<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("utf-8"); %>
<% response.setContentType("text/html; charset=utf-8"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	function confirmId(resultId){
		
		var parentInputId=opener.document.getElementsByName("id")[0];//id입력박스
		var idChkSpan=opener.document.getElementById("idChk");//중복여부span
		if(resultId=='null'){
			//사용가능한 아이디이므로 다음 입력단계인 이름 입력박스로 커서 이동
			opener.document.getElementsByName("name")[0].focus();
			idChkSpan.textContent='y';//y는 사용가능한 표시 및 중복체크 완료 표시
		}else{
			idChkSpan.textContent='n';
			parentInputId.focus();//중복된 아이디이기때문에 다시 입력하기위해 커서 넣어줌
		}
		
		self.close();
	}
</script>
</head>
<body>
<%
	// request에 저장하면 Object(String)로 형변환 됨 --> 형변환해서 가져오기
	String resultId=(String)request.getAttribute("resultId");
%>
<div>     
	<span><%=resultId==null?"사용가능한 아이디입니다.":"중복된 아이디입니다." %></span>
	<span><button onclick="confirmId('<%=resultId%>')">확인</button></span>
</div>
</body>
</html>







