package repository.generic;

import java.util.ArrayList;

public abstract class GenericRepository<T> implements IRepository<T> {
    protected ArrayList<T> data = new ArrayList<T>();

    @Override
    public ArrayList<T> getAll(){
        return (ArrayList<T>) this.data.clone();
    }
}
