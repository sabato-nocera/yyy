package model;

public class UserDetails {
	private String firstName;
	private String homeAddress;
	private String lastName;
	private String phoneNumber;
	private int userId;

	public UserDetails() {
	}

	public String getFirstName() {
		return firstName;
	}

	public String getHomeAddress() {
		return homeAddress;
	}

	public String getLastName() {
		return lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public int getUserId() {
		return userId;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}
