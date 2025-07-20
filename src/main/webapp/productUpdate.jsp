<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>商品編集ページ</title>
<style>
    .error-message {
        color: red;
        font-weight: bold;
        margin-bottom: 10px;
    }
</style>
</head>
<body>

<body>
    <h2>商品編集</h2>

     <c:if test="${not empty errorMessages}">
        <div class="error-message">
            <ul>
                <c:forEach var="msg" items="${errorMessages}">
                    <li><c:out value="${msg}"/></li>
                </c:forEach>
            </ul>
        </div>
    </c:if>
    <form action="./productUpdate" method="post">
        <input type="hidden" name="action" value="update">
        <input type="hidden" name="id" value="${product.productId}">

        <div>
            <label>商品ID:</label>
            <span class="current-value">${product.productId}</span>
        </div>

        <div>
            <label for="productName">商品名: <span class="current-value">(現在: ${product.productName})</span></label>
            <input type="text"  name="productName" placeholder="変更後の商品名を入力" value="${product.productName}">
        </div>
        <div>
            <label for="productPrice">価格: <span class="current-value">(現在: ${product.productPrice}円)</span></label>
            <input type="number" name="productPrice" placeholder="変更後の価格を入力" min="0" value="${product.productPrice}">
        </div>
        <div>
            <label for="productStock">在庫数: <span class="current-value">(現在: ${product.productStock}個)</span></label>
            <input type="number" name="productStock" placeholder="変更後の在庫数を入力" min="0" value="${product.productStock}">
        </div>
        <div>
            <label for="productCtgrId">カテゴリ: <span class="current-value">(現在: ${product.productCtgrName})</span></label>
            <select id="productCtgrId" name="productCtgrId">
                
                <option value="0">--- 変更しない ---</option>
                
                
                <c:forEach var="category" items="${categories}"> 
                    <option value="${category.categoryId}" 
                        <c:if test="${product.productCtgrId == category.categoryId}">selected</c:if>>
                        ${category.categoryName}
                    </option>
                </c:forEach>
            </select>
        </div>
        <div>
            <input type="submit" value="保存">
            <button type="button" class="cancel-button" onclick="location.href='./productList'">キャンセル</button>
        </div>
    </form>
</body>
</html>