package servlet;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.DAO.userDAO;

public class loginServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("login.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// ユーザーから送信されたユーザーネームとパスワードを取得する。
		String userName = request.getParameter("webUserName");
		String userPassword = request.getParameter("webUserPassword");

		// ログイン認証後に遷移する先を格納する
		String url = "";

		try {

			userDAO userDAO = new userDAO();

			boolean result = userDAO.getLogin(userName, userPassword);

			// ユーザーIDとパスワードが一致するユーザーが存在した時
			if (result == true) {

				HttpSession session = request.getSession();
				session.setAttribute("loggedInUserName", userName);
				session.setAttribute("isLoggedIn", true);
				
				request.setAttribute("displayUserName", userName);
				
				// ログイン成功画面に遷移する
				url = "loginSuccess.jsp";
			} else {
				
				request.setAttribute("loginFailure", "ログインに失敗しました");

				url = "login.jsp";
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		RequestDispatcher rd = request.getRequestDispatcher(url);
		rd.forward(request, response);
	}
}
