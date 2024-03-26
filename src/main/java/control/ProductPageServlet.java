package control;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DBConnection;
import dao.ProductDAO;
import model.Product;

@WebServlet("/ProductPage")
public class ProductPageServlet extends HttpServlet {
	private static final long serialVersionUID = 6L;
	private ProductDAO productDAO;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect("Catalog");

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			int productId = Integer.parseInt(request.getParameter("productId"));
			Product product = productDAO.getOrderProduct(productId);
			request.setAttribute("product", product);

			if (product.isDeleted())
				request.getRequestDispatcher("ProductPage_deleted.jsp").forward(request, response);
			else
				request.getRequestDispatcher("ProductPage.jsp").forward(request, response);

		} catch (SQLException e) {
			String errorMessage = "There was an error in retrieving the product, please try again.";
			response.sendError(500, errorMessage);
		}
	}

	@Override
	public void init() {
		productDAO = new ProductDAO(DBConnection.getDataSource());
	}
}
