package admin;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CartItemDAO;
import dao.DBConnection;
import dao.ProductDAO;
import model.Product;
import model.User;

@WebServlet({ "/Update", "/Remove" })
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 15L;
	private CartItemDAO cartItemDAO;
	private ProductDAO productDAO;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String servletPath = request.getServletPath();
		HttpSession session = request.getSession();

		User user = (User) session.getAttribute("user");
		String sessionToken = ((String) session.getAttribute("sessionToken"));
		String clientToken = (request.getParameter("clientToken"));
		if (user != null) {
			if (!sessionToken.equals(clientToken)) {
				String errorMessage = "You are not logged in, please login";
				request.setAttribute("errorMessage", errorMessage);
				request.getRequestDispatcher("Login.jsp").forward(request, response);
				return;
			}
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

		int productId = Integer.parseInt(request.getParameter("productId"));

		if (servletPath.equals("/Remove")) {
			try {
				productDAO.toggleProductDeleted(productId);
				cartItemDAO.removeAllDeletedItems(productId);

			} catch (SQLException e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
						"An error occurred while removing product.");
				return;
			}
		}

		else if (servletPath.equals("/Update")) {
			try {
				String name = request.getParameter("name");
				String description = request.getParameter("description");
				String category = request.getParameter("category");
				BigDecimal price = new BigDecimal(request.getParameter("price"));
				String imagePath = request.getParameter("imagePath");
				boolean recommended = request.getParameter("recommended") != null;

				Product existingProduct = productDAO.getOrderProduct(productId);

				existingProduct.setName(name);
				existingProduct.setDescription(description);
				existingProduct.setCategory(category);
				existingProduct.setPrice(price);
				existingProduct.setImagePath(imagePath);
				existingProduct.setRecommended(recommended);

				productDAO.updateProduct(existingProduct);

			}

			catch (SQLException e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
						"An error occurred while updating product.");
				return;
			}
		}
		response.sendRedirect("AdminCatalogPage");

	}

	@Override
	public void init() {
		productDAO = new ProductDAO(DBConnection.getDataSource());
		cartItemDAO = new CartItemDAO(DBConnection.getDataSource());
	}

}
