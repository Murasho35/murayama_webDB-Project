<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>商品一覧</title>
</head>
<body>
<h2>商品一覧画面</h2>

 <c:if test="${empty productList}">
        <p style="text-align: center;">登録されている商品はありません。</p>
    </c:if>
    <c:if test="${not empty productList}">
        <table>
            <thead>
            <table border="1">
                <tr>
                    <th>商品名</th>
                    <th>価格</th>
                    <th>在庫</th>
                    <th>カテゴリ</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="product" items="${productList}">
                    <tr>
                        <td>${product.productName}</td>
                        <td>${product.productPrice}</td>
                        <td>${product.productStock}</td>
                        <td>${product.productCtgrName}</td> 
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>
</body>
</html>