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

import dao.DBConnection;
import dao.ProductDAO;
import model.Product;
import model.User;

@WebServlet("/addProduct")
public class AddProductServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private ProductDAO productDAO;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		String category = request.getParameter("category");
		BigDecimal price = new BigDecimal(request.getParameter("price"));
		String imagePath = request.getParameter("imagePath");
		boolean recommended = Boolean.parseBoolean(request.getParameter("recommended"));
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

		if (price.compareTo(BigDecimal.ZERO) <= 0) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Invalid Price.");

			return;
		}

		Product product = new Product();
		product.setName(name);
		product.setDescription(description);
		product.setCategory(category);
		product.setPrice(price);
		product.setImagePath(imagePath);
		product.setRecommended(recommended);

		try {
			productDAO.saveProduct(product);
			response.sendRedirect("AdminCatalogPage");

		} catch (SQLException e) {
			request.setAttribute("errorMessage", "Error adding product ");
			request.getRequestDispatcher("AdminCatalogPage").forward(request, response);
		}
	}

	@Override
	public void init() {
		productDAO = new ProductDAO(DBConnection.getDataSource());
	}

}
