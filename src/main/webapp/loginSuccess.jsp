<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログイン成功</title>
</head>
<body>
<h2>ログイン成功！</h2>

<c:choose>
        <c:when test="${not empty requestScope.displayUserName}">
            <p>こんにちは、<c:out value="${requestScope.displayUserName}"/> さん！</p>
        </c:when>
        <c:otherwise>
            <p>ログイン情報が見つかりません。</p>
        </c:otherwise>
    </c:choose>

<input type="button" value="ログアウト" 
onclick="location.href='${pageContext.request.contextPath}/logout'">
</body>
</html>