<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
 <c:if test="${empty productList}">
        <p style="text-align: center;">登録されている商品はありません。</p>
    </c:if>
    <c:if test="${not empty productList}">
        <table>
            <thead>
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
                        <td>${product.productPrice}円</td>
                        <td>${product.productStock}個</td>
                        <td>${product.categoryName}</td> <%-- CategoryBeanのcategoryNameがセットされる想定 --%>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>
</body>
</html>