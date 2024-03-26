package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import model.Payment;

public class PaymentDAO {
	private DataSource dataSource;

	public PaymentDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void deletePayment(int paymentId) throws SQLException {
		String query = "DELETE FROM Payments WHERE payment_id = ?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement statement = conn.prepareStatement(query)) {
			statement.setInt(1, paymentId);
			statement.executeUpdate();
		}
	}

	public Payment getPaymentById(int paymentId) throws SQLException {
		String query = "SELECT * FROM Payments WHERE payment_id = ?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement statement = conn.prepareStatement(query)) {
			statement.setInt(1, paymentId);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					Payment payment = new Payment();
					payment.setPaymentId(resultSet.getInt("payment_id"));
					payment.setOrderIdExt(resultSet.getInt("order_id_ext"));
					payment.setPaymentMethodIdExt(resultSet.getInt("payment_method_id_ext"));
					payment.setPaymentDate(resultSet.getDate("payment_date"));
					return payment;
				}
			}
		}
		return null;
	}

	public void savePayment(Payment payment) throws SQLException {
		String query = "INSERT INTO Payments (order_id_ext, payment_method_id_ext, payment_date) VALUES (?, ?, ?)";
		try (Connection conn = dataSource.getConnection(); PreparedStatement statement = conn.prepareStatement(query)) {
			statement.setInt(1, payment.getOrderIdExt());
			statement.setInt(2, payment.getPaymentMethodIdExt());
			statement.setDate(3, (Date) payment.getPaymentDate());

			statement.executeUpdate();
		}
	}

	public void updatePayment(Payment payment) throws SQLException {
		String query = "UPDATE Payments SET order_id_ext = ?, payment_method_id_ext = ?, payment_date = ? WHERE payment_id = ?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement statement = conn.prepareStatement(query)) {
			statement.setInt(1, payment.getOrderIdExt());
			statement.setInt(2, payment.getPaymentMethodIdExt());
			statement.setDate(3, new java.sql.Date(payment.getPaymentDate().getTime()));
			statement.setInt(4, payment.getPaymentId());

			statement.executeUpdate();
		}
	}
}
