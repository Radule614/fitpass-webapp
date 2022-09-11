package model.facility;

import dto.comment.CommentDTO;
import model.User;
import service.UserService;

import java.time.LocalDate;

public class Comment {
    public String id;           //TODO: generate during comment creation (must be unique)
    public String facility_id;  //facility name
    public String user_id;      //username
    public LocalDate postedOn;

    public String content;
    public boolean approved;

    public Comment() {}
    
    public CommentDTO toDTO() {
    	CommentDTO commentDTO = new CommentDTO(this);
    	User commentCreator = new UserService().getUser(this.user_id);
    	
    	if(commentCreator == null) return null;
    	
    	commentDTO.firstname = commentCreator.firstname;
    	commentDTO.lastname = commentCreator.lastname;
    	
    	return commentDTO;
    }
}
