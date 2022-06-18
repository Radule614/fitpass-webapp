package repository;

import java.io.File;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.facility.Facility;
import model.facility.FacilityType;
import model.facility.Location;
import model.facility.WorkingHours;
import repository.fileHandler.FileHandler;
import repository.generic.GenericRepository;

public class FacilityRepository extends GenericRepository<Facility> {
    private static FacilityRepository instance;
    
    public static FacilityRepository getInstance(){
        if(instance == null){
            instance = new FacilityRepository();
        }
        return instance;
    }

    private FacilityRepository() {
    	createFileHandlerAndReadData();
    }
    
    // Helpers
    
    @Override
    protected void createFileHandlerAndReadData() {
    	TypeToken<ArrayList<Facility>> typeToken = new TypeToken<ArrayList<Facility>>() {};
    	this.fileHandler = new FileHandler<Facility>(
    			System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator 
    							+ "java" + File.separator + "data" + File.separator + "facilities.json", typeToken, new Gson());
    	this.data = fileHandler.readAll();
    }
}
