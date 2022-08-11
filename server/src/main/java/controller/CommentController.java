package controller;

import com.google.gson.Gson;
import dto.comment.CommentApprovalDTO;
import dto.comment.CommentDTO;
import model.User;
import model.facility.Comment;
import service.CommentService;
import service.UserService;
import spark.Request;
import spark.Response;
import utility.Utility;

import java.util.ArrayList;

public class CommentController {
    public static String getAllComments(Request request, Response response) {
        response.type("application/json");
        String facility_id = request.params("facility_id");
        try{
            return new Gson().toJson(commentsToDTOs(new CommentService().getAllFacilityComments(facility_id)));
        }catch (Exception e){
            e.printStackTrace();
            response.status(400);
            return Utility.convertMessageToJSON("Couldn't fetch comments");
        }
    }

    public static String commentApproval(Request request, Response response){
        response.type("application/json");
        try{
            CommentApprovalDTO dto = new Gson().fromJson(request.body(), CommentApprovalDTO.class);
            new CommentService().commentApproval(dto);
            return Utility.convertMessageToJSON("Approval successful.");
        }catch (Exception e){
            e.printStackTrace();
            response.status(400);
            return Utility.convertMessageToJSON("Couldn't approve or disapprove comment.");
        }
    }


    private static ArrayList<CommentDTO> commentsToDTOs(ArrayList<Comment> comments){
        ArrayList<CommentDTO> DTOs = new ArrayList<>();
        UserService userService = new UserService();
        for(Comment comment: comments){
            CommentDTO temp = new CommentDTO(comment);
            User user = userService.getUser(comment.user_id);
            temp.firstname = user.firstname;
            temp.lastname = user.lastname;
            DTOs.add(temp);
        }
        return DTOs;
    }
}
