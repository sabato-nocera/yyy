package control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.CartItem;
import model.User;

@WebServlet("/AddToCart")
public class AddToCartServlet extends HttpServlet {

	private static final long serialVersionUID = 14L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String referer = request.getHeader("referer");

		int productId = Integer.parseInt(request.getParameter("productId"));
		int quantity = Integer.parseInt(request.getParameter("quantity"));

		HttpSession session = request.getSession();

		if (quantity <= 0) {
			String errorMessage = "invalid quantity";
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, errorMessage);
			return;
		}

		List<CartItem> cart = null;
		int userId = 0;
		if (((User) session.getAttribute("user")) == null) {
			cart = (List<CartItem>) session.getAttribute("guestCart");

			session.setAttribute("guestCart", cart);
		} else {
			cart = (List<CartItem>) session.getAttribute("cart");
			userId = ((User) session.getAttribute("user")).getUserId();

		}
		if (cart == null) {
			cart = new ArrayList<>();
		}

		boolean productExists = false;
		for (CartItem cartItem : cart) {
			if (cartItem.getProductId() == productId) {
				cartItem.setQuantity(cartItem.getQuantity() + quantity);
				productExists = true;

				break;
			}
		}

		if (!productExists) {
			CartItem newCartItem = new CartItem();
			newCartItem.setProductId(productId);
			newCartItem.setUserId(userId);
			newCartItem.setQuantity(quantity);
			cart.add(newCartItem);

		}
		if (((User) session.getAttribute("user")) == null) {

			session.setAttribute("guestCart", cart);
		} else {
			session.setAttribute("cart", cart);

		}
		response.sendRedirect(referer);
	}

}
