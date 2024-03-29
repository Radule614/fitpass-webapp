package repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.facility.Facility;
import repository.fileHandler.FileHandler;
import repository.generic.GenericRepository;

import java.io.File;
import java.util.ArrayList;

public class FacilityRepository extends GenericRepository<Facility> {
    private static FacilityRepository instance;
    public static FacilityRepository getInstance(){
        if (instance == null)
            instance = new FacilityRepository();
        return instance;
    }

    public Facility getByName(String facilityName){
        for (Facility facility: getAll())
            if(facility.name.equals(facilityName))
                return facility;
        return null;
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
