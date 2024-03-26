package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CartItemDAO;
import dao.DBConnection;
import dao.PaymentMethodDAO;
import dao.UserDAO;
import dao.UserDetailsDAO;
import model.CartItem;
import model.PaymentMethod;
import model.User;
import model.UserDetails;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 10L;

	private CartItemDAO cartItemDAO;
	private PaymentMethodDAO paymentMethodDAO;
	private UserDAO userDAO;
	private UserDetailsDAO userDetailsDAO;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String hashedPassword = PasswordHasher.hashPassword(password);

		HttpSession session = request.getSession();
		User user = null;
		UserDetails userDetails = null;
		PaymentMethod paymentMethod = null;
		List<CartItem> cartItems = null;

		try {
			user = userDAO.getUserByUsernamePassword(username, hashedPassword);
		} catch (SQLException e) {
			String errorMessage = "There was an error during the login, try again";
			response.sendError(500, errorMessage);
			return;

		}
		if (user != null) {
			try {
				userDetails = userDetailsDAO.getUserDetails(user.getUserId());
				paymentMethod = paymentMethodDAO.getPaymentMethod(user.getUserId());
			} catch (SQLException e) {
				String errorMessage = "There was an error during the retrieval of your data, try again";
				response.sendError(500, errorMessage);
				return;
			}

			try {
				cartItems = cartItemDAO.findCartItemsByUserId(user.getUserId());
			} catch (SQLException e) {
				String errorMessage = "There was an error during the retrieval of your cart items, try again";
				response.sendError(500, errorMessage);
				return;
			}
			List<CartItem> guestCart = (List<CartItem>) session.getAttribute("guestCart");

			if (cartItems == null) {
				cartItems = new ArrayList<>();
			}

			if (guestCart != null && !guestCart.isEmpty()) {
				for (CartItem guestCartItem : guestCart) {
					boolean productExists = false;
					for (CartItem userCartItem : cartItems) {
						if (guestCartItem.getProductId() == userCartItem.getProductId()) {
							userCartItem.setQuantity(userCartItem.getQuantity() + guestCartItem.getQuantity());
							productExists = true;
							continue;
						}
					}
					if (!productExists) {
						guestCartItem.setUserId(user.getUserId());
						cartItems.add(guestCartItem);
					}
				}
			}

			session.removeAttribute("guestCart");

			session.setAttribute("cart", cartItems);

			String sessionToken = UUID.randomUUID().toString();

			session.setAttribute("user", user);
			session.setAttribute("userDetails", userDetails);
			session.setAttribute("paymentMethod", paymentMethod);
			session.setAttribute("sessionToken", sessionToken);

			response.sendRedirect("TechBuild");
		} else {

			String errorMessage = "Incorrect username or password";
			request.setAttribute("errorMessage", errorMessage);
			request.getRequestDispatcher("Login.jsp").forward(request, response);
		}
	}

	@Override
	public void init() {
		userDAO = new UserDAO(DBConnection.getDataSource());
		userDetailsDAO = new UserDetailsDAO(DBConnection.getDataSource());
		cartItemDAO = new CartItemDAO(DBConnection.getDataSource());
		paymentMethodDAO = new PaymentMethodDAO(DBConnection.getDataSource());
	}

}
