package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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
import model.User;

@WebServlet("/removeItem")
public class RemoveItemServlet extends HttpServlet {
	private static final long serialVersionUID = 3L;

	private CartItemDAO cartItemDAO;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String referer = request.getHeader("referer");

		int productId = Integer.parseInt(request.getParameter("productId"));

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		List<CartItem> cart;
		if (user == null) {
			List<CartItem> itemsToRemove = new ArrayList<>();
			cart = (List<CartItem>) session.getAttribute("guestCart");
			for (CartItem cartItem : cart) {
				if (cartItem.getProductId() == productId) {
					itemsToRemove.add(cartItem);
					continue;
				}
			}
			cart.removeAll(itemsToRemove);
			session.setAttribute("guestCart", cart);

		} else {

			int userId = user.getUserId();
			cart = (List<CartItem>) session.getAttribute("cart");
			List<CartItem> itemsToRemove = new ArrayList<>();

			for (CartItem cartItem : cart) {
				if (cartItem.getProductId() == productId) {
					itemsToRemove.add(cartItem);
					try {
						cartItemDAO.removeCartItem(userId, cartItem.getProductId());
					} catch (SQLException e) {
						String errorMessage = "There was an error in removing your items from the cart";
						response.sendError(500, errorMessage);
						return;

					}
					continue;
				}
			}
			cart.removeAll(itemsToRemove);

			session.setAttribute("cart", cart);

		}

		response.sendRedirect(referer);
	}

	@Override
	public void init() {
		cartItemDAO = new CartItemDAO(DBConnection.getDataSource());
	}

}
