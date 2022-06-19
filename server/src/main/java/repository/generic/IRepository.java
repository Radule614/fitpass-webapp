package repository.generic;

import java.util.ArrayList;

public interface IRepository<T> {
	ArrayList<T> getAll();
	void saveAll();
}
