<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("utf-8"); %>
<% response.setContentType("text/html; charset=utf-8"); %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/loginpage.css" />
<script type="text/javascript" src="js/cookieFunc.js"></script>
<script type="text/javascript">
	//회원가입폼 이동
	function registForm(){
		location.href="userController.jsp?command=registForm";
	}
	
	//아이디를 쿠키에 저장
	function userIdCookie(){
		//chkID는 아이디 저장 체크박스 객체임
		var chkID=document.querySelectorAll("input[type=checkbox]")[0];
		//화면에 입력된 ID값 가져오기
		var id=document.forms[0].id.value;//forms속성이용
// 		var id=document.getElementsByName("id")[0].value;//dom탐색메서드이용
		if(chkID.checked){//체크여부 확인하여 쿠키체 id 저장하기
			setCookie("rememberid",id,100);
		}
		return true;
	}
	
	onload=function(){
		var cookieID=getCookie("rememberid");
		if(cookieID!=null){
			//id입력박스에 cookie에서 가져온 값을 입력한다.
			document.getElementsByName("id")[0].value=cookieID;
		}
	}
</script>
</head>
<% String msg=request.getParameter("msg"); %>
<body>
<form action="userController.jsp" method="post" onsubmit="return userIdCookie()" >
	<input type="hidden" name="command" value="login"/>
	<h1>Login in</h1>
	<input class="loginVal" type="text" name="id" placeholder="ID" required="required"/>
	<input class="loginVal" type="password" name="password" placeholder="PASSWORD" required="required"/>
	<input type="checkbox" value="remember-me"/><label>아이디 저장</label>
	<label style="color:red;"><small><%=msg==null?"":msg%></small></label>
	<button type="submit">Sign in</button>
	<button type="button" onclick="registForm()">Sign Up</button>
</form>
</body>
</html>











