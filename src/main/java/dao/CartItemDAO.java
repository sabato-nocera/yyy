package dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import model.CartItem;

public class CartItemDAO {
	private DataSource dataSource;

	public CartItemDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void addCartItem(CartItem cartItem) throws SQLException {
		String query = "INSERT INTO CartItems (product_id, user_id, quantity, price) VALUES (?, ?, ?, ?)";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, cartItem.getProductId());
			statement.setInt(2, cartItem.getUserId());
			statement.setInt(3, cartItem.getQuantity());
			statement.setBigDecimal(4, cartItem.getPrice());
			statement.executeUpdate();
		}
	}

	public List<CartItem> findCartItemsByUserId(int userId) throws SQLException {
		List<CartItem> cartItems = new ArrayList<>();
		String query = "SELECT * FROM CartItems WHERE user_id = ? AND order_id IS NULL";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, userId);
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					CartItem cartItem = new CartItem();
					cartItem.setOrderItemId(resultSet.getInt("order_item_id"));
					cartItem.setOrderId(resultSet.getInt("order_id"));
					cartItem.setProductId(resultSet.getInt("product_id"));
					cartItem.setUserId(resultSet.getInt("user_id"));
					cartItem.setQuantity(resultSet.getInt("quantity"));
					cartItem.setPrice(resultSet.getBigDecimal("price"));
					cartItems.add(cartItem);
				}
			}
		}

		return cartItems;
	}

	public List<CartItem> findOrderedItemsByOrderId(int userId, int orderId) throws SQLException {
		List<CartItem> cartItems = new ArrayList<>();
		String query = "SELECT * FROM CartItems WHERE user_id = ? AND order_id = ?";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, userId);
			statement.setInt(2, orderId);
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					CartItem cartItem = new CartItem();
					cartItem.setOrderItemId(resultSet.getInt("order_item_id"));
					cartItem.setOrderId(resultSet.getInt("order_id"));
					cartItem.setProductId(resultSet.getInt("product_id"));
					cartItem.setUserId(resultSet.getInt("user_id"));
					cartItem.setQuantity(resultSet.getInt("quantity"));
					cartItem.setPrice(resultSet.getBigDecimal("price"));
					cartItems.add(cartItem);
				}
			}
		}
		return cartItems;
	}

	public List<CartItem> findOrderedItemsByUserId(int userId) throws SQLException {
		List<CartItem> cartItems = new ArrayList<>();
		String query = "SELECT * FROM CartItems WHERE user_id = ? AND order_id IS NOT NULL";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, userId);
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					CartItem cartItem = new CartItem();
					cartItem.setOrderItemId(resultSet.getInt("order_item_id"));
					cartItem.setOrderId(resultSet.getInt("order_id"));
					cartItem.setProductId(resultSet.getInt("product_id"));
					cartItem.setUserId(resultSet.getInt("user_id"));
					cartItem.setQuantity(resultSet.getInt("quantity"));
					cartItem.setPrice(resultSet.getBigDecimal("price"));
					cartItems.add(cartItem);
				}
			}
		}
		return cartItems;
	}

	public void removeAllDeletedItems(int productId) throws SQLException {
		String query = "DELETE FROM CartItems WHERE order_id IS NULL AND product_id = ?";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, productId);
			statement.executeUpdate();

		}
	}

	public void removeCartItem(int userId, int productId) throws SQLException {
		String query = "DELETE FROM CartItems WHERE user_id = ? AND product_id = ?";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, userId);
			statement.setInt(2, productId);
			statement.executeUpdate();
		}
	}

	public void saveAllCartItem(List<CartItem> cartItems) throws SQLException {
		try (Connection connection = dataSource.getConnection()) {
			for (CartItem cartItem : cartItems) {

				int user_id = cartItem.getUserId();
				int productId = cartItem.getProductId();
				int quantity = cartItem.getQuantity();

				String sql = "SELECT * FROM cartItems WHERE user_id = ? AND order_id IS NULL";
				PreparedStatement statement = connection.prepareStatement(sql);
				statement.setInt(1, user_id);
				ResultSet resultSet = statement.executeQuery();

				boolean productIdExists = false;

				while (resultSet.next()) {
					int fetchedProductId = resultSet.getInt("product_id");
					int fetchedOrderId = resultSet.getInt("order_id");

					if (fetchedOrderId == 0 && fetchedProductId == productId) {

						productIdExists = true;
						sql = "UPDATE CartItems SET quantity = ? WHERE product_id = ? AND user_id = ? AND order_id IS NULL";
						statement = connection.prepareStatement(sql);
						statement.setInt(1, quantity);
						statement.setInt(2, productId);
						statement.setInt(3, user_id);
						statement.executeUpdate();
					}
				}

				if (!productIdExists) {
					sql = "INSERT INTO CartItems (product_id, quantity, order_id, user_id) VALUES (?, ?, NULL, ?)";
					statement = connection.prepareStatement(sql);
					statement.setInt(1, productId);
					statement.setInt(2, quantity);
					statement.setInt(3, user_id);
					statement.executeUpdate();

				}

				resultSet.close();
				statement.close();
			}
		}
	}

	public void updateCartItem(int orderId, int userId, BigDecimal price, int productId) throws SQLException {
		String query = "UPDATE CartItems SET order_id = ?, price = ? WHERE user_id = ? AND order_id IS NULL AND product_id = ?";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, orderId);
			statement.setBigDecimal(2, price);
			statement.setInt(3, userId);
			statement.setInt(4, productId);
			statement.executeUpdate();
		}
	}

	public void updateEveryCartItems_orderId(int orderId, int userId) throws SQLException {
		String query = "UPDATE CartItems SET order_id = ? WHERE user_id = ? AND order_id IS NULL";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, orderId);
			statement.setInt(2, userId);
			statement.executeUpdate();
		}
	}
}
