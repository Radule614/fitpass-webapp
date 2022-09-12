package repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.trainer.Training;
import repository.fileHandler.FileHandler;
import repository.generic.GenericRepository;
import repository.util.LocalDateTimeAdapter;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class TrainingRepository extends GenericRepository<Training> {
	private static TrainingRepository trainingRepository;
	
	public static TrainingRepository getInstance() {
		if(trainingRepository == null) {
			trainingRepository = new TrainingRepository();
		}
		
		return trainingRepository;
	}
	
	private TrainingRepository() {
		createFileHandlerAndReadData();
	}
	
	@Override
	protected void createFileHandlerAndReadData() {
		TypeToken<ArrayList<Training>> typeToken = new TypeToken<ArrayList<Training>>() {};
		this.fileHandler = new FileHandler<Training>(System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + 			File.separator + "java" + File.separator + "data" + File.separator + "trainings.json", typeToken , createDeserializeGson());
		this.data = this.fileHandler.readAll();
	}
	
	private Gson createDeserializeGson() {
		return new GsonBuilder()
					.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
					.create();
	}
}
