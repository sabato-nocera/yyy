package control;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CategoryDAO;
import dao.DBConnection;
import dao.ProductDAO;
import model.Category;
import model.Product;
import model.User;

@WebServlet({ "/ProductServlet", "/recommendedProducts", "/Catalog", "/TechBuild", "/AdminCatalogPage" })
public class ProductServlet extends HttpServlet {
	private static final long serialVersionUID = 5L;
	private CategoryDAO categoryDAO;
	private ProductDAO productDAO;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String servletPath = request.getServletPath();
		try {
			if (servletPath.equals("/recommendedProducts") || servletPath.equals("/TechBuild")) {
				List<Product> products = productDAO.getAllRecommendedProducts();
				request.setAttribute("recommendedProducts", products);
				request.getRequestDispatcher("TechBuild.jsp").forward(request, response);

			} else if (servletPath.equals("/ProductServlet") || servletPath.equals("/Catalog")) {
				List<Product> products = productDAO.getAllProducts();
				List<Category> categories = categoryDAO.getAllCategories();
				request.setAttribute("products", products);
				request.setAttribute("categories", categories);
				request.getRequestDispatcher("Catalog.jsp").forward(request, response);

			} else if (servletPath.equals("/AdminCatalogPage")) {
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
				List<Product> products = productDAO.getAllProductsAdmin();
				List<Category> categories = categoryDAO.getAllCategories();
				request.setAttribute("products", products);
				request.setAttribute("categories", categories);
				request.getRequestDispatcher("admin/AdminCatalogPage.jsp").forward(request, response);

			} else {
				response.sendError(404, "Error, Page not found");
			}
		} catch (Exception e) {
			String errorMessage = "There was an error in retrieving the catalog, try again";
			response.sendError(500, errorMessage);

		}
	}

	@Override
	public void init() {
		productDAO = new ProductDAO(DBConnection.getDataSource());
		categoryDAO = new CategoryDAO(DBConnection.getDataSource());
	}

}
