window.addEventListener("load", function() {
	let usernameInput = document.getElementById("username");
	usernameInput.addEventListener("input", validateUsername);

	let emailInput = document.getElementById("email");
	emailInput.addEventListener("input", validateEmail);

	let passwordInput = document.getElementById("password");
	passwordInput.addEventListener("input", validatePassword);
	let form = document.getElementById("registerForm");
	form.addEventListener("submit", validateForm);

});


function validateForm(event) {
	clearErrors();


	if (!validateUsername() || !validateEmail() || !validatePassword()) {
		event.preventDefault();
		return false;
	}

	return true;



}


function validateUsername() {
	let username = document.getElementById("username").value;
	let usernameRegex = /^[a-zA-Z0-9]{5,30}$/;

	if (!usernameRegex.test(username)) {
		showError("usernameError", "Invalid username (5-30 characters, alphanumeric only)");
		return false;
	} else {
		clearErrors();
		return true;
	}
}
function validateEmail() {
	let email = document.getElementById("email").value;
	let emailRegex = /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/;

	if (email.length > 100) {
		showError("emailError", "Email address exceeds the maximum length of 100 characters");
		return false;
	} else if (!emailRegex.test(email)) {
		showError("emailError", "Invalid email address");
		return false;
	} else {
		clearErrors();
		return true;
	}
}

function validatePassword() {
	let password = document.getElementById("password").value;
	let passwordRegex = /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,50}$/;

	if (!passwordRegex.test(password)) {
		showError("passwordError", "Invalid password (minimum 8 characters, at least one maiusc letter, one number, one symbol)");
		return false;
	} else {
		clearErrors();
		return true;
	}
}

function showError(elementId, message) {
	let errorDiv = document.getElementById(elementId);
	errorDiv.innerHTML = message;
	errorDiv.style.display = "flex";
}

function clearErrors() {
	let errorDivs = document.getElementsByClassName("error");
	for (let i = 0; i < errorDivs.length; i++) {
		errorDivs[i].innerHTML = "";
	}
}