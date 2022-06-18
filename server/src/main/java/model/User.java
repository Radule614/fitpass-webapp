package model;

import java.time.LocalDate;
import model.utility.Gender;

public abstract class User {
	public UserType userType;

	public String username;
	public String password;
	public String firstname;
	public String lastname;
	public LocalDate dateOfBirth;
	public Gender gender;

	protected User(UserType userType) {
		this.userType = userType;
	}
	
	protected User() {
		
	}

	@Override
	public String toString() {
		return "User [userType=" + userType + ", username=" + username + ", password=" + password + ", firstname="
				+ firstname + ", lastname=" + lastname + ", dateOfBirth=" + dateOfBirth + ", gender=" + gender + "]";
	}
	
}
