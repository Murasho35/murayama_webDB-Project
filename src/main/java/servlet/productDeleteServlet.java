package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.DAO.productDAO;
import model.entity.productBean;

@WebServlet("/productDelete")
public class productDeleteServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
;
		productDAO pDao = new productDAO();

		try {

			// カテゴリ名を含む商品の一覧を取得

			List<productBean> productList = pDao.getAllProductsAndCategoryName();

			request.setAttribute("productList", productList);

			// 商品一覧JSPにフォワード

			request.getRequestDispatcher("/productDelete.jsp").forward(request, response);

		} catch (SQLException e) {

			e.printStackTrace();

			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "商品一覧の取得中にデータベースエラーが発生しました。");

		}

		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String productIdStr = request.getParameter("id");
		int productId = 0;

		try {
			productId = Integer.parseInt(productIdStr);
		} catch (NumberFormatException e) {
			request.getSession().setAttribute("errorMessage", "無効な商品IDが指定されました。");
			response.sendRedirect("./productList");
			return;
		}

		productDAO pDao = new productDAO();
		try {
			boolean deleteSuccess = pDao.deleteProduct(productId);

			if (deleteSuccess) {
				request.getSession().setAttribute("successMessage", "商品が正常に削除されました。");
			} else {
				request.getSession().setAttribute("errorMessage", "商品の削除に失敗しました。商品が見つからないか、処理に問題がありました。");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			request.getSession().setAttribute("errorMessage", "データベースエラーにより商品の削除に失敗しました。");
		}

		// 削除後、商品一覧ページにリダイレクト 
		response.sendRedirect("./productList");

	}

}
