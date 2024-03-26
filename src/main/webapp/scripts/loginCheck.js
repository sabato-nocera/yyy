window.addEventListener("load", function() {
	let usernameInput = document.getElementById("username");
	usernameInput.addEventListener("input", validateUsername);

	let passwordInput = document.getElementById("password");
	passwordInput.addEventListener("input", validatePassword);
	let form = document.getElementById("login");
	form.addEventListener("submit", validateForm);

});


function validateForm(event) {
	clearErrors();
	if (!validateUsername() || !validatePassword()) {
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


function validatePassword() {
	let password = document.getElementById("password").value;
	// almeno una lettera maiuscola, una lettera minuscola, un numero, un carattere speciale, minimo 8 caratteri max 50
	let passwordRegex = /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,50}$/;

	if (!passwordRegex.test(password)) {
		showError("passwordError", "Invalid password (minimum 8 characters, at least one maiusc letter, one symbol, one number and max 50 characters)");
		return false;
	} else {
		clearErrors();
		return true;
	}
}

function showError(elementId, message) {
	let errorDiv = document.getElementById(elementId);
	errorDiv.innerHTML = message;
	errorDiv.style.display = "block";
}

function clearErrors() {
	let errorDivs = document.getElementsByClassName("error");
	for (let i = 0; i < errorDivs.length; i++) {
		errorDivs[i].style.display = "none";
		errorDivs[i].innerHTML = "";
	}
}
