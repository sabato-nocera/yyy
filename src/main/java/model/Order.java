package model;

import java.math.BigDecimal;
import java.sql.Date;

public class Order {

	private Date orderDate;
	private int orderId;
	private String orderStatus;
	private BigDecimal totalAmount;
	private int userId;

	public Order() {
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public int getOrderId() {
		return orderId;
	}

	public String getorderStatus() {
		return orderStatus;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public int getUserId() {
		return userId;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public void setorderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
}
