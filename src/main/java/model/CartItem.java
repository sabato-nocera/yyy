package model;

import java.math.BigDecimal;

public class CartItem {
	private int orderId;
	private int orderItemId;
	private BigDecimal price;
	private int productId;
	private int quantity;
	private int userId;

	public CartItem() {
	}

	public int getOrderId() {
		return orderId;
	}

	public int getOrderItemId() {
		return orderItemId;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public int getProductId() {
		return productId;
	}

	public int getQuantity() {
		return quantity;
	}

	public int getUserId() {
		return userId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public void setOrderItemId(int orderItemId) {
		this.orderItemId = orderItemId;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
}
