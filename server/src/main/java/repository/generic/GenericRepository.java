package repository.generic;

import java.util.ArrayList;

import model.User;
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

    @Override
    public void add(T item) {
        this.data.add(item);
        this.saveAll();
    }

    @Override
    public boolean delete(T item){
        return data.remove(item);
    }
    
    
    protected abstract void createFileHandlerAndReadData();

}
