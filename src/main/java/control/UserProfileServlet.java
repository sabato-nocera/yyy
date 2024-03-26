package control;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.DBConnection;
import dao.UserDetailsDAO;
import model.User;
import model.UserDetails;

@WebServlet("/updateProfile")
public class UserProfileServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private UserDetailsDAO userDetailsDAO;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String phoneNumber = request.getParameter("phoneNumber");
		String homeAddress = request.getParameter("homeAddress");

		if (!firstName.matches("^[a-zA-Z ]{1,30}$")) {
			String errorMessage = "Invalid firstName (1-20 characters)";
			request.setAttribute("errorMessage", errorMessage);
			request.getRequestDispatcher("UserProfile.jsp").forward(request, response);

			return;
		}
		if (!lastName.matches("^[a-zA-Z ]{1,30}$")) {
			String errorMessage = "Invalid lastName (1-20 characters)";
			request.setAttribute("errorMessage", errorMessage);
			request.getRequestDispatcher("UserProfile.jsp").forward(request, response);

			return;
		}

		if (!phoneNumber.matches("^\\d{10}$")) {
			String errorMessage = "Invalid phone number";
			request.setAttribute("errorMessage", errorMessage);
			request.getRequestDispatcher("UserProfile.jsp").forward(request, response);

			return;
		}

		if (!homeAddress.matches("[a-zA-Z0-9 ]{1,100}$")) {
			String errorMessage = "Invalid home address";
			request.setAttribute("errorMessage", errorMessage);
			request.getRequestDispatcher("UserProfile.jsp").forward(request, response);

			return;
		}

		HttpSession session = request.getSession();
		User user = ((User) session.getAttribute("user"));
		String sessionToken = ((String) session.getAttribute("sessionToken"));
		String clientToken = (request.getParameter("clientToken"));

		if (user == null || !sessionToken.equals(clientToken)) {
			String errorMessage = "You are not logged in, please login";
			request.setAttribute("errorMessage", errorMessage);
			request.getRequestDispatcher("Login.jsp").forward(request, response);
			return;

		}

		UserDetails userDetails = new UserDetails();
		userDetails.setUserId(user.getUserId());
		userDetails.setFirstName(firstName);
		userDetails.setLastName(lastName);
		userDetails.setPhoneNumber(phoneNumber);
		userDetails.setHomeAddress(homeAddress);

		try {
			userDetailsDAO.updateUserDetails(userDetails);
			session.setAttribute("userDetails", userDetails);
			response.sendRedirect("UserProfile.jsp");
		} catch (SQLException e) {
			if (e instanceof SQLIntegrityConstraintViolationException) {
				String errorMessage = "Phone Number already exists";
				request.setAttribute("errorMessage", errorMessage);
				request.getRequestDispatcher("UserProfile.jsp").forward(request, response);

			} else {
				String errorMessage = "There was an error in updating your user informations, try again";
				request.setAttribute("errorMessage", errorMessage);

				request.getRequestDispatcher("error.jsp").forward(request, response);
			}
		}
	}

	@Override
	public void init() {
		userDetailsDAO = new UserDetailsDAO(DBConnection.getDataSource());
	}
}
