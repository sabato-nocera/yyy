<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="model.Product"%>

<!DOCTYPE html>
<html lang="en">
<head>
<script src="scripts/jquery-3.7.0.min.js"></script>

<title>Product Page</title>
<link rel="stylesheet" type="text/css"
	href="/TechBuild/styles/catalog.css">
<script src="scripts\cartNotification.js"></script>

<link rel="stylesheet" type="text/css"
	href="/TechBuild/styles/cartNotification.css">
</head>
<body>

	<%@include file="fragments/header.jsp"%>
	<div id="cart-notification"></div>

	<section id="catalog">
		<div class="container-catalog">
			<div class="product-list" style="justify-content: center;">
				<%
				Product product = (Product) request.getAttribute("product");
				%>

				<div class="product-item">
					<div class="product-image">
						<img src="<%=product.getImagePath()%>"
							alt="<%=product.getName()%>">
					</div>
					<div class="product-details">
						<h2><%=product.getName()%></h2>
						<p><%=product.getDescription()%></p>
						<span class="product-price">$<%=product.getPrice()%></span>
						<form action="AddToCart" method="POST">
							<input type="hidden" name="productId"
								value="<%=product.getProductId()%>"> <input
								type="number" name="quantity" value="1" min="1" max="10">
							<input type="submit" value="Add to Cart">
						</form>
					</div>
				</div>
			</div>
		</div>
	</section>

	<%@include file="fragments/footer.jsp"%>
</body>
</html>
