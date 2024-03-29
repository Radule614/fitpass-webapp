package repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.facility.Comment;
import repository.fileHandler.FileHandler;
import repository.generic.GenericRepository;
import repository.util.LocalDateAdapter;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;

public class CommentRepository  extends GenericRepository<Comment> {
    private static CommentRepository instance;
    public static CommentRepository getInstance(){
        if (instance == null)
            instance = new CommentRepository();
        return instance;
    }
    private CommentRepository() {
        createFileHandlerAndReadData();
    }

    @Override
    protected void createFileHandlerAndReadData() {
        TypeToken<ArrayList<Comment>> typeToken = new TypeToken<ArrayList<Comment>>() {};
        this.fileHandler = new FileHandler<Comment>(
                System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator
                        + "java" + File.separator + "data" + File.separator + "comments.json", typeToken, createDeserializeGson());
        this.data = fileHandler.readAll();
    }
    
    private Gson createDeserializeGson() {
    	return new GsonBuilder()
				.registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
				.create();
    }
}
