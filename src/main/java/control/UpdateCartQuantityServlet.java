package control;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import model.CartItem;
import model.User;

@WebServlet("/update-quantity")
public class UpdateCartQuantityServlet extends HttpServlet {
	private static final long serialVersionUID = 2L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String requestBody = request.getReader().lines().collect(Collectors.joining());

		JsonObject json = new Gson().fromJson(requestBody, JsonObject.class);

		int quantity = json.get("quantity").getAsInt();
		int productId = json.get("productId").getAsInt();

		HttpSession session = request.getSession();
		List<CartItem> cartItems;
		if (((User) session.getAttribute("user")) == null) {
			cartItems = (List<CartItem>) session.getAttribute("guestCart");
		} else {
			cartItems = (List<CartItem>) session.getAttribute("cart");
		}
		if (cartItems != null) {
			for (CartItem cartItem : cartItems) {
				if (cartItem.getProductId() == productId) {
					if (quantity > 10 || quantity < 1) {
						String errorMessage = "Invalid quanitity found, min 1, max 10";
						request.setAttribute("errorMessage", errorMessage);
						request.getRequestDispatcher("Cart.jsp").forward(request, response);
					}
					cartItem.setQuantity(quantity);
					continue;
				}
			}
		}
		if (((User) session.getAttribute("user")) == null) {

			session.setAttribute("guestCart", cartItems);
		} else {
			session.setAttribute("cart", cartItems);

		}
		JsonObject jsonResponse = new JsonObject();
		jsonResponse.addProperty("success", true);

		String json2 = new Gson().toJson(jsonResponse);

		response.setContentType("application/json");
		response.getWriter().write(json2);
	}
}
