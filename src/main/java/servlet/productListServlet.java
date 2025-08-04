package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.DAO.productDAO;
import model.entity.productBean;


@WebServlet("/productList")
public class productListServlet extends HttpServlet {


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		HttpSession session = request.getSession(false); // セッションが存在しない場合は作成しない
		if (session == null || session.getAttribute("isLoggedIn") == null || !(boolean) session.getAttribute("isLoggedIn")) {
		    // ログインしていない場合、ログインページにリダイレクト
		    response.sendRedirect(request.getContextPath() + "/login");
		    return; // 処理を終了
		}
		
		
		productDAO pDao = new productDAO();

		try {
			// カテゴリ名を含む商品の一覧を取得
			List<productBean> productList = pDao.getAllProductsAndCategoryName();
			request.setAttribute("productList", productList);

			// 商品一覧JSPにフォワード
			request.getRequestDispatcher("/productList.jsp").forward(request, response);

		} catch (SQLException e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "商品一覧の取得中にデータベースエラーが発生しました。");
		}
	}


}
