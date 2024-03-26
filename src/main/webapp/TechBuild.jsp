<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="model.Product"%>
<%@ page import="java.util.List"%>
<%@ page import="model.User"%>
<%@ page import="javax.servlet.http.HttpSession"%>

<!DOCTYPE html >
<html lang="en">
<head>
<title>TechBuild Hardware Store</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="icon" href="/TechBuild/images/icon/TechBuild-16.ico">

<script src="scripts/jquery-3.7.0.min.js"></script>

<link rel="stylesheet" type="text/css"
	href="/TechBuild/styles/techBuild.css">

<script src="scripts\cartNotification.js"></script>
<link rel="stylesheet" type="text/css"
	href="/TechBuild/styles/cartNotification.css">

</head>
<body>
	<div id="cart-notification"></div>

	<%
	boolean isSessionActive = (session != null && session.getAttribute("user") != null);
	String username = "";
	if (isSessionActive) {
		username = ((User) session.getAttribute("user")).getUsername();
	}
	%>
	<jsp:include page="fragments/header.jsp" />
	<section id="hero">


		<h2>
			Welcome
			<%=username%>
			to TechBuild
		</h2>
		<h3>Find the latest technology and advanced hardware components</h3>
		<a href="/TechBuild/Catalog" class="cta">Shop Now</a>
	</section>

	<section id="reccomendedProducts">
		<div class="container">
			<h2>Recommended Products</h2>

			<div class="slider">

				<%
				List<Product> recommendedProducts = (List<Product>) request.getAttribute("recommendedProducts");
				%>

				<%
				if (recommendedProducts.isEmpty()) {
				%>
				<p class="no-products-msg">No recommended products available.</p>
				<%
				} else {
				%>
				<div class="slide-container">
					<%
					for (Product product : recommendedProducts) {
					%>


					<div class="slide">

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
						<div class="slide-details">
							<h3><%=product.getName()%></h3>
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
				</div>
				<div class="navigation">
					<button class="prev-btn" onclick="moveToPrevSlide()">&#8249;</button>
					<button class="next-btn" onclick="moveToNextSlide()">&#8250;</button>
				</div>
				<%
				}
				%>

			</div>
		</div>
	</section>
	<script src="scripts/techBuild.js"></script>


	<jsp:include page="fragments/footer.jsp" />

</body>
</html>