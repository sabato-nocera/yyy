$(document)
	.ready(
		function() {
			$('.product-quantity').each(
				function() {
					let quantity = $(this).val();
					let errorText = $(this).closest('tr').find(
						'.error');
					checkQuantityValidity(quantity, errorText);
				});

			$('form').on(
				'submit',
				function(event) {
					let errorText = $(this).find('.error');
					let quantity = $(this).find(
						'.product-quantity').val();
					if (!checkQuantityValidity(quantity,
						errorText)) {
						event.preventDefault();
						return false;
					}
				});

			$('.product-quantity')
				.on(
					'input',
					function() {
						let quantity = $(this).val();
						let productId = $(this).data(
							'product-id');
						let tr = $(this).closest('tr');
						let errorText = tr.find('.error');
						checkQuantityValidity(quantity,
							errorText);

						$
							.ajax({
								url: 'update-quantity',
								type: 'POST',
								dataType: 'json',
								contentType: 'application/json',
								data: JSON
									.stringify({
										quantity: quantity,
										productId: productId
									}),
								success: function() {
									tr
										.find(
											'.product-quantity')
										.val(
											quantity);
									location.reload();
								},
								error: function() {
									errorText
										.text('An error occurred updating the quantity. (min 1 - max 10)');
								}
							});
					});

			function checkQuantityValidity(quantity, errorText) {
				errorText.text('');

				if (quantity < 1) {
					errorText
						.text('Quantity must be 1 or greater.');
					return false;
				} else if (quantity > 10) {
					errorText.text('Quantity must be 10 or less.');

					return false;
				}

				return true;
			}
		});

function submitForm(form) {
	form.submit();
}
