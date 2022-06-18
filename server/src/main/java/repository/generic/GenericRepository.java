package repository.generic;

import java.util.ArrayList;
import repository.fileHandler.FileHandler;

public abstract class GenericRepository<T> implements IRepository<T> {
    protected ArrayList<T> data;
    protected FileHandler<T> fileHandler;

    @Override
    public ArrayList<T> getAll(){
        return (ArrayList<T>) this.data.clone();
    }
    
    @Override
    public void saveAll() {
    	fileHandler.saveAll(data);
    }
    
    protected abstract void createFileHandlerAndReadData();
}
