package dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import model.Product;

public class ProductDAO {
	private static final String CATEGORY = "category";
	private static final String DELETED = "deleted";
	private static final String DESCRIPTION = "description";
	private static final String IMAGE_PATH = "image_path";
	private static final String NAME = "name";
	private static final String PRICE = "price";
	private static final String PRODUCT_ID = "product_id";
	private static final String RECOMMENDED = "recommended";
	private DataSource dataSource;

	public ProductDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public List<Product> getAllProducts() throws SQLException {
		String query = "SELECT * FROM Products WHERE deleted = FALSE";
		try (Connection conn = dataSource.getConnection(); PreparedStatement statement = conn.prepareStatement(query)) {
			List<Product> products = new ArrayList<>();
			try (ResultSet resultSet = statement.executeQuery()) {

				while (resultSet.next()) {
					Product product = new Product();
					product.setProductId(resultSet.getInt(PRODUCT_ID));
					product.setName(resultSet.getString(NAME));
					product.setDescription(resultSet.getString(DESCRIPTION));
					product.setCategory(resultSet.getString(CATEGORY));
					product.setPrice(resultSet.getBigDecimal(PRICE));
					product.setImagePath(resultSet.getString(IMAGE_PATH));
					product.setRecommended(resultSet.getBoolean(RECOMMENDED));
					product.setDeleted(resultSet.getBoolean(DELETED));

					products.add(product);
				}
				return products;
			}
		}
	}

	public List<Product> getAllProducts(BigDecimal minprice, BigDecimal maxprice) throws SQLException {
		String query = "SELECT * FROM Products WHERE price >= ? AND price <= ?  AND deleted = FALSE";

		try (Connection conn = dataSource.getConnection(); PreparedStatement statement = conn.prepareStatement(query)) {
			List<Product> products = new ArrayList<>();
			statement.setBigDecimal(1, minprice);
			statement.setBigDecimal(2, maxprice);
			try (ResultSet resultSet = statement.executeQuery()) {

				while (resultSet.next()) {
					Product product = new Product();
					product.setProductId(resultSet.getInt(PRODUCT_ID));
					product.setName(resultSet.getString(NAME));
					product.setDescription(resultSet.getString(DESCRIPTION));
					product.setCategory(resultSet.getString(CATEGORY));
					product.setPrice(resultSet.getBigDecimal(PRICE));
					product.setImagePath(resultSet.getString(IMAGE_PATH));
					product.setRecommended(resultSet.getBoolean(RECOMMENDED));
					product.setDeleted(resultSet.getBoolean(DELETED));

					products.add(product);
				}
				return products;
			}
		}
	}

	public List<Product> getAllProducts(String category) throws SQLException {
		String query = "SELECT * FROM Products WHERE Category = ? AND deleted = FALSE";

		try (Connection conn = dataSource.getConnection(); PreparedStatement statement = conn.prepareStatement(query)) {
			List<Product> products = new ArrayList<>();
			statement.setString(1, category);
			try (ResultSet resultSet = statement.executeQuery()) {

				while (resultSet.next()) {
					Product product = new Product();
					product.setProductId(resultSet.getInt(PRODUCT_ID));
					product.setName(resultSet.getString(NAME));
					product.setDescription(resultSet.getString(DESCRIPTION));
					product.setCategory(resultSet.getString(CATEGORY));
					product.setPrice(resultSet.getBigDecimal(PRICE));
					product.setImagePath(resultSet.getString(IMAGE_PATH));
					product.setRecommended(resultSet.getBoolean(RECOMMENDED));
					product.setDeleted(resultSet.getBoolean(DELETED));

					products.add(product);
				}
				return products;
			}
		}
	}

	public List<Product> getAllProducts(String category, BigDecimal minprice, BigDecimal maxprice) throws SQLException {
		String query = "SELECT * FROM Products WHERE Category = ? AND price >= ? AND price <= ? AND deleted = FALSE";
		try (Connection conn = dataSource.getConnection(); PreparedStatement statement = conn.prepareStatement(query)) {
			List<Product> products = new ArrayList<>();
			statement.setString(1, category);
			statement.setBigDecimal(2, minprice);
			statement.setBigDecimal(3, maxprice);
			try (ResultSet resultSet = statement.executeQuery()) {

				while (resultSet.next()) {
					Product product = new Product();
					product.setProductId(resultSet.getInt(PRODUCT_ID));
					product.setName(resultSet.getString(NAME));
					product.setDescription(resultSet.getString(DESCRIPTION));
					product.setCategory(resultSet.getString(CATEGORY));
					product.setPrice(resultSet.getBigDecimal(PRICE));
					product.setImagePath(resultSet.getString(IMAGE_PATH));
					product.setRecommended(resultSet.getBoolean(RECOMMENDED));
					product.setDeleted(resultSet.getBoolean(DELETED));

					products.add(product);
				}
				return products;
			}
		}
	}

