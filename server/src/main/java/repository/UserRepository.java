package repository;

import java.util.ArrayList;

import model.User;
import model.customer.Customer;
import model.utility.Gender;

public class UserRepository {
	private static UserRepository instance;
	ArrayList<User> users;
	
	private UserRepository() {
		users = new ArrayList<User>();
		
		Customer customer = new Customer();
		customer.username = "Radule614";
		customer.firstname = "Rade";
		customer.lastname = "Stojanovic";
		customer.password = "123";
		customer.gender = Gender.MALE;
		customer.points = 25;
		users.add(customer);
	}

	public static UserRepository getInstance(){
		if(instance == null){
			instance = new UserRepository();
		}
		return instance;
	}
	
	public ArrayList<User> getUsers(){
		return this.users;
	}
}
