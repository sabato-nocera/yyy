<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="model.User"%>
<%@ page import="model.UserDetails"%>
<%@ page import="java.util.Date"%>
<%@ page import="javax.servlet.http.HttpSession"%>

<!DOCTYPE html >
<html lang="en">
<head>
<title>User Profile</title>
<link rel="icon" href="/TechBuild/images/icon/TechBuild-16.ico">

<link rel="stylesheet" type="text/css"
	href="/TechBuild/styles/userProfile.css">
</head>
<body>
	<jsp:include page="fragments/header.jsp" />

	<div class="container-userProfile">
		<div class="profile-section">
			<h1>User Profile</h1>

			<%-- Get the user and userDetails objects from the session --%>
			<%
			User user = (User) session.getAttribute("user");
			%>
			<%
			UserDetails userDetails = (UserDetails) session.getAttribute("userDetails");
			String sessionToken = (String) session.getAttribute("sessionToken");
			%>
			<p class="error" style="text-align: center;">
				<%
					if (request.getAttribute("errorMessage") != null) {
					%>

				<%=request.getAttribute("errorMessage")%>

				<%
					}
					%>

			</p>
			<div class="profile-details">
				<%-- Display user profile information --%>
				<p>
					<strong>Username</strong><br>
					<%=user.getUsername()%>
				</p>
				<p>
					<strong>Email</strong><br>
					<%=user.getEmail()%>
				</p>
				<p>
					<a href="/TechBuild/PaymentInfo.jsp">Informazioni Metodo di
						Pagamento</a><br>
				</p>
			</div>

			<%-- Display userDetails if it is available --%>
			<%
			if (userDetails != null) {
			%>
			<div class="profile-details">
				<p>
					<strong>First Name</strong><br>
					<%=userDetails.getFirstName()%>
				</p>
				<p>
					<strong>Last Name</strong><br>
					<%=userDetails.getLastName()%>
				</p>
				<p>
					<strong>Phone Number</strong><br>
					<%=userDetails.getPhoneNumber()%>
				</p>
				<p>
					<strong>Home Address</strong><br>
					<%=userDetails.getHomeAddress()%>
				</p>
			</div>
			<%
			} else {
			%>
			<p>User details not available.</p>
			<%
			}
			%>
		</div>

		<div class="update-section">
			<h2>Update Profile</h2>
			<form id="updateForm" method="post" action="updateProfile"
				onsubmit="return validateForm(event)">
				<label for="firstNameInput">First Name</label> <input type="text"
					id="firstNameInput" name="firstName"
					value='<%=userDetails != null ? userDetails.getFirstName() : ""%>'
					required>
				<div id="firstNameError" class="error"></div>

				<label for="lastNameInput">Last Name</label> <input type="text"
					id="lastNameInput" name="lastName"
					value='<%=userDetails != null ? userDetails.getLastName() : ""%>'
					required>
				<div id="lastNameError" class="error"></div>

				<label for="phoneNumberInput">Phone Number</label> <input type="tel"
					id="phoneNumberInput" name="phoneNumber"
					value='<%=userDetails != null ? userDetails.getPhoneNumber() : ""%>'
					required>
				<div id="phoneNumberError" class="error"></div>

				<label for="homeAddressInput">Home Address</label> <input
					type="text" id="homeAddressInput" name="homeAddress"
					value='<%=userDetails != null ? userDetails.getHomeAddress() : ""%>'
					required>
				<div id="homeAddressError" class="error"></div>
				<input type="hidden" name="clientToken" value="<%=sessionToken%>">
				<input type="submit" value="Update Profile">
			</form>
		</div>
	</div>

	<jsp:include page="fragments/footer.jsp" />
	<script src="scripts\userProfile.js" type="text/javascript"></script>

</body>

</html>
