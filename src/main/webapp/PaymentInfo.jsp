<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="model.PaymentMethod"%>
<%@ page import="java.util.Date"%>
<%@ page import="javax.servlet.http.HttpSession"%>


<!DOCTYPE html>
<html lang="en">
<head>
<title>Payment Information</title>
<script src="scripts/paymentInfo.js"></script>
<link rel="stylesheet" type="text/css"
	href="/TechBuild/styles/paymentInfo.css">
<link rel="icon" href="/TechBuild/images/icon/TechBuild-16.ico">

</head>
<body>
	<jsp:include page="fragments/header.jsp" />

	<div class="container-paymentInfoProfile">
		<div class="profile-section">
			<h1>Payment Information</h1>
			<p class="error" style="text-align: center;">
				<%
					if (request.getAttribute("errorMessage") != null) {
					%>

				<%=request.getAttribute("errorMessage")%>

				<%
					}
					%>

			</p>
			<%-- Get the paymentInfo and paymentInfoDetails objects from the session --%>
			<%
			PaymentMethod paymentMethod = (PaymentMethod) session.getAttribute("paymentMethod");

			String sessionToken = (String) session.getAttribute("sessionToken");
			%>

			<div class="profile-details">
				<%-- Display paymentMethod profile information --%>
				<p>
					<strong>Payment Method Name</strong><br>
					<%=paymentMethod.getPaymentMethodName()%>
				</p>
				<p>
					<strong>Card Holder Name</strong><br>
					<%=paymentMethod.getCardHolderName()%>
				</p>
				<p>
					<strong>Card Number</strong><br>
					<%=paymentMethod.getCardNumber()%>
				</p>
				<p>
					<strong>Expiration Month</strong><br>
					<%=paymentMethod.getexpirationMonth()%>
				</p>
				<p>
					<strong>Expiration Year</strong><br>
					<%=paymentMethod.getexpirationYear()%>
				</p>
			</div>
		</div>
		<%
	if (request.getAttribute("errorMessage") != null) {
	%>
		<div class="error">
			<%=request.getAttribute("errorMessage")%>
		</div>
		<%
	}
	%>
		<div class="update-section">
			<h2>Update Card Informations</h2>
			<form id="updateFormPayment" method="post"
				action="updatePaymentMethod" onsubmit="return validateForm(event)">
				<label for="cardHolderNameInput">Card Holder Name</label> <input
					type="text" id="cardHolderNameInput" name="cardHolderName"
					value="<%=paymentMethod.getCardHolderName()%>" required>
				<div id="cardHolderNameError" class="error"></div>

				<label for="cardNumberInput">Card Number</label> <input type="text"
					id="cardNumberInput" name="cardNumber"
					value="<%=paymentMethod.getCardNumber()%>" required>
				<div id="cardNumberError" class="error"></div>

				<label for="expirationMonthInput">Expiration Month</label> <input
					type="text" id="expirationMonthInput" name="expirationMonth"
					value="<%=paymentMethod.getexpirationMonth()%>" required>
				<div id="expirationMonthError" class="error"></div>

				<label for="expirationYearInput">Expiration Year</label> <input
					type="text" id="expirationYearInput" name="expirationYear"
					value="<%=paymentMethod.getexpirationYear()%>" required>
				<div id="expirationYearError" class="error"></div>

				<input type="hidden" name="clientToken" value="<%=sessionToken%>">
				<input type="submit" value="Update Profile">
			</form>
		</div>
	</div>

	<jsp:include page="fragments/footer.jsp" />
</body>
</html>
