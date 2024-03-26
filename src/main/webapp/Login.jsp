<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html >
<html lang="en">
<head>
<title>Login</title>
<script src="scripts/jquery-3.7.0.min.js"></script>

<link rel="stylesheet" type="text/css"
	href="/TechBuild/styles/register.css">
<link rel="icon" href="/TechBuild/images/icon/TechBuild-16.ico">

</head>
<body>
	<jsp:include page="fragments/header.jsp" />

	<h2>Login</h2>
	<%
	if (request.getAttribute("errorMessage") != null) {
	%>
	<div class="error">
		<%=request.getAttribute("errorMessage")%>
	</div>
	<%
	}
	%>
	<form id="login" action="login" method="POST"
		onsubmit="return validateForm(event)">
		<label for="username">Username</label> <input type="text"
			id="username" name="username" required><br>
		<div id="usernameError" class="error"></div>

		<label for="password">Password</label> <input type="password"
			id="password" name="password" required><br>
		<div id="passwordError" class="error"></div>

		<input type="submit" value="Login">
	</form>

	<script src="scripts\loginCheck.js"></script>
	<jsp:include page="fragments/footer.jsp" />

</body>
</html>
