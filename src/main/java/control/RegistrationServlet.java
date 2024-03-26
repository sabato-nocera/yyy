package control;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DBConnection;
import dao.PaymentMethodDAO;
import dao.UserDAO;
import dao.UserDetailsDAO;
import model.User;
import model.UserDetails;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {

	private static final long serialVersionUID = 4L;
	private PaymentMethodDAO paymentMethodDAO;
	private UserDAO userDAO;
	private UserDetailsDAO userDetailsDAO;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String hashedPassword = PasswordHasher.hashPassword(password);

		if (!username.matches("^[a-zA-Z0-9]{5,30}$")) {
			String errorMessage = "Invalid username (5-20 characters, alphanumeric)";
			request.setAttribute("errorMessage", errorMessage);

			request.getRequestDispatcher("Register.jsp").forward(request, response);

			return;
		}

		if (email.length() > 100 || !email.matches("^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$")) {
			String errorMessage = "Invalid email address";
			request.setAttribute("errorMessage", errorMessage);

			request.getRequestDispatcher("Register.jsp").forward(request, response);

			return;
		}

		if (!password.matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,50}$")) {

			String errorMessage = "Invalid password (minimum 8 characters, at least one maiusc letter, one symbol, one number and max 50 characters)";
			request.setAttribute("errorMessage", errorMessage);

			request.getRequestDispatcher("Register.jsp").forward(request, response);

			return;
		}
		User user = new User();
		user.setUsername(username);
		user.setPassword(hashedPassword);
		user.setEmail(email);
		user.setUserRole("customer");

		try {
			userDAO.addUser(user);
			int userId = userDAO.getUserByUsernamePassword(username, hashedPassword).getUserId();

			UserDetails userDetails = new UserDetails();

			userDetails.setUserId(userId);

			userDetailsDAO.saveUserDetails(userDetails);
			paymentMethodDAO.savePaymentMethod(userId);

			request.getRequestDispatcher("login").forward(request, response);
		} catch (SQLException e) {
			if (e instanceof SQLIntegrityConstraintViolationException) {
				String errorMessage = "Username or email already exists";
				request.setAttribute("errorMessage", errorMessage);
				request.getRequestDispatcher("Register.jsp").forward(request, response);

			} else {
				String errorMessage = "An error occurred during registration";
				request.setAttribute("errorMessage", errorMessage);
				request.getRequestDispatcher("Register.jsp").forward(request, response);
			}
		}
	}

	@Override
	public void init() {
		userDAO = new UserDAO(DBConnection.getDataSource());
		userDetailsDAO = new UserDetailsDAO(DBConnection.getDataSource());
		paymentMethodDAO = new PaymentMethodDAO(DBConnection.getDataSource());

	}

}
