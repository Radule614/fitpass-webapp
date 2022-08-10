package service;

import model.facility.Comment;
import repository.CommentRepository;

import java.util.ArrayList;

public class CommentService {
    public static final CommentRepository commentRepository = CommentRepository.getInstance();
    public CommentService(){}

    public ArrayList<Comment> getAllFacilityComments(String facility_id){
        ArrayList<Comment> temp = new ArrayList<>();
        for(Comment comment: commentRepository.getAll()){
            if(comment.facility_id.equals(facility_id)) temp.add(comment);
        }
        return temp;
    }
}
