package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import model.Order;

public class OrderDAO {
	private DataSource dataSource;

	public OrderDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void deleteOrder(int orderId) throws SQLException {
		String query = "DELETE FROM Orders WHERE order_id = ?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement statement = conn.prepareStatement(query)) {
			statement.setInt(1, orderId);
			statement.executeUpdate();
		}
	}

	public List<Order> getAllOrders() throws SQLException {
		String query = "SELECT * FROM Orders";
		List<Order> orderList = new ArrayList<>();

		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(query);
				ResultSet resultSet = statement.executeQuery()) {
			while (resultSet.next()) {
				Order order = new Order();
				order.setOrderId(resultSet.getInt("order_id"));
				order.setOrderDate(resultSet.getDate("order_date"));
				order.setTotalAmount(resultSet.getBigDecimal("total_amount"));
				order.setUserId(resultSet.getInt("user_id"));
				order.setorderStatus(resultSet.getString("order_status"));

				orderList.add(order);
			}
		}

		return orderList;
	}

	public List<Order> getOrders(int userId) throws SQLException {
		String query = "SELECT * FROM Orders WHERE user_id = ?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement statement = conn.prepareStatement(query)) {
			statement.setInt(1, userId);
			try (ResultSet resultSet = statement.executeQuery()) {
				List<Order> orders = new ArrayList<>();
				while (resultSet.next()) {
					Order order = new Order();
					order.setOrderId(resultSet.getInt("order_id"));
					order.setUserId(resultSet.getInt("user_id"));
					order.setOrderDate(resultSet.getDate("order_date"));
					order.setTotalAmount(resultSet.getBigDecimal("total_amount"));
					order.setorderStatus(resultSet.getString("order_status"));
					orders.add(order);
				}
				return orders;
			}
		}
	}

	public List<Order> getOrders(java.sql.Date fromDate, java.sql.Date toDate) throws SQLException {
		String query = "SELECT * FROM Orders WHERE order_date BETWEEN ? AND ?";
		List<Order> orderList = new ArrayList<>();

		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setDate(1, fromDate);
			statement.setDate(2, toDate);

			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					Order order = new Order();
					order.setOrderId(resultSet.getInt("order_id"));
					order.setOrderDate(resultSet.getDate("order_date"));
					order.setTotalAmount(resultSet.getBigDecimal("total_amount"));
					order.setUserId(resultSet.getInt("user_id"));
					order.setorderStatus(resultSet.getString("order_status"));

					orderList.add(order);
				}
			}
		}

		return orderList;
	}

	public List<Order> getOrders(java.sql.Date fromDate, java.sql.Date toDate, int userId) throws SQLException {
		String query = "SELECT * FROM Orders WHERE order_date BETWEEN ? AND ? AND user_id = ?";
		List<Order> orderList = new ArrayList<>();

		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setDate(1, fromDate);
			statement.setDate(2, toDate);
			statement.setInt(3, userId);

			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					Order order = new Order();
					order.setOrderId(resultSet.getInt("order_id"));
					order.setOrderDate(resultSet.getDate("order_date"));
					order.setTotalAmount(resultSet.getBigDecimal("total_amount"));
					order.setUserId(resultSet.getInt("user_id"));
					order.setorderStatus(resultSet.getString("order_status"));

					orderList.add(order);
				}
			}
		}

		return orderList;
	}

	public int saveOrder(Order order) throws SQLException {
		int generatedOrderId = 0;
		String sql = "INSERT INTO Orders (user_id, order_date, total_amount, order_status) VALUES (?, ?, ?, ?)";
		try (Connection conn = dataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			stmt.setInt(1, order.getUserId());
			stmt.setDate(2, order.getOrderDate());
			stmt.setBigDecimal(3, order.getTotalAmount());
			stmt.setString(4, order.getorderStatus());

			int affectedRows = stmt.executeUpdate();
			if (affectedRows > 0) {
				ResultSet generatedKeys = stmt.getGeneratedKeys();
				if (generatedKeys.next()) {
					generatedOrderId = generatedKeys.getInt(1);
				}
			}
		}
		return generatedOrderId;
	}

	public void updateOrder(Order order) throws SQLException {
		String query = "UPDATE Orders SET user_id = ?, order_date = ?, total_amount = ?, order_status = ? WHERE order_id = ?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement statement = conn.prepareStatement(query)) {
			statement.setInt(1, order.getUserId());
			statement.setDate(2, order.getOrderDate());
			statement.setBigDecimal(3, order.getTotalAmount());
			statement.setString(4, order.getorderStatus());
			statement.setInt(5, order.getOrderId());

			statement.executeUpdate();
		}
	}

	public void updateOrder(String status, int orderId) throws SQLException {
		String query = "UPDATE Orders SET order_status = ? WHERE order_id = ?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement statement = conn.prepareStatement(query)) {
			statement.setString(1, status);
			statement.setInt(2, orderId);

			statement.executeUpdate();
		}
	}
}
