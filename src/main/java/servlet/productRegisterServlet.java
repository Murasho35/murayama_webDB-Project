package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.DAO.categoryDAO;
import model.DAO.productDAO;
import model.entity.CategoryBean;
import model.entity.productBean;

public class productRegisterServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		categoryDAO cDao = new categoryDAO();

		try {
			// 全てのカテゴリを取得してJSPに渡す
			List<CategoryBean> categoryList = cDao.getAllCategory();

			request.setAttribute("categories", categoryList);

			// 商品登録JSPにフォワード
			request.getRequestDispatcher("/productRegister.jsp").forward(request, response);

		} catch (SQLException e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "カテゴリ情報の取得中にデータベースエラーが発生しました。");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8"); // リクエストの文字エンコーディングをUTF-8に設定

		// フォームから各パラメータを取得
		String productName = request.getParameter("webProductName");
		String productPriceStr = request.getParameter("webProductPrice");
		String productStockStr = request.getParameter("webProductStock");
		String productCategoryIdStr = request.getParameter("webProductCategory");

		List<String> errorMessages = new ArrayList<>(); // エラーメッセージをリストで管理

		// 入力値の空文字チェック

		boolean hasNameError = false;
		boolean hasPriceError = false;
		boolean hasStockError = false;
		boolean hasCategoryError = false; 

		// 1. 商品名チェック (空の場合)
		if (productName == null || productName.trim().isEmpty()) {
			errorMessages.add("商品名を入力してください。");
			hasNameError = true;
		}

		// 2. 価格チェック (空の場合 または 半角数字以外の場合)
		if (productPriceStr == null || productPriceStr.trim().isEmpty()) {
			errorMessages.add("価格は半角数字を入力してください。"); 
			hasPriceError = true;
		} else {
			try {
				Integer.parseInt(productPriceStr);
			} catch (NumberFormatException e) {
				if (!hasPriceError) { 
					errorMessages.add("価格は半角数字を入力してください。");
					hasPriceError = true;
				}
			}
		}

		// 3. 在庫数チェック (空の場合 または 半角数字以外の場合)
		if (productStockStr == null || productStockStr.trim().isEmpty()) {
			errorMessages.add("在庫数は半角数字を入力してください。");
			hasStockError = true;
		} else {
			try {
				Integer.parseInt(productStockStr);
			} catch (NumberFormatException e) {
				if (!hasStockError) { 
					errorMessages.add("在庫数は半角数字を入力してください。");
					hasStockError = true;
				}
			}
		}

		// 4. カテゴリチェック (空の場合 または 数値変換できない場合)
		if (productCategoryIdStr == null || productCategoryIdStr.trim().isEmpty()) {
			errorMessages.add("カテゴリを選択してください。"); 
			hasCategoryError = true;
		} else {
			try {
				Integer.parseInt(productCategoryIdStr); 
			} catch (NumberFormatException e) {
				if (!hasCategoryError) { 
					errorMessages.add("カテゴリを選択してください。");
					hasCategoryError = true;
				}
			}
		}

		if (!errorMessages.isEmpty()) {
			request.setAttribute("errorMessages", errorMessages); 

			categoryDAO cDao = new categoryDAO();
			try {
				List<CategoryBean> categoryList = cDao.getAllCategory();
				request.setAttribute("categories", categoryList);
			} catch (SQLException e) {
				e.printStackTrace();
				errorMessages.add("カテゴリ情報の取得中にデータベースエラーが発生しました。");
			}
			productBean inputProduct = new productBean();
			inputProduct.setProductName(productName);
			if (!hasPriceError) {
				try {
					inputProduct.setProductPrice(Integer.parseInt(productPriceStr));
				} catch (NumberFormatException ignore) {
				}
			}
			if (!hasStockError) {
				try {
					inputProduct.setProductStock(Integer.parseInt(productStockStr));
				} catch (NumberFormatException ignore) {
				}
			}
			if (!hasCategoryError) {
				try {
					inputProduct.setProductCtgrId(Integer.parseInt(productCategoryIdStr));
				} catch (NumberFormatException ignore) {
				}
			}
			request.setAttribute("product", inputProduct);

			request.getRequestDispatcher("/productRegister.jsp").forward(request, response);
			return; 
		}


		int productPrice = Integer.parseInt(productPriceStr);
		int productStock = Integer.parseInt(productStockStr);
		int productCategoryId = Integer.parseInt(productCategoryIdStr);

		// productBeanオブジェクトを作成し、取得した値をセット
		productBean product = new productBean();

		product.setProductName(productName);
		product.setProductPrice(productPrice);
		product.setProductStock(productStock);
		product.setProductCtgrId(productCategoryId); // カテゴリIDをセット

		productDAO pDao = new productDAO();

		try {

			boolean registrationSuccess = pDao.addProduct(product);

			if (registrationSuccess) {
				// 商品一覧ページに飛びたい
				response.sendRedirect("./productList");
			} else {
				// 登録失敗の場合
				request.setAttribute("errorMessage", "商品の登録に失敗しました。もう一度お試しください。");
				doGet(request, response);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "データベースエラーが発生しました。入力内容を確認してください。");
			doGet(request, response);
		}
	}

}
