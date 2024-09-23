<%@page import="java.util.List"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="com.hk.user.dtos.UserDto"%>
<%@page import="com.hk.user.daos.UserDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("utf-8"); %>
<% response.setContentType("text/html; charset=utf-8"); %>   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	String command=request.getParameter("command");

	UserDao dao=UserDao.getUserDao();//클래스명.메서드():static메서드 호출방법
	
	if(command.equals("getAllUserList")){//회원전체조회
		List<UserDto>list=dao.getAllUserList();
	
		request.setAttribute("list", list);
		
		pageContext.forward("userAllList.jsp");
	}else if(command.equals("getUserList")){//회원목록조회[등급수정을 위한 조회]
		List<UserDto>list=dao.getUserList();
	
		request.setAttribute("list", list);
		pageContext.forward("userList.jsp");
	}else if(command.equals("roleForm")){//등급수정폼으로 이동
		String id=request.getParameter("id");
		UserDto dto=dao.getUserInfo(id);//나의정보조회하기 기능
		
		request.setAttribute("dto", dto);
		pageContext.forward("userRoleForm.jsp");//등급수정 폼으로 이동
	}else if(command.equals("userUpdateRole")){//등급수정하기
		String id=request.getParameter("id");
		String role=request.getParameter("role");
		
		boolean isS=dao.userUpdateRole(id, role);
		if(isS){
			response.sendRedirect("userController.jsp?command=getUserList");
		}else{
			response.sendRedirect("error.jsp?msg="+URLEncoder.encode("등급수정실패","utf-8"));
		}
	}
%>
</body>
</html>


















