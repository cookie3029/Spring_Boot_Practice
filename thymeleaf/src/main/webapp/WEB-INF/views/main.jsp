<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<!-- c로 시작하는 태그는 http://java.sun.com/jsp/jstl/core에서 해석한 내용으로 변경 -->
<!-- 여기서 uri은 웹 주소 형태로 되어 있지만 실제로는 jstl.jar 파일에서 해석을 합니다. -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta charset="UTF-8">
<title>Spring에서 JSP 출력</title>
</head>
<body>
	<div>
		<table>
			<tr>
				<th>언어</th>
				<th>데이터베이스</th>
			</tr>
			<tr>
				<td>${map.language}</td>
				<td>${map.database}</td>
			</tr>
		</table>
	</div>
	<div>
		<table>
			<c:forEach items="${list}" var="task">
				<tr>
					<td>${task}</td>
				</tr>
			</c:forEach>
		</table>
	</div>
</body>
</html>