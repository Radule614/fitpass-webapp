package repository;

import model.User;
import model.admin.Admin;
import model.customer.Customer;
import model.manager.Manager;
import model.trainer.Trainer;
import model.utility.Gender;
import repository.generic.GenericRepository;

public class UserRepository extends GenericRepository<User> {
	private static UserRepository instance;
	public static UserRepository getInstance(){
		if(instance == null){
			instance = new UserRepository();
		}
		return instance;
	}

	private UserRepository() {
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

		data.add(customer);
		data.add(trainer);
		data.add(manager);
		data.add(admin);
	}


}
