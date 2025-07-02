<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>商品登録ページ</title>
</head>
<body>
	<form action="./productRegister" method="post">
		<div>
			<label>商品名</label> <input type="text" name="webProductName">
		</div>
		<div>
			<label>価格</label> <input type="text" name="webProductPrice">
		</div>
		<div>
			<label>在庫数</label> <input type="text" name="webProductStock">
		</div>
		<div>
            <label for="webProductCategory">カテゴリ</label>
            <select id="webProductCategory" name="webProductCategory">
                <%-- 「カテゴリなし」のオプション (値は0で送信) --%>
                <option value="0">--- カテゴリなし ---</option>
                
                <%-- サーブレットから渡されたカテゴリリストをループで表示 --%>
                <c:forEach var="category" items="${categories}">
                    <option value="${category.categoryId}">${category.categoryName}</option>
                </c:forEach>
            </select>
        </div>
		<div>
			<input type="submit" value="登録">
		</div>
	</form>
</body>
</html>