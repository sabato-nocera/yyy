package control;

import java.io.IOException;
import java.math.BigDecimal;
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
import dao.OrderDAO;
import dao.PaymentDAO;
import dao.ProductDAO;
import model.CartItem;
import model.Order;
import model.Payment;
import model.PaymentMethod;
import model.Product;
import model.User;
import model.UserDetails;

@WebServlet("/Checkout")
public class CheckoutServlet extends HttpServlet {
	private static final long serialVersionUID = 12L;
	private CartItemDAO cartItemDAO;
	private OrderDAO orderDAO;
	private PaymentDAO paymentDAO;
	private ProductDAO productDAO;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		String sessionToken = ((String) session.getAttribute("sessionToken"));
		String clientToken = (request.getParameter("clientToken"));
		BigDecimal totalPrice = new BigDecimal(request.getParameter("totalPrice"));

		User user = (User) session.getAttribute("user");
		java.sql.Date localDate = new java.sql.Date(System.currentTimeMillis());

		if (user == null || !sessionToken.equals(clientToken)) {
			String errorMessage = "You are not logged in, please login";
			request.setAttribute("errorMessage", errorMessage);
			request.getRequestDispatcher("Login.jsp").forward(request, response);
			return;
		}
		UserDetails userDetails = (UserDetails) session.getAttribute("userDetails");
		if (userDetails.getFirstName() == null || !userDetails.getFirstName().matches("^[a-zA-Z ]{1,30}$")) {
			String errorMessage = "User Profile must be compiled before Checkout (First Name missing)";
			request.setAttribute("errorMessage", errorMessage);
			request.getRequestDispatcher("UserProfile.jsp").forward(request, response);

			return;
		}
		if (userDetails.getLastName() == null || !userDetails.getLastName().matches("^[a-zA-Z ]{1,30}$")) {
			String errorMessage = "User Profile must be compiled before Checkout (Last Name missing)";
			request.setAttribute("errorMessage", errorMessage);
			request.getRequestDispatcher("UserProfile.jsp").forward(request, response);

			return;
		}

		if (userDetails.getPhoneNumber() == null || !userDetails.getPhoneNumber().matches("^\\d{10}$")) {
			String errorMessage = "User Profile must be compiled before Checkout (Phone Number missing)";
			request.setAttribute("errorMessage", errorMessage);
			request.getRequestDispatcher("UserProfile.jsp").forward(request, response);

			return;
		}

		if (userDetails.getHomeAddress() == null || !userDetails.getHomeAddress().matches("[a-zA-Z0-9 ]{1,100}$")) {
			String errorMessage = "User Profile must be compiled before Checkout (Home Address missing)";
			request.setAttribute("errorMessage", errorMessage);
			request.getRequestDispatcher("UserProfile.jsp").forward(request, response);

			return;
		}

		PaymentMethod paymentMethod = (PaymentMethod) session.getAttribute("paymentMethod");
		if (paymentMethod.getCardHolderName() == null || paymentMethod.getCardHolderName().trim().isEmpty()) {
			String errorMessage = "Card holder name is required before Checkout";
			request.setAttribute("errorMessage", errorMessage);
			request.getRequestDispatcher("PaymentInfo.jsp").forward(request, response);
			return;
		}

		if (paymentMethod.getCardNumber() == null || !isValidCardNumber(paymentMethod.getCardNumber())) {
			String errorMessage = "card number is required before Checkout";
			request.setAttribute("errorMessage", errorMessage);
			request.getRequestDispatcher("PaymentInfo.jsp").forward(request, response);
			return;
		}

		if (paymentMethod.getexpirationMonth() == null || !isValidExpirationMonth(paymentMethod.getexpirationMonth())) {
			String errorMessage = "month is required before Checkout.";
			request.setAttribute("errorMessage", errorMessage);
			request.getRequestDispatcher("PaymentInfo.jsp").forward(request, response);

			return;
		}

