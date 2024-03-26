<!DOCTYPE html>
<html lang="en">
<head>
<title>Error</title>
<link rel="stylesheet" type="text/css"
	href="/TechBuild/styles/error.css">
<link rel="icon" href="/TechBuild/images/icon/TechBuild-16.ico">

</head>
<body>

	<jsp:include page="fragments/header.jsp" />

	<div class="container">
		<h1>Error</h1>
		<p>${errorMessage}
			<%
			String errorMessage = (String) request.getAttribute("javax.servlet.error.message");
				if (errorMessage == null) {
					errorMessage = "There was an unexpected error.";
				}
				out.println(errorMessage);
			%>
		</p>
	</div>

	<jsp:include page="fragments/footer.jsp" />

</body>
</html>
