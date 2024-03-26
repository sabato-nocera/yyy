package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import model.UserDetails;

public class UserDetailsDAO {
	private DataSource dataSource;

	public UserDetailsDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void deleteUserDetails(int userId) throws SQLException {
		String query = "DELETE FROM UserDetails WHERE user_id = ?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement statement = conn.prepareStatement(query)) {
			statement.setInt(1, userId);
			statement.executeUpdate();
		}
	}

	public UserDetails getUserDetails(int userId) throws SQLException {
		String query = "SELECT * FROM UserDetails WHERE user_id = ?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement statement = conn.prepareStatement(query)) {
			statement.setInt(1, userId);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					UserDetails userDetails = new UserDetails();
					userDetails.setUserId(resultSet.getInt("user_id"));
					userDetails.setFirstName(resultSet.getString("user_first_name"));
					userDetails.setLastName(resultSet.getString("user_last_name"));
					userDetails.setPhoneNumber(resultSet.getString("phone_number"));
					userDetails.setHomeAddress(resultSet.getString("home_address"));
					return userDetails;
				}
			}
		}
		return null;
	}

	public void saveUserDetails(UserDetails userDetails) throws SQLException {
		String query = "INSERT INTO UserDetails (user_id, user_first_name, user_last_name, phone_number, home_address) VALUES (?, ?, ?, ?, ?)";
		try (Connection conn = dataSource.getConnection(); PreparedStatement statement = conn.prepareStatement(query)) {
			statement.setInt(1, userDetails.getUserId());
			statement.setString(2, userDetails.getFirstName());
			statement.setString(3, userDetails.getLastName());
			statement.setString(4, userDetails.getPhoneNumber());
			statement.setString(5, userDetails.getHomeAddress());

			statement.executeUpdate();
		}
	}

	public void updateUserDetails(UserDetails userDetails) throws SQLException {
		String query = "UPDATE UserDetails SET user_first_name = ?, user_last_name = ?, phone_number = ?, home_address = ? WHERE user_id = ?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement statement = conn.prepareStatement(query)) {
			statement.setString(1, userDetails.getFirstName());
			statement.setString(2, userDetails.getLastName());
			statement.setString(3, userDetails.getPhoneNumber());
			statement.setString(4, userDetails.getHomeAddress());
			statement.setInt(5, userDetails.getUserId());
			statement.executeUpdate();
		}
	}
}
