package model;

import model.utility.Gender;

public abstract class User {
	public final UserType userType;

	public String username;
	public String password;
	public String firstname;
	public String lastname;
	public Gender gender;

	protected User(UserType userType) {
		this.userType = userType;
	}
}
