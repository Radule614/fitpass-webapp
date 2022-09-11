package repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.facility.Grade;
import repository.fileHandler.FileHandler;
import repository.generic.GenericRepository;

import java.io.File;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class GradeRepository extends GenericRepository<Grade> {
	private static GradeRepository instance;
	
	public static GradeRepository getInstance() {
		if(instance == null) {
			instance = new GradeRepository();
		}
		
		return instance;
	}
	
	public void update(Grade g) {
		this.data = (ArrayList<Grade>) this.data.stream()
				.map(grade -> {
					return (grade.getUsername().equals(g.getUsername()) && grade.getFacilityName().equals(g.getFacilityName()) 
							? g : grade);})
				.collect(Collectors.toList());
		saveAll();
	}
	
	private GradeRepository() {
		createFileHandlerAndReadData();
	}
	
	@Override
	protected void createFileHandlerAndReadData() {
		TypeToken<ArrayList<Grade>> typeToken = new TypeToken<ArrayList<Grade>>() {};
		this.fileHandler = new FileHandler<Grade>(System.getProperty("user.dir") + File.separator + "src" + File.separator 
				+ "main" + File.separator + "java" + File.separator + "data" + File.separator + "grades.json", typeToken, new Gson());
		this.data = fileHandler.readAll();
	}

}
