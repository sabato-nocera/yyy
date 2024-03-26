
$(document).ready(function() {
	$("form").submit(function(e) {

		// Check if the form has an action attribute set to "Checkout"
		let allowedActions = ["Checkout", "AdminOrderPage", "FilterCatalogAdmin", "FilterCatalog"];

		if (allowedActions.includes($(this).attr("action"))) {
			// Allow the form to perform a regular submission
			return;
		}

		// Prevent the default form submission behavior
		e.preventDefault();

		let form = $(this);
		let url = form.attr("action");
		let data = form.serialize();
		let method = form.attr("method");

		$.ajax({
			url: url,
			method: method,
			data: data,
			success: function() {
				location.reload();
			},
			error: function() {
				$("#cart-notification")
					.text("There was an error, please try again")
					.fadeIn()
					.delay(1000)
					.fadeOut();
			}
		});
	});
});
