<%@page import="kh.my.board.board.model.vo.Board"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<%
	// 이곳은 자바 문법에 따름
	ArrayList<Board> volist = (ArrayList<Board>)request.getAttribute("boardvolist");
	int startPage = (int)request.getAttribute("startPage");
	int endPage = (int)request.getAttribute("endPage");
	int pageCount = (int)request.getAttribute("pageCount");
	String writer = (String)request.getSession().getAttribute("memberLoginInfo");
	%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 리스트</title>
</head>
<body>
	<h1>게시판</h1>
	<%=writer %> 님 환영합니다!
	<table border = "1">
		<tr>
			<td>번호</td>
			<td>제목</td>
			<td>작성자</td>
			<td>날짜</td>
		</tr>
		<tr>
<%
		if(volist != null) {
		for(Board vo:volist){ 
		// tr이 volist 갯수만큼 생기게 됨.
		// <%= 은 화면에 출력을 위한 표현식을 작성하는 태그, ;없어야 함.
%>
		</tr>
		<tr>
			<td><a href="boardcontent?no=<%=vo.getBno()%>"> <%=vo.getBno()%> </a></td>
			<td>
			<%
				// 답글 몇단에 따라서 Re: 붙여주기
				for(int i = 0; i<vo.getBreLevel(); i++){
			%>
					Re:
			<%
				}
			%>
			<%=vo.getTitle()%></td>
			
			
			<td><%=vo.getWriter()%></td>
			<td><%=vo.getCreateDate()%></td>
		</tr>
		
		<%
			} 		
		}
		%>
	</table>


	<%
		if (startPage > 1){
	%>
		<a href = "boardlist?pagenum=${startPage-1}">이전</a>
	<%
		}
		for (int i = startPage; i <= endPage; i++) {
	%>
	<a href="boardlist?pagenum=<%=i%>"> <%=i%>  </a>
	<%
		if (i != endPage) {
	%>
	,
	<%
		}
	}
	if (endPage < pageCount) {
	%>
		<a href = "boardlist?pagenum=${endPage+1}">다음</a>
	<%
	}
	%>


<br>
<a href = "boardwrite">글쓰기</a>


</body>
</html>