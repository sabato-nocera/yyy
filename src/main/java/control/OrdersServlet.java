package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import dao.CartItemDAO;
import dao.DBConnection;
import dao.OrderDAO;
import dao.ProductDAO;
import model.CartItem;
import model.Order;
import model.Product;
import model.User;

@WebServlet("/Orders")
public class OrdersServlet extends HttpServlet {
	private static final long serialVersionUID = 8L;
	private CartItemDAO cartItemDAO;
	private Gson gson;
	private OrderDAO orderDAO;
	private ProductDAO productDAO;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		if (user == null) {
			String errorMessage = "You are not logged in, please login";
			request.setAttribute("errorMessage", errorMessage);
			request.getRequestDispatcher("Login.jsp").forward(request, response);
			return;
		}

		try {
			List<Order> orders = orderDAO.getOrders(user.getUserId());
			JsonObject resultJson = new JsonObject();

			for (Order order : orders) {
				int orderId = order.getOrderId();
				List<CartItem> cartItems = cartItemDAO.findOrderedItemsByOrderId(user.getUserId(), orderId);

				JsonObject orderJson = new JsonObject();
				orderJson.addProperty("orderId", order.getOrderId());
				orderJson.addProperty("userId", order.getUserId());
				orderJson.addProperty("orderDate", order.getOrderDate().toString());
				orderJson.addProperty("totalAmount", order.getTotalAmount().toString());
				orderJson.addProperty("orderStatus", order.getorderStatus());

				JsonArray cartItemsJson = new JsonArray();
				for (CartItem cartItem : cartItems) {
					JsonObject cartItemJson = new JsonObject();

					Product product = productDAO.getOrderProduct(cartItem.getProductId());
					JsonObject productJson = new JsonObject();
					productJson.addProperty("productId", product.getProductId());
					productJson.addProperty("imagePath", product.getImagePath());

					productJson.addProperty("price", product.getPrice().toString());
					productJson.addProperty("name", product.getName());
					productJson.addProperty("description", product.getDescription());
					productJson.addProperty("category", product.getCategory());
					productJson.addProperty("image", product.getImagePath());

					cartItemJson.add("product", productJson);
					cartItemJson.addProperty("quantity", cartItem.getQuantity());
					cartItemJson.addProperty("price", cartItem.getPrice());

					cartItemsJson.add(cartItemJson);
				}

				orderJson.add("cartItems", cartItemsJson);
				resultJson.add(String.valueOf(orderId), orderJson);
			}

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(gson.toJson(resultJson));
		} catch (SQLException e) {
			String errorMessage = "There was an error during the retrieval of your orders, try again";
			response.sendError(500, errorMessage);
		}
	}

	@Override
	public void init() {
		orderDAO = new OrderDAO(DBConnection.getDataSource());
		cartItemDAO = new CartItemDAO(DBConnection.getDataSource());
		productDAO = new ProductDAO(DBConnection.getDataSource());
		gson = new GsonBuilder().setPrettyPrinting().create();
	}
}
