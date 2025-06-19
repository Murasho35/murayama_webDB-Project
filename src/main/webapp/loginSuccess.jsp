<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログイン成功</title>
</head>
<body>
<h2>ログイン成功！</h2>

<input type="button" value="ログアウト" 
onclick="location.href='${pageContext.request.contextPath}/logout'">
</body>
</html>