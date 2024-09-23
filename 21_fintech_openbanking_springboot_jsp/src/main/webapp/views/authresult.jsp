<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<div id="accessToken">${access_token}</div>
<div id="refreshToken">${refresh_token}</div>
<div id="userseqNo">${user_seq_no}</div>
<script type="text/javascript">
	opener.document.getElementsByName("useraccesstoken")[0].value
	=document.getElementById("accessToken").textContent;
	
	opener.document.getElementsByName("userrefreshtoken")[0].value
	=document.getElementById("refreshToken").textContent;
	
	opener.document.getElementsByName("userseqno")[0].value
	=document.getElementById("userseqNo").textContent;
	
	self.close();
</script>

</body>
</html>






