$(document).ready(function() {
	$("form").submit(function(e) {
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
				$("#cart-notification").text("Item added to cart").fadeIn().delay(1000).fadeOut();
			},
			error: function() {
				$("#cart-notification").text("Item not added to cart").fadeIn().delay(1000).fadeOut();

			}
		});
	});
});
function submitForm(form) {
	form.submit();
}