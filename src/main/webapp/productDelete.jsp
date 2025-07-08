<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>商品削除ページ</title>
</head>
<body>

<h2>商品削除画面</h2>

	<c:if test="${empty productList}">
		<p style="text-align: center;">登録されている商品はありません。</p>
	</c:if>
	<c:if test="${not empty productList}">
		<table>
			<thead>
				<table border="1">
					<tr>
						<th>商品ID</th>
						<th>商品名</th>
						<th>価格</th>
						<th>在庫</th>
						<th>カテゴリ</th>
						<th>操作</th>
					</tr>
					</thead>
					<tbody>
						<c:forEach var="product" items="${productList}">
							<tr>
								<td>${product.productId}</td>
								<td>${product.productName}</td>
								<td>${product.productPrice}</td>
								<td>${product.productStock}</td>
								<td>${product.productCtgrName}</td>
								<td>
                            <form action="./productDelete" method="post" 
                                  onsubmit="return confirm('本当に商品「${product.productName}」を削除しますか？');"
                                  style="display:inline;"> 
                                <input type="hidden" name="id" value="${product.productId}">
                                <input type="submit" value="削除"> 
                            </form>
                        </td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				</c:if>
</body>
</html>