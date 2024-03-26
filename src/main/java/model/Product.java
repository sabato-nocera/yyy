package model;

import java.math.BigDecimal;

public class Product {
	private String category;
	private boolean deleted;
	private String description;
	private String imagePath;
	private String name;
	private BigDecimal price;
	private int productId;
	private boolean recommended;

	public Product() {
	}

	public String getCategory() {
		return category;
	}

	public String getDescription() {
		return description;
	}

	public String getImagePath() {
		return imagePath;
	}

	public String getName() {
		return name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public int getProductId() {
		return productId;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public boolean isRecommended() {
		return recommended;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public void setRecommended(boolean recommended) {
		this.recommended = recommended;
	}
}
