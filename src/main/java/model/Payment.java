package model;

import java.util.Date;

public class Payment {
	private int orderIdExt;
	private Date paymentDate;
	private int paymentId;
	private int paymentMethodIdExt;

	public int getOrderIdExt() {
		return orderIdExt;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public int getPaymentId() {
		return paymentId;
	}

	public int getPaymentMethodIdExt() {
		return paymentMethodIdExt;
	}

	public void setOrderIdExt(int orderIdExt) {
		this.orderIdExt = orderIdExt;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}

	public void setPaymentMethodIdExt(int paymentMethodIdExt) {
		this.paymentMethodIdExt = paymentMethodIdExt;
	}
}
