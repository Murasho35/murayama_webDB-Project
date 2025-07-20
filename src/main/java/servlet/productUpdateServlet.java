package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.DAO.categoryDAO;
import model.DAO.productDAO;
import model.entity.CategoryBean;
import model.entity.productBean;

@WebServlet("/productUpdate")
public class productUpdateServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		// URLからactionパラメータとidパラメータを取得
		String action = request.getParameter("action");
		String productIdStr = request.getParameter("id");

		productDAO pDao = new productDAO();
		categoryDAO cDao = new categoryDAO();

		try {
			// 全てのカテゴリを取得してJSPに渡す
			List<CategoryBean> categoryList = cDao.getAllCategory();

			request.setAttribute("categories", categoryList);

			if ("edit".equals(action) && productIdStr != null && !productIdStr.isEmpty()) {
				int productId = Integer.parseInt(productIdStr);

				productBean productUpdate = pDao.getProductById(productId);

				if (productUpdate != null) {
					
					 CategoryBean categoryBean = cDao.getCategoryById(productUpdate.getProductCtgrId());
			            if (categoryBean != null) {
			                productUpdate.setProductCtgrName(categoryBean.getCategoryName()); // productBeanにカテゴリ名をセット
			            } else {
			                productUpdate.setProductCtgrName("カテゴリなし"); 
			            }
					
					// 取得した商品情報をリクエストスコープにセット
					request.setAttribute("product", productUpdate);
					// 商品編集JSPにフォワード
					request.getRequestDispatcher("/productUpdate.jsp").forward(request, response);
				} else {
					// 商品が見つからない場合
					request.getSession().setAttribute("errorMessage", "指定された商品IDの商品が見つかりませんでした。");
					response.sendRedirect("./productList"); // 商品一覧に戻す

				}
			} else {
				// action="edit"でない、またはproductIdStrがない/空の場合
				// 例：/productUpdate に直接アクセスされた場合など
				request.getSession().setAttribute("errorMessage", "無効なリクエストです。編集対象の商品を選択してください。");
				response.sendRedirect("./productList"); // 商品一覧に戻す
			}
		} catch (SQLException e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "カテゴリ情報の取得中にデータベースエラーが発生しました。");
		}

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String action = request.getParameter("action");
		String productIdStr = request.getParameter("id");

		productDAO pDao = new productDAO();
		categoryDAO cDao = new categoryDAO();

		String productName = request.getParameter("productName");
		String productPriceStr = request.getParameter("productPrice");
		String productStockStr = request.getParameter("productStock");
		String productCategoryIdStr = request.getParameter("productCtgrId");

		List<String> errorMessages = new ArrayList<>();

		// 入力値の検証と数値への変換
		int productPrice;
		int productStock;
		int productCategoryId;

		//もともとの情報をもってくる
		int productId = 0;
		try {
			productId = Integer.parseInt(productIdStr);
		} catch (NumberFormatException e) {
			request.getSession().setAttribute("errorMessage", "無効な商品IDが指定されました。");
			response.sendRedirect("./productList");
		}

		productBean originalProduct = null;

		try {
			originalProduct = pDao.getProductById(productId);
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			request.getSession().setAttribute("errorMessage", "無効な商品IDが指定されました。");
			response.sendRedirect("./productList");
			return;
		}

		productBean product = new productBean();

		product.setProductId(originalProduct.getProductId());
		product.setProductName(originalProduct.getProductName());
		product.setProductPrice(originalProduct.getProductPrice());
		product.setProductStock(originalProduct.getProductStock());
		product.setProductCtgrId(originalProduct.getProductCtgrId());

		//名前の更新
		if (productName != null && !productName.isEmpty()) {
			product.setProductName(productName);
		}

		//値段の更新
		if (productPriceStr != null && !productPriceStr.isEmpty()) {
			try {
				product.setProductPrice(Integer.parseInt(productPriceStr));
			} catch (NumberFormatException e) {
				// 数値変換エラーが発生した場合
				errorMessages.add("価格は数値で入力してください。");
			}

		}

		//在庫の更新
		if (productStockStr != null && !productStockStr.isEmpty()) {

			try {
				product.setProductStock(Integer.parseInt(productStockStr));
			} catch (NumberFormatException e) {
				errorMessages.add("在庫数は数値で入力してください。"); // エラーメッセージをリストに追加
			}

		}

		//カテゴリの更新
		if (productCategoryIdStr != null && !productCategoryIdStr.isEmpty()) {

			try {
				productCategoryId = Integer.parseInt(productCategoryIdStr);
				 if (productCategoryId != 0) { // "0" (変更しない) 以外の値が選択された場合のみ更新
	                    product.setProductCtgrId(productCategoryId);
	                }
			} catch (NumberFormatException e) {
				errorMessages.add("カテゴリIDが不正です。"); // エラーメッセージをリストに追加

			}

		}

		if (!errorMessages.isEmpty()) {
            // エラーがある場合、エラーメッセージと入力済みデータをJSPにフォワード
            request.setAttribute("errorMessages", errorMessages); // 複数形に変更
            request.setAttribute("product", product); // ユーザーの入力値を保持
            try { // カテゴリリストも再取得してJSPに渡す
                List<CategoryBean> categoryList = cDao.getAllCategory();
                request.setAttribute("categories", categoryList);
            } catch (SQLException sqle) {
                sqle.printStackTrace();
                errorMessages.add("カテゴリ情報の取得中にエラーが発生しました。"); 
            }
            request.getRequestDispatcher("/productUpdate.jsp").forward(request, response);
            return; 
        }
		
		try {

			boolean updateSuccess = pDao.updateProduct(product);

			if (updateSuccess) {
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
