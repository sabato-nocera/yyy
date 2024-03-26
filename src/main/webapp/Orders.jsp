<%@ page import="model.User"%>

<!DOCTYPE html>
<html lang="en">
<head>

<title>Orders</title>
<link rel="stylesheet" type="text/css"
	href="/TechBuild/styles/orders.css">
<script src="scripts/jquery-3.7.0.min.js"></script>
<link rel="icon" href="/TechBuild/images/icon/TechBuild-16.ico">

</head>
<body>
	<jsp:include page="fragments/header.jsp" />
	<%
	String sessionToken = (String) session.getAttribute("sessionToken");

	User user = ((User) session.getAttribute("user"));
	if (user == null) {
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "You are not logged in.");
		return;
	}
	%>
	<h1>Orders</h1>
	<div id="ordersContainer"></div>
	<!-- Container to hold the orders -->
	<p class="error"></p>

	<script>
	function submitForm(form) {
		form.submit();
	}
		$.ajax({
			url : "Orders",
			type : "GET",
			dataType : "json",
			success : function(data) {
				renderOrders(data);
			},
			error : function() {
				let errorMessage = "Error retrieving order data.";
				let errorElement = document.querySelector("p.error");
				if (errorElement) {
					errorElement.textContent = errorMessage;
				} else {
					errorElement = document.createElement("p");
					errorElement.textContent = errorMessage;
					errorElement.classList.add("error");
					document.body.appendChild(errorElement);
				}
			}
		});

		function renderOrders(data) {
			let ordersContainer = $("#ordersContainer");
			let j = 1;

			for ( let orderId in data) {
				if (data.hasOwnProperty(orderId)) {
					let order = data[orderId];
					let orderHtml = '<div class="order">';
					orderHtml += '<div class="orderHeader">' + 'Order n°: ' + j
							+ '</div>';
					orderHtml += '<div class="orderDetails" style="display:none;">';
					orderHtml += '<p><strong style="font-size:100%;">Order Date:'
							+ '<span style="color: blue;">'
							+ order.orderDate
							+ '</span>' + '</strong></p> ';
					orderHtml += '<p><strong style="font-size:100%;">Total Amount:'
							+ '<span style="color: green;">'
							+ order.totalAmount + '$</span>';
					+'</strong></p> '
					orderHtml += '<p><strong style="font-size:100%;">Status:'
							+ '<span style="color: blue;">' + order.orderStatus
							+ '</span>';
					+'</strong></p> '

					j++;
					for (let i = 0; i < order.cartItems.length; i++) {
						let cartItem = order.cartItems[i];
						let product = cartItem.product;

						orderHtml += '<div class="cartItem">';
						orderHtml += '<div class="product-image"> <form action="ProductPage" method="POST"> <input type="hidden" name="productId" value="'
								+ product.productId +
						'"><div onclick="submitForm(this.parentNode)"><img src="'
								+ product.image
								+ '" alt="'
								+ product.name
								+ '" style="cursor: pointer;" class="product-image"></div></form></div>';

						orderHtml += '<div class="cartItemDetails">';
						orderHtml += '<p><strong style="font-size:100%;">Product Name:'
								+ '<span style="color: blue;">'
								+ product.name
								+ '</span>' + '</strong></p> ';
						;
						orderHtml += '<p><strong style="font-size:100%;">Price:'
								+ '<span style="color: green;">'
								+ cartItem.price + '</span>' + '</strong></p> ';

						orderHtml += '<p><strong style="font-size:100%;">Quantity:'
								+ '<span style="color: blue;">'
								+ cartItem.quantity + '</span></strong>'
						'</p> ';
						orderHtml += '</div>';
						orderHtml += '</div>';
					}

					orderHtml += '</div>';
					orderHtml += '</div>';

					ordersContainer.append(orderHtml);
				}
			}

			$(".orderHeader").click(function() {
				$(this).next(".orderDetails").slideToggle("fast");
			});
		}
	</script>
	<jsp:include page="fragments/footer.jsp" />
</body>
</html>
