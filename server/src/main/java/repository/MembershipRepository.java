package repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.customer.Membership;
import repository.fileHandler.FileHandler;
import repository.generic.GenericRepository;
import repository.util.LocalDateAdapter;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;

public class MembershipRepository extends GenericRepository<Membership> {
	private static MembershipRepository instance;
	
	public static MembershipRepository getInstance() {
		if(instance == null) {
			instance = new MembershipRepository();
		}
		
		return instance;
	}
	
	private MembershipRepository() {
		createFileHandlerAndReadData();
	}
	
	public boolean deactivate(Membership m) {
		m.active = false;
		saveAll();
		return true;
	}
	
	
	@Override
	protected void createFileHandlerAndReadData() {
		// TODO Auto-generated method stub
		TypeToken<ArrayList<Membership>> typeToken = new TypeToken<ArrayList<Membership>>() {};
		this.fileHandler = new FileHandler<Membership>
			(System.getProperty("user.dir") + File.separator + "src" + File.separator 
					+ "main" + File.separator + "java" + File.separator + "data" + File.separator + "memberships.json", 
			typeToken, createDeserializeGson());
		this.data = fileHandler.readAll();
	}
	
	private Gson createDeserializeGson() {
		return new GsonBuilder()
				.registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
				.create();
	}

}
