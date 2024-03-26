window.addEventListener("load", function() {
	let cardHolderNameInput = document.getElementById("cardHolderNameInput");
	cardHolderNameInput.addEventListener("input", validatecardHolderName);

	let cardNumberInput = document.getElementById("cardNumberInput");
	cardNumberInput.addEventListener("input", validateCardNumber);

	let expirationMonthInput = document.getElementById("expirationMonthInput");
	expirationMonthInput.addEventListener("input", validateExpirationMonth);

	let expirationYearInput = document.getElementById("expirationYearInput");
	expirationYearInput.addEventListener("input", validateExpirationYear);

	let form = document.getElementById("updateFormPayment");
	form.addEventListener("submit", validateForm);
});

function validateForm(event) {

	if (
		!validatecardHolderName() ||
		!validateCardNumber() ||
		!validateExpirationMonth() ||
		!validateExpirationYear()
	) {
		event.preventDefault();
		return false;
	}
	return true;
}

function validateCardNumber() {
	let cardNumber = document.getElementById("cardNumberInput").value;


	if (typeof cardNumber !== "string") {
		showError("cardNumberError", "Invalid card number. Please enter a valid card number");
		return false;
	}




	let cardNumberRegex = /^[0-9 ]{16}$/;

	let trimmedCardNumber = cardNumber.replace(/ /g, "");
	if (!cardNumberRegex.test(trimmedCardNumber)) {
		showError("cardNumberError", "Invalid card number there must be 16 numbers");
		return false;
	}
	if (!/^\d+$/.test(trimmedCardNumber)) {
		showError("cardNumberError", "Invalid card number. Please enter a valid card number");
		return false;
	}

	let sum = 0;
	let doubleDigit = false;
	for (let i = trimmedCardNumber.length - 1; i >= 0; i--) {
		let digit = parseInt(trimmedCardNumber.charAt(i), 10);
		if (doubleDigit) {
			digit *= 2;
			if (digit > 9) {
				digit -= 9;
			}
		}
		sum += digit;
		doubleDigit = !doubleDigit;
	}
	if (sum % 10 === 0) {
		clearError("cardNumberError");
		return true;
	} else {
		showError("cardNumberError", "Invalid card number. Please enter a valid card number");
		return false;
	}
}

function validateExpirationMonth() {
	let expirationMonth = document.getElementById("expirationMonthInput").value;

	let pattern = /^(0[1-9]|1[0-2])$/;
	if (!pattern.test(expirationMonth)) {
		showError(
			"expirationMonthError",
			"Invalid expiration month. Please enter a valid two-digit month (01-12)."
		);
		return false;
	}

	let currentYear = new Date().getFullYear();
	let currentMonth = new Date().getMonth() + 1;
	let expirationYear = document.getElementById("expirationYearInput").value;

	if (
		parseInt(expirationMonth) <= currentMonth &&
		parseInt(expirationYear) === currentYear
	) {
		showError(
			"expirationMonthError",
			"Expiration month must be after the current month."
		);
		return false;
	}

	clearError("expirationMonthError");
	return true;
}

function validateExpirationYear() {
	let expirationYear = document.getElementById("expirationYearInput").value;

	let pattern = /^(20\d{2})$/;
	if (!pattern.test(expirationYear)) {
		showError(
			"expirationYearError",
			"Invalid expiration year. Please enter a valid four-digit year."
		);
		return false;
	}

	let currentYear = new Date().getFullYear();
	if (parseInt(expirationYear) <= currentYear) {
		showError(
			"expirationYearError",
			"Expiration year must be after the current year."
		);
		return false;
	}

	clearError("expirationYearError");
	return true;
}

function validatecardHolderName() {
	let cardHolderName = document.getElementById("cardHolderNameInput").value;

	let usernameRegex = /^[a-zA-Z ]{1,60}$/;

	if (!usernameRegex.test(cardHolderName)) {
		showError(
			"cardHolderNameError",
			"Invalid card holder name (1-60 characters)"
		);
		return false;
	}

	clearError("cardHolderNameError");
	return true;
}

function showError(elementId, message) {
	let errorDiv = document.getElementById(elementId);
	errorDiv.innerHTML = message;
	errorDiv.style.display = "flex";
}

function clearError(elementId) {
	let errorDiv = document.getElementById(elementId);
	errorDiv.innerHTML = "";
}
