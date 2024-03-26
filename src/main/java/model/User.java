package model;

public class User {
	private String email;
	private String password;
	private int userId;
	private String username;
	private String userRole;

	public User() {
		super();
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public int getUserId() {
		return userId;
	}

	public String getUsername() {
		return username;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

}
