package service;

import dto.comment.CommentApprovalDTO;
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

    public void commentApproval(CommentApprovalDTO dto){
        for(Comment comment: commentRepository.getAll()){
            if(comment.id.equals(dto.id)) {
                if(dto.approval){
                    comment.approved = true;
                }else {
                    commentRepository.delete(comment);
                }
                break;
            }
        }
        commentRepository.saveAll();
    }

    public void removeByUser(String user_id){
        for(Comment c: commentRepository.getAll()){
            if(c.user_id.equals(user_id)) commentRepository.delete(c);
        }
        commentRepository.saveAll();
    }

    public void removeByFacility(String facility_id){
        for(Comment c: commentRepository.getAll()){
            if(c.facility_id.equals(facility_id)) commentRepository.delete(c);
        }
        commentRepository.saveAll();
    }

    public void addComment(Comment newComment) {
    	commentRepository.add(newComment);
    }
}
