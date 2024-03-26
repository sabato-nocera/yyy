<!DOCTYPE html>
<%@ page import="model.User"%>
<%@ page import="model.Category"%>
<%@ page import="java.util.List"%>
<%@ page import="javax.servlet.http.HttpServletResponse"%>

<html lang="en">
<head>
<title>Admin Page</title>
<script src="scripts/jquery-3.7.0.min.js"></script>
<link rel="icon" href="/TechBuild/images/icon/TechBuild-16.ico">


<script src="scripts\catalog.js"></script>


<script src="scripts\adminCatalog.js"></script>

<script src="scripts\asyncForms.js"></script>
<link rel="stylesheet" type="text/css"
	href="/TechBuild/styles/cartNotification.css">

<link rel="stylesheet" type="text/css"
	href="/TechBuild/styles/catalog.css">




<style>
.invalid {
	border: 1px solid red;
}

#buttonDiv {
	text-align: center;
}

#toggleButton {
	cursor: pointer;
	text-align: center;
	font-size: 20px;
}

.hidden {
	display: none;
}
</style>


</head>
<body>
	<%
	String sessionToken = (String) session.getAttribute("sessionToken");

	String userrole = ((User) session.getAttribute("user")).getUserRole();
	if (!(userrole.equals("admin"))) {
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "You are not an admin.");
		return;
	}
	%>
	<jsp:include page="../fragments/header.jsp" />
	<div id="buttonDiv">
		<button id="toggleButton">Show Catalog</button>
	</div>
	<div id="cart-notification"></div>

	<section id="catalog">
		<div class="container-catalog" id="container-catalog">
			<div class="filter-section">
				<form id="filterForm" action="FilterCatalogAdmin" method="POST">
					<input type="hidden" name="clientToken" value="<%=sessionToken%>">

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
						<h1>
							Product Id
							<%=product.getProductId()%></h1>

						<form class="product-details-form" action="Update" method="POST">
							<input type="hidden" name="productId"
								value="<%=product.getProductId()%>"> <input
								type="hidden" name="clientToken" value="<%=sessionToken%>">

							<div>
								<label for="name">Name</label><br> <input type="text"
									name="name" id="name" value="<%=product.getName()%>">
							</div>

							<div>
								<label for="description">Description</label><br>
								<textarea name="description" id="description"><%=product.getDescription()%></textarea>
							</div>

							<div>

								<label for="category">Category(<%=product.getCategory()%>)
								</label><br> <select name="category" id="category">
									<%
									for (Category category : categories) {
									%>
									<option value="<%=category.getCategory()%>"><%=category.getCategory()%></option>
									<%
									}
									%>
								</select>


							</div>

							<div>
								<label for="price">Price</label><br> <input type="number"
									name="price" id="price" min=1 max=1000 step="any"
									value="<%=product.getPrice()%>">
							</div>

							<div>
								<label for="imagePath">Image Path</label><br> <input
									type="text" name="imagePath" id="imagePath"
									value="<%=product.getImagePath()%>">
							</div>

							<div>
								<label for="recommended">Recommended</label><br> <input
									type="checkbox" name="recommended" id="recommended"
									<%if (product.isRecommended()) {%> checked <%}%>>
							</div>

							<div class="button-container">
								<input type="submit" value="Update Product">
							</div>
						</form>
						<form action="Remove" method="POST">
							<input type="hidden" name="productId"
								value="<%=product.getProductId()%>"> <input
								type="hidden" name="clientToken" value="<%=sessionToken%>">

							<%
							if (product.isDeleted()) {
							%>
							<input type="submit" value="Add Product"
								onclick="changeButtonLabel(this)">
							<%
							} else {
							%>
							<input type="submit" value="Remove Product"
								onclick="changeButtonLabel(this)">
							<%
							}
							%>
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


	<section id="AddProduct">

		<h1>Add Product</h1>
		<form class="product-details" action="addProduct" method="POST"
			id="AddProductForm" onsubmit="return validateForm()">
			<div>
				<label for="nameForm">Name</label><br> <input type="text"
					id="nameForm" name="name" required>
			</div>

			<div>
				<label for="descriptionForm">Description</label><br>
				<textarea id="descriptionForm" name="description" required></textarea>
			</div>

			<div>
				<label for="categoryForm">Category</label><br> <select
					name="category" id="categoryForm">
					<%
					for (Category category : categories) {
					%>
					<option value="<%=category.getCategory()%>"><%=category.getCategory()%></option>
					<%
					}
					%>
				</select>
			</div>

			<div>
				<label for="priceForm">Price</label><br> <input type="number"
					id="priceForm" name="price" step="any" required>
			</div>

			<div>
				<label for="imagePathForm">Image Path</label><br> <input
					type="text" id="imagePathForm" name="imagePath" required>
			</div>

			<div>
				<label for="recommendedForm">Recommended</label><br> <input
					type="checkbox" id="recommendedForm" name="recommended">
			</div>
			<input type="hidden" name="clientToken" value="<%=sessionToken%>">
			<div class="button-container">
				<input type="submit" value="Add Product">
			</div>
		</form>

		<%-- Display error message if available --%>
		<%
		String errorMessage = (String) request.getAttribute("errorMessage");
		%>
		<%
		if (errorMessage != null && !errorMessage.isEmpty()) {
		%>
		<p class="error"><%=errorMessage%></p>
		<%
		}
		%>

	</section>


	<jsp:include page="../fragments/footer.jsp" />

</body>
</html>
