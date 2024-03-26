package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import model.User;

public class UserDAO {
	private DataSource dataSource;

	public UserDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void addUser(User user) throws SQLException {
		String query = "INSERT INTO Users (username, password, email, user_role) VALUES (?, ?, ?, ?)";

		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, user.getUsername());
			statement.setString(2, user.getPassword());
			statement.setString(3, user.getEmail());
			statement.setString(4, user.getUserRole());

			statement.executeUpdate();
		}
	}

	public void deleteUser(int userId) throws SQLException {
		String query = "DELETE FROM Users WHERE user_id = ?";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, userId);

			statement.executeUpdate();
		}
	}

	public List<User> getAllUsers() throws SQLException {
		String query = "SELECT * FROM Users";
		List<User> userList = new ArrayList<>();

		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(query);
				ResultSet resultSet = statement.executeQuery()) {
			while (resultSet.next()) {
				User user = new User();
				user.setUserId(resultSet.getInt("user_id"));
				user.setUsername(resultSet.getString("username"));
				user.setPassword(resultSet.getString("password"));
				user.setEmail(resultSet.getString("email"));
				user.setUserRole(resultSet.getString("user_role"));

				userList.add(user);
			}
		}

		return userList;
	}

	public User getUserById(int userId) throws SQLException {
		String query = "SELECT * FROM Users WHERE user_id = ?";

		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, userId);

			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					User user = new User();
					user.setUserId(resultSet.getInt("user_id"));
					user.setUsername(resultSet.getString("username"));
					user.setPassword(resultSet.getString("password"));
					user.setEmail(resultSet.getString("email"));
					user.setUserRole(resultSet.getString("user_role"));
					return user;
				}
			}
		}

		return null;
	}

	public User getUserByUsernamePassword(String Username, String Password) throws SQLException {
		String query = "SELECT * FROM Users WHERE username = ? AND password = ?";

		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, Username);
			statement.setString(2, Password);

			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					User user = new User();
					user.setUserId(resultSet.getInt("user_id"));
					user.setUsername(resultSet.getString("username"));
					user.setPassword(resultSet.getString("password"));
					user.setEmail(resultSet.getString("email"));
					user.setUserRole(resultSet.getString("user_role"));
					return user;
				}
			}
		}

		return null;
	}

	public void updateUser(User user) throws SQLException {
		String query = "UPDATE Users SET username = ?, password = ?, email = ?, user_role = ? WHERE user_id = ?";

		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, user.getUsername());
			statement.setString(2, user.getPassword());
			statement.setString(3, user.getEmail());
			statement.setString(4, user.getUserRole());
			statement.setInt(5, user.getUserId());

			statement.executeUpdate();
		}
	}
}
