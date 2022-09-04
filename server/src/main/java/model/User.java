package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

import dto.user.UserDTO;
import model.admin.Admin;
import model.customer.Customer;
import model.manager.Manager;
import model.trainer.Trainer;
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

	public static User createInstance(UserType userType){
		if		(userType == UserType.CUSTOMER) return new Customer();
		else if	(userType == UserType.ADMIN) 	return new Admin();
		else if	(userType == UserType.MANAGER) 	return new Manager();
		else if (userType == UserType.TRAINER) 	return new Trainer();
		return null;
	}

	@Override
	public String toString() {
		return "User [userType=" + userType + ", username=" + username + ", password=" + password + ", firstname="
				+ firstname + ", lastname=" + lastname + ", dateOfBirth=" + dateOfBirth + ", gender=" + gender + "]";
	}

	public abstract UserDTO getDTO();

	private static HashMap<String, Comparator<User>> initComparators(){
		return new HashMap<String, Comparator<User>>(){{
			put("username", new ByUsername());
			put("firstname", new ByFirstname());
			put("lastname", new ByLastname());
			put("points", new ByPoints());
		}};
	}

	private static class ByUsername implements Comparator<User>{
		public int compare(User user1, User user2){
			return user1.username.toLowerCase().compareTo(user2.username.toLowerCase());
		}
	}

	private static class ByFirstname implements Comparator<User>{
		public int compare(User user1, User user2){
			return user1.firstname.toLowerCase().compareTo(user2.firstname.toLowerCase());
		}
	}

	private static class ByLastname implements Comparator<User>{
		public int compare(User user1, User user2){
			return user1.lastname.toLowerCase().compareTo(user2.lastname.toLowerCase());
		}
	}

	private static class ByPoints implements Comparator<User>{
		public int compare(User user1, User user2){
			if		(user1 instanceof Customer && user2 instanceof Customer) 	return ((Customer) user2).points - ((Customer) user1).points;
			else if	(user1 instanceof Customer) 								return -1;
			else if	(user2 instanceof Customer) 								return 1;
			else 																return 0;
		}
	}

	public static class CreateUserException extends Exception{
		public CreateUserException(String message){
			super(message);
		}
	}
}
