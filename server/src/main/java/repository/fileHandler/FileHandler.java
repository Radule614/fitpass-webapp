package repository.fileHandler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import repository.util.LocalDateAdapter;

public class FileHandler<T> implements IFileHandler<T> {
	private String filePath;
	// Da bi se hard bound-ovalo i T tokom compile time-a
	private TypeToken<ArrayList<T>> listType;
	private Gson deserializeGson;
	
	public FileHandler(String fp, TypeToken<ArrayList<T>> listType, Gson deserializeGson) {
		filePath = fp;
		this.listType = listType;
		this.deserializeGson = deserializeGson;
	}
	
	@Override
	public ArrayList<T> readAll() {
    	ArrayList<T> readData = null;
    	try {
    		System.out.println(filePath);
    		File file = new File(filePath);
    		if(!file.exists()) {
    			readData = new ArrayList<T>();
    		} else {
    			BufferedReader fileReader = new BufferedReader(new FileReader(file));
    			readData = deserializeGson.fromJson(fileReader, listType.getType());
    			fileReader.close();
    		}
    	} catch(Exception ex) {
    		ex.printStackTrace();
    	}
		return readData;
	}
	
	@Override
	public void saveAll(ArrayList<T> data) {
		Gson gson = new GsonBuilder()
				.registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
				.setPrettyPrinting()
				.serializeNulls()
				.create();
		try {
			File file = new File(filePath);
			file.createNewFile();
			if(file.exists()) {
				BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file));
				gson.toJson(data, listType.getType(), fileWriter);
				fileWriter.flush();
				fileWriter.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
}
