package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import model.PaymentMethod;

public class PaymentMethodDAO {
	private DataSource dataSource;

	public PaymentMethodDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void deletePaymentMethod(int paymentMethodId) throws SQLException {
		String query = "DELETE FROM PaymentMethods WHERE payment_method_id = ?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement statement = conn.prepareStatement(query)) {
			statement.setInt(1, paymentMethodId);
			statement.executeUpdate();
		}
	}

	public PaymentMethod getPaymentMethod(int userIdDetails) throws SQLException {
		String query = "SELECT * FROM PaymentMethods WHERE user_id_details = ?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement statement = conn.prepareStatement(query)) {
			statement.setInt(1, userIdDetails);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					PaymentMethod paymentMethod = new PaymentMethod();
					paymentMethod.setPaymentMethodId(resultSet.getInt("payment_method_id"));
					paymentMethod.setUserIdDetails(resultSet.getInt("user_id_details"));
					paymentMethod.setPaymentMethodName(resultSet.getString("payment_method_name"));
					paymentMethod.setCardNumber(resultSet.getString("card_number"));
					paymentMethod.setCardHolderName(resultSet.getString("card_holder_name"));
					paymentMethod.setexpirationMonth(resultSet.getString("expiration_month"));
					paymentMethod.setexpirationYear(resultSet.getString("expiration_year"));
					return paymentMethod;
				} else {
					return null;
				}
			}
		}
	}

	public void savePaymentMethod(int userId) throws SQLException {
		String query = "INSERT INTO PaymentMethods (user_id_details) VALUES (?)";
		try (Connection conn = dataSource.getConnection(); PreparedStatement statement = conn.prepareStatement(query)) {
			statement.setInt(1, userId);

			statement.executeUpdate();
		}
	}

	public void savePaymentMethod(PaymentMethod paymentMethod) throws SQLException {
		String query = "INSERT INTO PaymentMethods (user_id_details, payment_method_name, card_number, card_holder_name, expiration_month, expiration_year) VALUES (?, ?, ?, ?, ?, ?)";
		try (Connection conn = dataSource.getConnection(); PreparedStatement statement = conn.prepareStatement(query)) {
			statement.setInt(1, paymentMethod.getUserIdDetails());
			statement.setString(2, paymentMethod.getPaymentMethodName());
			statement.setString(3, paymentMethod.getCardNumber());
			statement.setString(4, paymentMethod.getCardHolderName());
			statement.setString(5, paymentMethod.getexpirationMonth());
			statement.setString(6, paymentMethod.getexpirationYear());

			statement.executeUpdate();
		}
	}

	public void updatePaymentMethod(PaymentMethod paymentMethod) throws SQLException {
		String query = "UPDATE PaymentMethods SET payment_method_name = ?, card_number = ?, card_holder_name = ?, expiration_month = ?, expiration_year  = ? WHERE user_id_details = ?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement statement = conn.prepareStatement(query)) {
			statement.setString(1, paymentMethod.getPaymentMethodName());
			statement.setString(2, paymentMethod.getCardNumber());
			statement.setString(3, paymentMethod.getCardHolderName());
			statement.setString(4, paymentMethod.getexpirationMonth());
			statement.setString(5, paymentMethod.getexpirationYear());
			statement.setInt(6, paymentMethod.getUserIdDetails());

			statement.executeUpdate();
		}
	}
}
