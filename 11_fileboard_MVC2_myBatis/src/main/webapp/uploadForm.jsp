<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>파일업로드</h1>
<form action="fileUpload.file" method="post" 
							   enctype="multipart/form-data">
	<table border="1">
		<tr>
			<th>파일:</th>
			<td><input type="file" name="filename" /> </td>
		</tr>
		<tr>
			<th>다운로드(test확인용):</th>    
			<td><a href="upload/${param.filename}">${param.filename}</a> </td>
		</tr>       
		<tr>
			<th>다운로드:</th>    
			<td><a href="downloadList.file">파일목록보기</a></td>
		</tr>              
		<tr>
			<td colspan="2"><input type="submit" value="업로드"/> </td>
		</tr>
	</table>
</form>
<hr/>
<h1>파일업로드</h1>
<form action="fileMultiUpload.multi" method="post" 
							   enctype="multipart/form-data">
	<table border="1">
		<tr>
			<th>파일:</th>
			<td>
			<input type="file" name="filename" multiple="multiple"/><br/>
				<input type="file" name="filename1" /><br/> 
				<input type="file" name="filename2" /><br/> 
				<input type="file" name="filename3" />
			</td>
		</tr>
		<tr>
			<th>다운로드:</th>    
			<td><a href="downloadList.file">파일목록보기</a></td>
		</tr>              
		<tr>
			<td colspan="2"><input type="submit" value="업로드"/> </td>
		</tr>
	</table>
</form>
</body>
</html>









