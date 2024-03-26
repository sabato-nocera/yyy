package control;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.DBConnection;
import dao.PaymentMethodDAO;
import model.PaymentMethod;
import model.User;

@WebServlet("/updatePaymentMethod")
public class PaymentMethodServlet extends HttpServlet {

	private static final long serialVersionUID = 7L;
	private PaymentMethodDAO paymentMethodDAO;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String cardHolderName = request.getParameter("cardHolderName");
		String cardNumber = request.getParameter("cardNumber");
		String expirationMonth = request.getParameter("expirationMonth");
		String expirationYear = request.getParameter("expirationYear");

		if (cardHolderName.trim().isEmpty()) {
			String errorMessage = "Card holder name is required.";
			request.setAttribute("errorMessage", errorMessage);
			request.getRequestDispatcher("PaymentInfo.jsp").forward(request, response);
			return;
		}

		if (!isValidCardNumber(cardNumber)) {
			String errorMessage = "Invalid card number.";
			request.setAttribute("errorMessage", errorMessage);
			request.getRequestDispatcher("PaymentInfo.jsp").forward(request, response);
			return;
		}

		if (!isValidExpirationMonth(expirationMonth)) {
			String errorMessage = "Invalid expiration month. Please enter a valid two-digit month (01-12).";
			request.setAttribute("errorMessage", errorMessage);
			request.getRequestDispatcher("PaymentInfo.jsp").forward(request, response);

			return;
		}

		if (!isValidExpirationYear(expirationYear)) {
			String errorMessage = "Invalid expiration year. Please enter a valid four-digit year.";
			request.setAttribute("errorMessage", errorMessage);
			request.getRequestDispatcher("PaymentInfo.jsp").forward(request, response);

			return;
		}

		HttpSession session = request.getSession();
		String sessionToken = (String) session.getAttribute("sessionToken");
		String clientToken = request.getParameter("clientToken");

		if (!sessionToken.equals(clientToken)) {
			response.sendRedirect("forceLogout");
			return;
		}

		User user = (User) session.getAttribute("user");
		PaymentMethod paymentMethod = (PaymentMethod) session.getAttribute("paymentMethod");

		paymentMethod.setUserIdDetails(user.getUserId());
		paymentMethod.setPaymentMethodName("Credit Card");
		paymentMethod.setCardHolderName(cardHolderName);
		paymentMethod.setCardNumber(cardNumber);
		paymentMethod.setexpirationMonth(expirationMonth);
		paymentMethod.setexpirationYear(expirationYear);

		try {
			paymentMethodDAO.updatePaymentMethod(paymentMethod);
			session.setAttribute("paymentMethod", paymentMethod);
			response.sendRedirect("PaymentInfo.jsp");
		} catch (SQLException e) {
			String errorMessage = "There was an error in updating your card informations, try again";
			request.setAttribute("errorMessage", errorMessage);

			request.getRequestDispatcher("error.jsp").forward(request, response);
		}
	}

	@Override
	public void init() {
		paymentMethodDAO = new PaymentMethodDAO(DBConnection.getDataSource());
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
