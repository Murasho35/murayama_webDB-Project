<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

</head>
<body>
<h2>商品管理</h2>
<h2>ログイン画面</h2>

<!-- エラーメッセージが存在するときだけ表示する -->
<% if (request.getAttribute("loginFailure") != null) {%>
    <%=request.getAttribute("loginFailure") %>
<%} %>

	<form action="./login" method="post">
		<div>
			<label>ユーザー名</label> <input type="text" name="webUserName">
		</div>
		<div>
			<label>パスワード</label> <input type="text" name="webUserPassword">
		</div>
		<div>
			<input type="submit" value="ログイン">
		</div>
	</form>
</body>
</html>