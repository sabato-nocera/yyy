<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="model.Product"%>
<%@ page import="model.User"%>
<%@ page import="java.util.List"%>
<%@ page import="java.math.BigDecimal"%>
<%@ page import="model.CartItem"%>
<%@ page import="com.google.gson.JsonArray"%>
<%@ page import="com.google.gson.JsonParser"%>
<%@ page import="com.google.gson.Gson"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Cart Items</title>
<link rel="icon" href="/TechBuild/images/icon/TechBuild-16.ico">

<link rel="stylesheet" type="text/css" href="/TechBuild/styles/cart.css">
<script src="scripts/jquery-3.7.0.min.js" type="text/javascript"></script>
<script src="scripts/cart.js" type="text/javascript"></script>
<script src="scripts\asyncForms.js"></script>
<link rel="stylesheet" type="text/css"
	href="/TechBuild/styles/cartNotification.css">

</head>
<body>
	<jsp:include page="/fragments/header.jsp" />

	<div class="container">
		<h1 style="text-align: center">Shopping Cart</h1>

		<%

		List<CartItem> cartItems = null;
		if (((User) session.getAttribute("user")) == null) {
			cartItems = (List<CartItem>) session.getAttribute("guestCart");
		} else {
			cartItems = (List<CartItem>) session.getAttribute("cart");
		}

		if (cartItems == null || cartItems.isEmpty()) {
		%>
		<p class="empty-cart-msg">
			Your cart is empty.<br> <a href="/TechBuild/Catalog">Continue
				Shopping</a>
		</p>
		<%
		} else {
		%>
		<p class="error" style="text-align: center;">
			<%
			if (request.getAttribute("errorMessage") != null) {
			%>
			<%=request.getAttribute("errorMessage")%>
			<%}%>

		</p>
		<table class="cart-table">
			<caption>Items in the Cart</caption>

			<thead>
				<tr>
					<th>Product</th>
					<th>Price</th>
					<th>Quantity</th>
					<th>Subtotal</th>
					<th></th>
				</tr>
			</thead>
			<tbody>

				<%
				for (int i = 0; i < cartItems.size(); i++) {

					JsonArray cartItemsJson = new JsonParser().parse(request.getAttribute("cartItemsJson").toString()).getAsJsonArray();
					Product product = new Gson().fromJson(cartItemsJson.get(i), Product.class);
					int quantity = cartItems.get(i).getQuantity();
					BigDecimal subtotal = product.getPrice().multiply(BigDecimal.valueOf(quantity));
				%>
				<tr>
					<td>
						<div class="product-details">

							<div class="product-image">
								<form action="ProductPage" method="POST">
									<input type="hidden" name="productId"
										value="<%=product.getProductId()%>">
									<div onclick="submitForm(this.parentNode)">
										<img src="<%=product.getImagePath()%>"
											alt="<%=product.getName()%>" style="cursor: pointer;"
											class="product-image">
									</div>
								</form>
							</div>
							<div class="product-info">
								<p class="product-name"><%=product.getName()%></p>
								<p><%=product.getDescription()%></p>
							</div>
						</div>
					</td>
					<td class="product-price">$<%=product.getPrice()%></td>
					<td><input type="number" class="product-quantity"
						data-product-id="<%=cartItems.get(i).getProductId()%>"
						value="<%=quantity%>" min="1" max="10">
						<p class="error"></p></td>
					<td class="product-subtotal">$<%=subtotal%></td>
					<td class="remove-item">
						<form action="removeItem" method="POST">
							<input type="hidden" name="productId"
								value="<%=cartItems.get(i).getProductId()%>">
							<button type="submit">Remove Item</button>
						</form>
					</td>
				</tr>
				<%
				}
				%>
			</tbody>
		</table>

		<form action="Checkout" method="POST">
			<%
			String sessionToken = (String) session.getAttribute("sessionToken");
			BigDecimal totalPrice = (BigDecimal) request.getAttribute("totalPrice");
			%>
			<input type="hidden" name="clientToken" value="<%=sessionToken%>">
			<input type="hidden" type="number" name="totalPrice"
				value="<%=totalPrice%>">

			<div class="cart-summary">



				<p>
					Cart Total:<span class="cart-total">$<%=totalPrice%></span>
				</p>

				<button type="submit" class="checkout-btn">Proceed to
					Checkout</button>
			</div>
		</form>

		<%

		}
		%>
	</div>

	<jsp:include page="/fragments/footer.jsp" />

</body>
</html>
