package admin;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.DBConnection;
import dao.OrderDAO;
import dao.UserDAO;
import model.Order;
import model.User;

@WebServlet("/AdminOrderPage")

public class RetrieveOrdersServlet extends HttpServlet {

	private static final long serialVersionUID = 16L;
	private OrderDAO orderDAO;
	private UserDAO userDAO;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user != null) {

			if (!(user.getUserRole().equals("admin"))) {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "You are not an admin.");
				return;
			}
		} else {
			String errorMessage = "You are not logged in, please login";
			request.setAttribute("errorMessage", errorMessage);
			request.getRequestDispatcher("Login.jsp").forward(request, response);
			return;

		}
		try {
			List<User> userList = userDAO.getAllUsers();
			List<Order> orderList = orderDAO.getAllOrders();
			request.setAttribute("userList", userList);
			request.setAttribute("orderList", orderList);

			request.getRequestDispatcher("admin/AdminOrdersPage.jsp").forward(request, response);
		} catch (SQLException e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					"An error occurred while retrieving user orders.");
		}

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user != null) {

			if (!(user.getUserRole().equals("admin"))) {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "You are not an admin.");
				return;
			}
		} else {
			String errorMessage = "You are not logged in, please login";
			request.setAttribute("errorMessage", errorMessage);
			request.getRequestDispatcher("Login.jsp").forward(request, response);
			return;

		}
		try {

			List<User> userList = userDAO.getAllUsers();

			List<Order> orderList = null;
			String selectedUserId = request.getParameter("userId");
			String fromDate = request.getParameter("fromDate");
			String toDate = request.getParameter("toDate");
			if (fromDate != null && !fromDate.isEmpty() && toDate != null && !toDate.isEmpty() && selectedUserId != null
					&& !selectedUserId.isEmpty()) {
				int userId = Integer.parseInt(selectedUserId);

				java.sql.Date fromDateSql = java.sql.Date.valueOf(fromDate);
				java.sql.Date toDateSql = java.sql.Date.valueOf(toDate);

				orderList = orderDAO.getOrders(fromDateSql, toDateSql, userId);
			} else if (fromDate != null && !fromDate.isEmpty() && toDate != null && !toDate.isEmpty()) {
				java.sql.Date fromDateSql = java.sql.Date.valueOf(fromDate);
				java.sql.Date toDateSql = java.sql.Date.valueOf(toDate);
				orderList = orderDAO.getOrders(fromDateSql, toDateSql);
			} else if (selectedUserId != null && !selectedUserId.isEmpty()) {

				int userId = Integer.parseInt(selectedUserId);

				orderList = orderDAO.getOrders(userId);
			} else {
				orderList = orderDAO.getAllOrders();

			}

			request.setAttribute("userList", userList);
			request.setAttribute("orderList", orderList);

			request.getRequestDispatcher("admin/AdminOrdersPage.jsp").forward(request, response);
		} catch (SQLException e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					"An error occurred while retrieving user orders.");
		}
	}

	@Override
	public void init() {
		userDAO = new UserDAO(DBConnection.getDataSource());
		orderDAO = new OrderDAO(DBConnection.getDataSource());
	}

}
