<link rel="stylesheet" type="text/css"
	href="/TechBuild/styles/header.css">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<header>
	<div class="header-left">
		<h1 style="text-align: left;">TechBuild</h1>
		<nav aria-label="Left NavBar">
			<ul>
				<%@ page import="model.User"%>


				<li><a href="/TechBuild/TechBuild">Home</a></li>
				<li><a href="/TechBuild/Catalog">Catalog</a></li>
				<li><a href="/TechBuild/Cart">Cart</a></li>
				<%
				User user = (User) session.getAttribute("user");

				if (user != null) {
					if (user.getUserRole().equals("admin")) {
				%>
				<li><a href="/TechBuild/AdminCatalogPage">Admin Catalog
						Page</a></li>
				<li><a href="/TechBuild/AdminOrderPage">Admin Orders Page</a> <%
 }
 }
 %>
			</ul>
		</nav>
	</div>

	<div class="header-right">
		<nav aria-label="Right Navbar">
			<ul>
				<%
				if (session.getAttribute("user") == null) {
				%>
				<li><a href="/TechBuild/Register.jsp">Register</a></li>
				<li><a href="/TechBuild/Login.jsp">Login</a></li>
				<%
				} else {
				%>
				<li><a href="/TechBuild/UserProfile.jsp">Profile</a></li>
				<li><a href="/TechBuild/Orders.jsp">Orders</a></li>
				<li><a href="/TechBuild/logout">Logout</a></li>
				<%
				}
				%>
			</ul>
		</nav>
	</div>
</header>