		if (paymentMethod.getexpirationYear() == null || !isValidExpirationYear(paymentMethod.getexpirationYear())) {
			String errorMessage = "year is required before Checkout.";
			request.setAttribute("errorMessage", errorMessage);
			request.getRequestDispatcher("PaymentInfo.jsp").forward(request, response);

			return;
		}

		List<CartItem> cartItems;
		if (((User) session.getAttribute("user")) == null) {
			cartItems = (List<CartItem>) session.getAttribute("guestCart");
		} else {
			cartItems = (List<CartItem>) session.getAttribute("cart");
		}
		try {
			cartItemDAO.saveAllCartItem(cartItems);
		} catch (SQLException e) {
			String errorMessage = "There was an error in saving your cart data to the database";
			response.sendError(500, errorMessage);
			return;
		}

		Order order = new Order();
		Product product;

		order.setOrderId(0);
		order.setOrderDate(localDate);
		order.setTotalAmount(totalPrice);
		order.setUserId(user.getUserId());
		order.setorderStatus("Awaiting Shipment");
		int orderId;
		try {
			orderId = orderDAO.saveOrder(order);
		} catch (SQLException e) {
			String errorMessage = "There was an error in creating the order, try again";
			response.sendError(500, errorMessage);
			return;
		}

		for (CartItem cartItem : cartItems) {
			try {

				product = productDAO.getProduct(cartItem.getProductId());
				if (product == null) {
					String errorMessage = "The item you were buying got deleted, return to the cart";
					response.sendError(500, errorMessage);
					return;
				}

			} catch (SQLException e) {
				String errorMessage = "There was an error in retrieving the product in your cart, try removing each cart item";
				response.sendError(500, errorMessage);
				return;

			}
			cartItem.setPrice(product.getPrice());
			try {
				cartItemDAO.updateCartItem(orderId, user.getUserId(), product.getPrice(), product.getProductId());
			} catch (SQLException e) {
				String errorMessage = "There was an error in updating the product in your cart, try removing each cart item";
				response.sendError(500, errorMessage);
				return;
			}
			session.setAttribute("cart", null);
		}
		try {
			Payment payment = new Payment();
			payment.setOrderIdExt(orderId);
			payment.setPaymentDate(localDate);
			payment.setPaymentMethodIdExt(paymentMethod.getPaymentMethodId());
			paymentDAO.savePayment(payment);
		} catch (SQLException e) {
			String errorMessage = "There was an error in making the payment, try again";
			response.sendError(500, errorMessage);
			return;

		}

		response.sendRedirect("Orders.jsp");
	}

	@Override
	public void init() {
		productDAO = new ProductDAO(DBConnection.getDataSource());
		cartItemDAO = new CartItemDAO(DBConnection.getDataSource());
		orderDAO = new OrderDAO(DBConnection.getDataSource());
		paymentDAO = new PaymentDAO(DBConnection.getDataSource());

	}

	private boolean isValidCardNumber(String cardNumber) {
		String trimmedCardNumber = cardNumber.replace(" ", "");
		if (!trimmedCardNumber.matches("^\\d+$")) {
			return false;
		}

		int sum = 0;
		boolean doubleDigit = false;
		for (int i = trimmedCardNumber.length() - 1; i >= 0; i--) {
			int digit = Character.getNumericValue(trimmedCardNumber.charAt(i));
			if (doubleDigit) {
				digit *= 2;
				if (digit > 9) {
					digit -= 9;
				}
			}
			sum += digit;
			doubleDigit = !doubleDigit;
		}

		return sum % 10 == 0;
	}

	private boolean isValidExpirationMonth(String expirationMonth) {
		return expirationMonth.matches("^(0[1-9]|1[0-2])$");
	}

	private boolean isValidExpirationYear(String expirationYear) {
		return expirationYear.matches("^(20\\d{2})$");
	}
}
