<%@ page import="java.util.List"%>
<%@ page import="model.User"%>
<%@ page import="model.Order"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<!DOCTYPE html>

<html lang="en">
<head>
<script src="scripts/jquery-3.7.0.min.js"></script>
<link rel="icon" href="/TechBuild/images/icon/TechBuild-16.ico">

<link rel="stylesheet" type="text/css"
	href="/TechBuild/styles/adminOrderPage.css">
<script src="scripts\asyncForms.js"></script>
<link rel="stylesheet" type="text/css"
	href="/TechBuild/styles/cartNotification.css">

<title>User Orders</title>
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
	<div id="cart-notification"></div>

	<div class="container">

		<section id="Orders">
			<form action="AdminOrderPage" method="POST">
				<h1>Filter by User</h1>
				<select name="userId" id="userId">
					<option value="">All Users</option>
					<%
					List<User> userList = (List<User>) request.getAttribute("userList");
					for (User user : userList) {
					%>
					<option value="<%=user.getUserId()%>"><%=user.getUsername()%></option>
					<%
					}
					%>
				</select> <br> <br>
				<h1>Filter by Date Range</h1>
				<h3>From</h3>
				<input type="date" name="fromDate" id="fromDate">
				<h3>To</h3>
				<input type="date" name="toDate" id="toDate"> <input
					type="submit" value="Apply Filter"> <input type="hidden"
					name="clientToken" value="<%=sessionToken%>">

			</form>

			<table>
				<caption>Users' Orders</caption>

				<thead>
					<tr>
						<th>User ID</th>
						<th>Order ID</th>
						<th>Order Date</th>
						<th>Total Amount</th>
						<th>Status</th>
						<th>Action</th>
					</tr>
				</thead>
				<tbody>
					<%
					List<Order> orderList = (List<Order>) request.getAttribute("orderList");

					for (Order order : orderList) {
					%>
					<tr>
						<td><%=order.getUserId()%></td>
						<td><%=order.getOrderId()%></td>
						<td><%=order.getOrderDate()%></td>
						<td><%=order.getTotalAmount()%>$</td>
						<td><%=order.getorderStatus()%></td>
						<td>
							<form action="ChangeStatusServlet" method="POST">
								<input type="hidden" name="clientToken"
									value="<%=sessionToken%>"> <input type="hidden"
									name="orderId" value="<%=order.getOrderId()%>"> <select
									name="status">
									<option value="Completed">Completed</option>
									<option value="Cancelled">Cancelled</option>
									<option value="Awaiting Shipment">Awaiting Shipment</option>
									<option value="Shipped">Shipped</option>
								</select> <input type="submit" value="Change Status">

							</form>
						</td>
					</tr>
					<%
					}
					%>
				</tbody>
			</table>
		</section>
	</div>

	<jsp:include page="../fragments/footer.jsp" />

</body>
</html>

