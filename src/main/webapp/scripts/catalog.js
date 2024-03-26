document.addEventListener('DOMContentLoaded', function() {
	let minPriceSlider = document.getElementById('minPrice');
	let maxPriceSlider = document.getElementById('maxPrice');
	let minPriceText = document.getElementById('minPriceText');
	let maxPriceText = document.getElementById('maxPriceText');
	let form = document.getElementById('filterForm');
	let errorElement = document.querySelector("p.error");

	minPriceSlider.addEventListener('input', function() {
		minPriceText.value = minPriceSlider.value;
	});

	maxPriceSlider.addEventListener('input', function() {
		maxPriceText.value = maxPriceSlider.value;
	});

	minPriceText.addEventListener('input', function() {
		minPriceSlider.value = minPriceText.value;
	});

	maxPriceText.addEventListener('input', function() {
		maxPriceSlider.value = maxPriceText.value;
	});

	form.addEventListener('submit', function(event) {
		event.preventDefault();

		if (!validateForm()) {
			return;
		}

		form.submit();
	});

	function showError(errorMessage) {
		if (errorElement) {
			errorElement.textContent = errorMessage;
		} else {
			errorElement = document.createElement("p");
			errorElement.textContent = errorMessage;
			errorElement.classList.add("error");
			document.body.appendChild(errorElement);
		}
	}

	function validateForm() {
		let minPrice = parseFloat(minPriceText.value);
		let maxPrice = parseFloat(maxPriceText.value);

		if (isNaN(minPrice) || isNaN(maxPrice) || minPrice > maxPrice) {
			showError("Error retrieving order data.");
			return false;
		}

		return true;
	}
});
