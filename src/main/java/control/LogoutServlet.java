package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CartItemDAO;
import dao.DBConnection;
import model.CartItem;

@WebServlet({ "/logout", "/forceLogout" })
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 9L;
	private CartItemDAO cartItemDAO;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		String servletPath = request.getServletPath();

		if (servletPath.equals("/logout")) {
			if (session != null) {
				List<CartItem> cartItems = (List<CartItem>) session.getAttribute("cart");

				if (cartItems != null && !cartItems.isEmpty()) {
					try {
						cartItemDAO.saveAllCartItem(cartItems);
					} catch (SQLException e) {
						String errorMessage = "There was an error during the logout, your items in the cart didn't make it to the database";
						response.sendError(500, errorMessage);
						return;

					}
				}

			}

		} else if (servletPath.equals("forceLogout")) {
			session.invalidate();
			response.sendRedirect("TechBuild");

			return;
		}
		session.invalidate();

		response.sendRedirect("TechBuild");

	}

	@Override
	public void init() throws ServletException {
		cartItemDAO = new CartItemDAO(DBConnection.getDataSource());
	}
}
