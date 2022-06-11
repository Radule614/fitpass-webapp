package repository;

import java.util.ArrayList;

import model.User;
import model.admin.Admin;
import model.customer.Customer;
import model.manager.Manager;
import model.trainer.Trainer;
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

		Trainer trainer = new Trainer();
		trainer.username = "trainer";
		trainer.firstname = "ime";
		trainer.lastname = "prezime";
		trainer.password = "123";
		trainer.gender = Gender.FEMALE;

		Manager manager = new Manager();
		manager.username = "manager";
		manager.firstname = "ime";
		manager.lastname = "prezime";
		manager.password = "123";
		manager.gender = Gender.FEMALE;

		Admin admin = new Admin();
		admin.username = "admin";
		admin.firstname = "ime";
		admin.lastname = "prezime";
		admin.password = "123";
		admin.gender = Gender.FEMALE;

		users.add(customer);
		users.add(trainer);
		users.add(manager);
		users.add(admin);
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
