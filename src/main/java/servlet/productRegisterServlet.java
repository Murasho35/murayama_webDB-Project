package servlet;

import java.io.IOException;
import java.sql.SQLException;
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
			// エラーが発生した場合はエラーページにリダイレクトするか、エラーメッセージを表示
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "カテゴリ情報の取得中にデータベースエラーが発生しました。");
		}
	}

	/**
	 * POSTリクエストを処理：フォームから送信された商品データをデータベースに登録
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8"); // リクエストの文字エンコーディングをUTF-8に設定

		// フォームから各パラメータを取得
		String productName = request.getParameter("webProductName");
		String productPriceStr = request.getParameter("webProductPrice");
		String productStockStr = request.getParameter("webProductStock");
		String productCategoryIdStr = request.getParameter("webProductCategory");

		// 入力値の検証と数値への変換
		int productPrice = 0;
		int productStock = 0;
		int productCategoryId = 0; // デフォルト値は0（カテゴリなしを意味）

		try {
			productPrice = Integer.parseInt(productPriceStr);
			productStock = Integer.parseInt(productStockStr);
			productCategoryId = Integer.parseInt(productCategoryIdStr);
		} catch (NumberFormatException e) {
			// 数値変換エラーが発生した場合
			request.setAttribute("errorMessage", "価格、在庫数、カテゴリIDは数値で入力してください。");
			doGet(request, response); // エラーメッセージを表示してフォームを再表示
			return;
		}

		// productBeanオブジェクトを作成し、取得した値をセット
		productBean product = new productBean();
		// ProductIdはデータベースで自動生成されるため、ここではセットしない
		product.setProductName(productName);
		product.setProductPrice(productPrice);
		product.setProductStock(productStock);
		product.setProductCtgrId(productCategoryId); // カテゴリIDをセット

		productDAO pDao = new productDAO();

		try {

			boolean registrationSuccess = pDao.addProduct(product); // 新しい登録メソッドを想定

			if (registrationSuccess) {
				// 登録成功ページにリダイレクト
				response.sendRedirect("productRegisterSuccess.jsp"); // 成功ページのURLに合わせる
			} else {
				// 登録失敗の場合
				request.setAttribute("errorMessage", "商品の登録に失敗しました。もう一度お試しください。");
				doGet(request, response); // エラーメッセージを表示してフォームを再表示
			}

		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "データベースエラーが発生しました。入力内容を確認してください。");
			doGet(request, response); // エラーメッセージを表示してフォームを再表示
		}
	}

}
