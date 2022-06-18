package repository;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import model.User;
import model.UserType;
import model.admin.Admin;
import model.customer.Customer;
import model.manager.Manager;
import model.trainer.Trainer;
import model.utility.Gender;
import repository.fileHandler.FileHandler;
import repository.generic.GenericRepository;
import repository.util.RuntimeTypeAdapterFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import repository.util.LocalDateAdapter;

public class UserRepository extends GenericRepository<User> {
	private static UserRepository instance;
	
	public static UserRepository getInstance(){
		if(instance == null){
			instance = new UserRepository();
		}
		return instance;
		
	}

	private UserRepository() {
		createFileHandlerAndReadData();
//		Customer customer = new Customer();
//		customer.username = "Radule614";
//		customer.firstname = "Rade";
//		customer.lastname = "Stojanovic";
//		customer.password = "123";
//		customer.gender = Gender.MALE;
//		customer.points = 25;
//		customer.dateOfBirth = LocalDate.of(1999, 9, 21);
//
//		Trainer trainer = new Trainer();
//		trainer.username = "trainer";
//		trainer.firstname = "ime";
//		trainer.lastname = "prezime";
//		trainer.password = "123";
//		trainer.gender = Gender.FEMALE;
//		trainer.dateOfBirth = LocalDate.of(1995, 5, 12);
//
//		Manager manager = new Manager();
//		manager.username = "manager";
//		manager.firstname = "ime";
//		manager.lastname = "prezime";
//		manager.password = "123";
//		manager.gender = Gender.FEMALE;
//		manager.dateOfBirth = LocalDate.of(1990, 2, 25);
//
//		Admin admin = new Admin();
//		admin.username = "admin";
//		admin.firstname = "ime";
//		admin.lastname = "prezime";
//		admin.password = "123";
//		admin.gender = Gender.FEMALE;
//		admin.dateOfBirth = LocalDate.of(1988, 3, 2);
//
//		data.add(customer);
//		data.add(trainer);
//		data.add(manager);
//		data.add(admin);
//		
//		saveAll();
	}
	
	public void addNewUser(User newUser) {
		this.data.add(newUser);
		this.fileHandler.saveAll(data);
	}
	
	// Private/Protected Helpers
	
	@Override
	protected void createFileHandlerAndReadData() {
		TypeToken<ArrayList<User>> typeToken = new TypeToken<ArrayList<User>>() {};
		this.fileHandler = new FileHandler<User>(
				System.getProperty("user.dir") + File.separator + "src" + File.separator 
				+ "main" + File.separator + "java" + File.separator + "data" + File.separator + "users.json",
				typeToken, this.createGsonDeserializer());
		
		this.data = fileHandler.readAll();
	}

	private Gson createGsonDeserializer() {
		RuntimeTypeAdapterFactory<User> runtimeTypeAdapterFactory =
		RuntimeTypeAdapterFactory
			.of(User.class, "userType")
			.registerSubtype(Customer.class, UserType.CUSTOMER.toString())
			.registerSubtype(Manager.class, UserType.MANAGER.toString())
			.registerSubtype(Trainer.class, UserType.TRAINER.toString())
			.registerSubtype(Admin.class, UserType.ADMIN.toString());
		
		return new GsonBuilder()
				.registerTypeAdapterFactory(runtimeTypeAdapterFactory)
				.registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
				.create();
	}

}
