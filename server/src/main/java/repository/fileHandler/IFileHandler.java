package repository.fileHandler;

import java.util.ArrayList;

public interface IFileHandler<T> {
	void saveAll(ArrayList<T> data);
	ArrayList<T> readAll();
}
