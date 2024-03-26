package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import model.Category;

public class CategoryDAO {
	private DataSource dataSource;

	public CategoryDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public List<Category> getAllCategories() throws SQLException {
		List<Category> categories = new ArrayList<>();
		String query = "SELECT * FROM categories";

		try (Connection conn = dataSource.getConnection();
				PreparedStatement statement = conn.prepareStatement(query);
				ResultSet resultSet = statement.executeQuery()) {
			while (resultSet.next()) {
				Category category = new Category();
				category.setCategory(resultSet.getString("category"));
				categories.add(category);
			}
		}

		return categories;
	}
}
