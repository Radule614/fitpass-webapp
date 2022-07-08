package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

import model.utility.Gender;

public abstract class User {
	public static final HashMap<String, Comparator<User>> COMPARATORS = initComparators();

	public String username;
	public UserType userType;
	public String password;
	public String firstname;
	public String lastname;
	public LocalDate dateOfBirth;
	public Gender gender;

	protected User(UserType userType) {
		this.userType = userType;
	}
	
	protected User() {}

	@Override
	public String toString() {
		return "User [userType=" + userType + ", username=" + username + ", password=" + password + ", firstname="
				+ firstname + ", lastname=" + lastname + ", dateOfBirth=" + dateOfBirth + ", gender=" + gender + "]";
	}

	private static HashMap<String, Comparator<User>> initComparators(){
		return new HashMap<String, Comparator<User>>(){{
			put("username", new ByUsername());
			put("firstname", new ByFirstname());
			put("lastname", new ByLastname());
		}};
	}

	private static class ByUsername implements Comparator<User>{
		public int compare(User user1, User user2){
			return user1.username.compareTo(user2.username);
		}
	}

	private static class ByFirstname implements Comparator<User>{
		public int compare(User user1, User user2){
			return user1.firstname.compareTo(user2.firstname);
		}
	}

	private static class ByLastname implements Comparator<User>{
		public int compare(User user1, User user2){
			return user1.lastname.compareTo(user2.lastname);
		}
	}
}
