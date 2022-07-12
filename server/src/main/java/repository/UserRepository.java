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
import model.facility.Facility;
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
	}
	
	public void addNewUser(User newUser) {
		this.data.add(newUser);
		this.fileHandler.saveAll(data);
	}

	public User getByUsername(String username){
		for (User user: getAll())
			if(user.username.equals(username))
				return user;
		return null;
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
