<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="model.Category"%>
<%@ page import="java.util.List"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>Catalog</title>
<script src="scripts/jquery-3.7.0.min.js"></script>
<link rel="icon" href="/TechBuild/images/icon/TechBuild-16.ico">

<script src="scripts\cartNotification.js"></script>

<link rel="stylesheet" type="text/css"
	href="/TechBuild/styles/cartNotification.css">

<script src="scripts\catalog.js"></script>

<link rel="stylesheet" type="text/css"
	href="/TechBuild/styles/catalog.css">

</head>
<body>
	<jsp:include page="fragments/header.jsp" />
	<div id="cart-notification"></div>
	<p class="error"></p>

	<section id="catalog">
		<div class="container-catalog">
			<div class="filter-section">
				<form id="filterForm" action="FilterCatalog" method="POST">
					<label for="category">Category</label> <select name="category"
						id="category">
						<option value="All">All</option>
						<%
						List<Category> categories = (List<Category>) request.getAttribute("categories");
						for (Category category : categories) {
						%>
						<option value="<%=category.getCategory()%>"><%=category.getCategory()%></option>
						<%
						}
						%>
					</select> <label for="minPrice">Min Price</label> <input type="range"
						name="minPrice" id="minPrice" value="0" min="0" max="1000"
						step="1"> <input type="number" id="minPriceText" value="0"
						min="0" max="1000" step="1"> <label for="maxPrice">Max
						Price</label> <input type="range" name="maxPrice" id="maxPrice"
						value="1000" min="0" max="1000" step="1"> <input
						type="number" id="maxPriceText" value="1000" min="0" max="1000"
						step="any"> <input type="submit" value="Apply Filter">
				</form>
			</div>
			<div class="product-list">
				<%@ page import="model.Product"%>
				<%@ page import="java.util.List"%>
				<%
				List<Product> products = (List<Product>) request.getAttribute("products");
				%>

				<%
				if (products.isEmpty()) {
				%>
				<p class="no-products-msg">No products available.</p>
				<%
				} else {
				%>
				<%
				for (Product product : products) {
				%>
				<div class="product-item">
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
				<%
				}
				%>
				<%
				}
				%>
			</div>
		</div>
	</section>

	<jsp:include page="fragments/footer.jsp" />

</body>
</html>