	public List<Product> getAllProductsAdmin() throws SQLException {
		String query = "SELECT * FROM Products";
		try (Connection conn = dataSource.getConnection(); PreparedStatement statement = conn.prepareStatement(query)) {
			List<Product> products = new ArrayList<>();
			try (ResultSet resultSet = statement.executeQuery()) {

				while (resultSet.next()) {
					Product product = new Product();
					product.setProductId(resultSet.getInt(PRODUCT_ID));
					product.setName(resultSet.getString(NAME));
					product.setDescription(resultSet.getString(DESCRIPTION));
					product.setCategory(resultSet.getString(CATEGORY));
					product.setPrice(resultSet.getBigDecimal(PRICE));
					product.setImagePath(resultSet.getString(IMAGE_PATH));
					product.setRecommended(resultSet.getBoolean(RECOMMENDED));
					product.setDeleted(resultSet.getBoolean(DELETED));
					products.add(product);
				}
				return products;
			}
		}
	}

	public List<Product> getAllRecommendedProducts() throws SQLException {
		String query = "SELECT * FROM Products WHERE recommended = 1 AND deleted = FALSE";
		try (Connection conn = dataSource.getConnection(); PreparedStatement statement = conn.prepareStatement(query)) {
			List<Product> products = new ArrayList<>();
			try (ResultSet resultSet = statement.executeQuery()) {

				while (resultSet.next()) {
					Product product = new Product();
					product.setProductId(resultSet.getInt(PRODUCT_ID));
					product.setName(resultSet.getString(NAME));
					product.setDescription(resultSet.getString(DESCRIPTION));
					product.setCategory(resultSet.getString(CATEGORY));
					product.setPrice(resultSet.getBigDecimal(PRICE));
					product.setImagePath(resultSet.getString(IMAGE_PATH));
					product.setRecommended(resultSet.getBoolean(RECOMMENDED));
					product.setDeleted(resultSet.getBoolean(DELETED));

					products.add(product);
				}
				return products;
			}
		}
	}

	public Product getOrderProduct(int productId) throws SQLException {
		String query = "SELECT * FROM Products WHERE product_id = ?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement statement = conn.prepareStatement(query)) {
			statement.setInt(1, productId);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					Product product = new Product();
					product.setProductId(resultSet.getInt(PRODUCT_ID));
					product.setName(resultSet.getString(NAME));
					product.setDescription(resultSet.getString(DESCRIPTION));
					product.setCategory(resultSet.getString(CATEGORY));
					product.setPrice(resultSet.getBigDecimal(PRICE));
					product.setImagePath(resultSet.getString(IMAGE_PATH));
					product.setRecommended(resultSet.getBoolean(RECOMMENDED));
					product.setDeleted(resultSet.getBoolean(DELETED));
					return product;

				} else {
					return null;
				}
			}
		}
	}

	public Product getProduct(int productId) throws SQLException {
		String query = "SELECT * FROM Products WHERE product_id = ? AND deleted = FALSE";
		try (Connection conn = dataSource.getConnection(); PreparedStatement statement = conn.prepareStatement(query)) {
			statement.setInt(1, productId);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					Product product = new Product();
					product.setProductId(resultSet.getInt(PRODUCT_ID));
					product.setName(resultSet.getString(NAME));
					product.setDescription(resultSet.getString(DESCRIPTION));
					product.setCategory(resultSet.getString(CATEGORY));
					product.setPrice(resultSet.getBigDecimal(PRICE));
					product.setImagePath(resultSet.getString(IMAGE_PATH));
					product.setRecommended(resultSet.getBoolean(RECOMMENDED));
					product.setDeleted(resultSet.getBoolean(DELETED));
					return product;

				} else {
					return null;
				}
			}
		}
	}

	public void saveProduct(Product product) throws SQLException {
		String query = "INSERT INTO Products (name, description, category, price, image_path, recommended) VALUES (?, ?, ?, ?, ?, ?)";
		try (Connection conn = dataSource.getConnection(); PreparedStatement statement = conn.prepareStatement(query)) {
			statement.setString(1, product.getName());
			statement.setString(2, product.getDescription());
			statement.setString(3, product.getCategory());
			statement.setBigDecimal(4, product.getPrice());
			statement.setString(5, product.getImagePath());
			statement.setBoolean(6, product.isRecommended());

			statement.executeUpdate();
		}
	}

	public void toggleProductDeleted(int productId) throws SQLException {
		String query = "UPDATE Products SET deleted = CASE WHEN deleted = 1 THEN 0 ELSE 1 END WHERE product_id = ?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement statement = conn.prepareStatement(query)) {
			statement.setInt(1, productId);
			statement.executeUpdate();
		}
	}

	public void updateProduct(Product product) throws SQLException {
		String query = "UPDATE Products SET name = ?, description = ?, category = ?, price = ?, image_path = ?, recommended = ?, deleted = ?  WHERE product_id = ?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement statement = conn.prepareStatement(query)) {
			statement.setString(1, product.getName());
			statement.setString(2, product.getDescription());
			statement.setString(3, product.getCategory());
			statement.setBigDecimal(4, product.getPrice());
			statement.setString(5, product.getImagePath());
			statement.setBoolean(6, product.isRecommended());
			statement.setBoolean(7, product.isDeleted());
			statement.setInt(8, product.getProductId());
			statement.executeUpdate();
		}
	}

}
