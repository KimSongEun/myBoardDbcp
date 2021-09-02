<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글쓰기</title>
</head>
<body>
		<h1>게시판</h1>
	    <form method="get" action="boardwrite.kh">
            	제목 : <input type ="text" name ="t" ><br>
            	내용 : <input type = "text" name = "c"><br>
            <input type = "submit" value = "등록">
    </form>
</body>
</html>