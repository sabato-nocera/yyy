package control;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import dao.DBConnection;
import dao.ProductDAO;
import model.CartItem;
import model.Product;
import model.User;

@WebServlet("/Cart")
public class CartServlet extends HttpServlet {
	private static final long serialVersionUID = 13L;
	private ProductDAO productDAO;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();

		List<CartItem> cartItems;
		if (((User) session.getAttribute("user")) == null) {
			cartItems = (List<CartItem>) session.getAttribute("guestCart");
		} else {
			cartItems = (List<CartItem>) session.getAttribute("cart");
		}
		List<CartItem> itemsToRemove = new ArrayList<>();

		JsonArray cartItemsJson = new JsonArray();
		BigDecimal totalPrice = BigDecimal.ZERO;
		if (cartItems != null) {
			for (CartItem cartItem : cartItems) {
				Product product = null;
				try {

					product = productDAO.getProduct(cartItem.getProductId());
				} catch (SQLException e) {
					String errorMessage = "There was an error retrieving your cart data";
					response.sendError(500, errorMessage);
					return;

				}
				if (product == null) {
					itemsToRemove.add(cartItem);
					continue;
				}

				JsonObject cartItemJson = new JsonObject();
				cartItemJson.addProperty("productId", product.getProductId());

				cartItemJson.addProperty("imagePath", product.getImagePath());

				cartItemJson.addProperty("name", product.getName());
				cartItemJson.addProperty("description", product.getDescription());
				cartItemJson.addProperty("category", product.getCategory());
				cartItemJson.addProperty("price", product.getPrice());

				if (cartItem.getQuantity() > 10) {
					cartItem.setQuantity(10);

					String errorMessage = "You can't add more than 10 items, set quantity to 10";
					request.setAttribute("errorMessage", errorMessage);

				}
				if (cartItem.getQuantity() < 1) {
					cartItem.setQuantity(1);

					String errorMessage = "You can't add less than 1 item, set quantity to 1";
					request.setAttribute("errorMessage", errorMessage);

				}
				cartItemJson.addProperty("quantity", cartItem.getQuantity());

				try {
					totalPrice = totalPrice
							.add(product.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
				} catch (NullPointerException e) {
					String errorMessage = "There was an error retrieving your cart data";
					response.sendError(500, errorMessage);
					return;
				}
				cartItemsJson.add(cartItemJson);

			}
			cartItems.removeAll(itemsToRemove);
		}

		if (((User) session.getAttribute("user")) == null) {
			session.setAttribute("guestCart", cartItems);
		} else {
			session.setAttribute("cart", cartItems);
		}
		request.setAttribute("cartItemsJson", cartItemsJson.toString());
		request.setAttribute("totalPrice", totalPrice);
		RequestDispatcher dispatcher = request.getRequestDispatcher("Cart.jsp");
		dispatcher.forward(request, response);

	}

	@Override
	public void init() {
		productDAO = new ProductDAO(DBConnection.getDataSource());
	}

}
