window.addEventListener("load", function() {
	let firstNameInput = document.getElementById("firstNameInput");
	firstNameInput.addEventListener("input", validatefirstName);

	let lastNameInput = document.getElementById("lastNameInput");
	lastNameInput.addEventListener("input", validatelastName);

	let phoneNumberInput = document.getElementById("phoneNumberInput");
	phoneNumberInput.addEventListener("input", validatephoneNumber);

	let homeAddressInput = document.getElementById("homeAddressInput");
	homeAddressInput.addEventListener("input", validateAddress);


	let form = document.getElementById("updateForm");
	form.addEventListener("submit", validateForm);

});
function validateForm(event) {


	if (!validatefirstName() || !validatelastName() || !validatephoneNumber() || !validateAddress()) {
		event.preventDefault();
		return false;
	}

	return true;
}
function validatefirstName() {
	let firstNameInput = document.getElementById("firstNameInput").value;
	let usernameRegex = /^[a-zA-Z ]{1,30}$/;

	if (!usernameRegex.test(firstNameInput)) {
		showError("firstNameError", "Invalid first Name (1-30 characters)");
		return false;
	} else {
		clearErrors("firstNameError");
		return true;
	}
}

function validatelastName() {
	let lastNameInput = document.getElementById("lastNameInput").value;
	let usernameRegex = /^[a-zA-Z ]{1,30}$/;

	if (!usernameRegex.test(lastNameInput)) {
		showError("lastNameError", "Invalid last Name (1-30 characters)");
		return false;
	} else {
		clearErrors("lastNameError");
		return true;
	}
}

function validatephoneNumber() {
	let phoneNumberInput = document.getElementById("phoneNumberInput").value;
	let phoneNumberRegex = /^[0-9]{10}$/;


	if (!phoneNumberRegex.test(phoneNumberInput)) {
		showError("phoneNumberError", "Invalid phone number (10 numbers)");
		return false;
	} else {
		clearErrors("phoneNumberError");
		return true;
	}
}
function validateAddress() {
	let homeAddressInput = document.getElementById("homeAddressInput").value;
	let homeAddressRegex = /^[a-zA-Z0-9 ]{1,100}$/;


	if (!homeAddressRegex.test(homeAddressInput)) {
		showError("homeAddressError", "Invalid home address (1-100 characters)");
		return false;
	} else {
		clearErrors("homeAddressError");
		return true;
	}
}
function showError(elementId, message) {
	let errorDiv = document.getElementById(elementId);
	errorDiv.innerHTML = message;
	errorDiv.style.display = "flex";
}

function clearErrors(elementId) {

	let errorDiv = document.getElementById(elementId);
	errorDiv.innerHTML = "";

}
