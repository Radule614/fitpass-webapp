package repository.fileHandler;

import java.util.ArrayList;

import com.google.gson.GsonBuilder;

public interface IFileHandler<T> {
	void saveAll(ArrayList<T> data);
	ArrayList<T> readAll();
}
