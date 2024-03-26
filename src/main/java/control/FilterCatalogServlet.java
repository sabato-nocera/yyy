package control;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CategoryDAO;
import dao.DBConnection;
import dao.ProductDAO;
import model.Category;
import model.Product;

@WebServlet({ "/FilterCatalog", "/FilterCatalogAdmin" })
public class FilterCatalogServlet extends HttpServlet {
	private static final long serialVersionUID = 11L;
	private CategoryDAO categoryDAO;
	private ProductDAO productDAO;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		return;
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String servletPath = request.getServletPath();

		String category = request.getParameter("category");
		String minPriceParam = request.getParameter("minPrice");
		String maxPriceParam = request.getParameter("maxPrice");
		if (category == null) {
			String errorMessage = "There was an error in filtering your catalog, try again";
			response.sendError(500, errorMessage);
			return;
		}
		List<Product> filteredProducts;
		try {

			switch (category) {
			case "All":
				if (minPriceParam != null && maxPriceParam != null) {
					BigDecimal minPrice = new BigDecimal(minPriceParam);
					BigDecimal maxPrice = new BigDecimal(maxPriceParam);
					filteredProducts = productDAO.getAllProducts(minPrice, maxPrice);
				} else {
					filteredProducts = productDAO.getAllProducts(category);
				}
				break;
			default:
				if (minPriceParam != null && maxPriceParam != null) {
					BigDecimal minPrice = new BigDecimal(minPriceParam);
					BigDecimal maxPrice = new BigDecimal(maxPriceParam);
					filteredProducts = productDAO.getAllProducts(category, minPrice, maxPrice);
				} else {
					filteredProducts = productDAO.getAllProducts(category);
				}
				break;
			}

			request.setAttribute("products", filteredProducts);

			List<Category> categories = null;

			categories = categoryDAO.getAllCategories();
			request.setAttribute("categories", categories);

			if (servletPath.equals("/FilterCatalog")) {

				RequestDispatcher dispatcher = request.getRequestDispatcher("Catalog.jsp");
				dispatcher.forward(request, response);
			} else if (servletPath.equals("/FilterCatalogAdmin")) {
				RequestDispatcher dispatcher = request.getRequestDispatcher("admin/AdminCatalogPage.jsp");
				dispatcher.forward(request, response);

			}
		} catch (NumberFormatException e) {
			String errorMessage = "There was an error in filtering your catalog, invalid number found, try again";
			response.sendError(500, errorMessage);
		} catch (SQLException e) {
			String errorMessage = "There was an error in filtering your catalog, try again";
			response.sendError(500, errorMessage);

		}
	}

	@Override
	public void init() {
		productDAO = new ProductDAO(DBConnection.getDataSource());
		categoryDAO = new CategoryDAO(DBConnection.getDataSource());
	}

}
