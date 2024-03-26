package model;

public class PaymentMethod {
	private String cardHolderName;
	private String cardNumber;
	private String expirationMonth;
	private String expirationYear;
	private int paymentMethodId;
	private String paymentMethodName;
	private int userIdDetails;

	public PaymentMethod() {
	}

	public String getCardHolderName() {
		return cardHolderName;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public String getexpirationMonth() {
		return expirationMonth;
	}

	public String getexpirationYear() {
		return expirationYear;
	}

	public int getPaymentMethodId() {
		return paymentMethodId;
	}

	public String getPaymentMethodName() {
		return paymentMethodName;
	}

	public int getUserIdDetails() {
		return userIdDetails;
	}

	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public void setexpirationMonth(String expirationMonth) {
		this.expirationMonth = expirationMonth;
	}

	public void setexpirationYear(String expirationYear) {
		this.expirationYear = expirationYear;
	}

	public void setPaymentMethodId(int paymentMethodId) {
		this.paymentMethodId = paymentMethodId;
	}

	public void setPaymentMethodName(String paymentMethodName) {
		this.paymentMethodName = paymentMethodName;
	}

	public void setUserIdDetails(int userIdDetails) {
		this.userIdDetails = userIdDetails;
	}

}
