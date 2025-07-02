package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.DAO.productDAO;
import model.entity.productBean;

public class productListServlet extends HttpServlet{
	
	  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        productDAO pDao = new productDAO();

	        try {
	            // カテゴリ名を含む商品の一覧を取得
	            List<productBean> productList = pDao.getAllProductsAndCategoryName();
	            request.setAttribute("productList", productList);

	            // 商品一覧JSPにフォワード
	            request.getRequestDispatcher("/product_list.jsp").forward(request, response);

	        } catch (SQLException e) {
	            e.printStackTrace();
	            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "商品一覧の取得中にデータベースエラーが発生しました。");
	        }
	    }


}
