package repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import dto.user.UpdateUserDTO;
import model.User;
import model.UserType;
import model.admin.Admin;
import model.customer.Customer;
import model.facility.Content;
import model.facility.Facility;
import model.manager.Manager;
import model.trainer.Trainer;
import repository.fileHandler.FileHandler;
import repository.generic.GenericRepository;
import repository.util.LocalDateAdapter;
import repository.util.RuntimeTypeAdapterFactory;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;

public class ContentRepository extends GenericRepository<Content> {
	private static ContentRepository instance;

	public static ContentRepository getInstance(){
		if(instance == null){
			instance = new ContentRepository();
		}
		return instance;

	}

	private ContentRepository() {
		createFileHandlerAndReadData();
	}

	public Content getById(String id){
		for (Content content: getAll())
			if(content.id.equals(id))
				return content;
		return null;
	}

	@Override
	protected void createFileHandlerAndReadData() {
		TypeToken<ArrayList<Content>> typeToken = new TypeToken<ArrayList<Content>>() {};
		this.fileHandler = new FileHandler<>(
				System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator
						+ "java" + File.separator + "data" + File.separator + "content.json", typeToken, new Gson());
		this.data = fileHandler.readAll();
	}


}
