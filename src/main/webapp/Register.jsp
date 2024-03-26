<!DOCTYPE html >
<html lang="en">
<head>
<title>Registration</title>

<link rel="stylesheet" type="text/css"
	href="/TechBuild/styles/register.css">
<script src="scripts/registrationCheck.js"></script>
<link rel="icon" href="/TechBuild/images/icon/TechBuild-16.ico">

</head>
<body>

	<jsp:include page="fragments/header.jsp" />

	<h2>Registration</h2>
	<%
	if (request.getAttribute("errorMessage") != null) {
	%>
	<div class="error">
		<%=request.getAttribute("errorMessage")%>
	</div>
	<%
	}
	%>


	<form id="registerForm" action="register" method="POST"
		onsubmit="return validateForm(event)">

		<label for="username">Username</label> <input type="text"
			id="username" name="username" required><br>
		<div id="usernameError" class="error"></div>

		<label for="email">Email</label> <input type="email" id="email"
			name="email" required><br>
		<div id="emailError" class="error"></div>

		<label for="password">Password</label> <input type="password"
			id="password" name="password" required><br>
		<div id="passwordError" class="error"></div>

		<input type="submit" value="Register">

	</form>
	<jsp:include page="fragments/footer.jsp" />

</body>
</html>
