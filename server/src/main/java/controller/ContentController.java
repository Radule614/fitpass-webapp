package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.facility.*;
import dto.user.TrainerDTO;
import model.facility.Content;
import model.facility.ContentType;
import model.trainer.Trainer;
import repository.util.LocalDateAdapter;
import service.ContentService;
import service.UserService;
import spark.Request;
import spark.Response;
import utility.Utility;

import javax.servlet.MultipartConfigElement;

import java.time.LocalDate;
import java.util.ArrayList;

import static utility.Utility.parseStringInput;

public class ContentController {
    public static String createContent(Request request, Response response){
        request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
        response.type("application/json");
        try{
            String name = parseStringInput(request.raw().getPart("name"));
            String type = parseStringInput(request.raw().getPart("type"));
            if(name != null && name.isEmpty()) throw new Content.CreateContentException("Content name can't be empty");
            String id = new ContentService().createContent(request.attribute("username"), new AddContentDTO(name, ContentType.valueOf(type)));
            return "{\"id\": \"" + id + "\"}";
        } catch(Content.CreateContentException e){
            e.printStackTrace();
            response.status(400);
            return Utility.convertMessageToJSON(e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            response.status(400);
            return Utility.convertMessageToJSON("Failed to add content to facility");
        }
    }

    public static String deleteContent(Request request, Response response) {
        response.type("application/json");
        try{
            DeleteContentDTO dto = new Gson().fromJson(request.body(), DeleteContentDTO.class);
            new ContentService().deleteContent(request.attribute("username"), dto);
            return Utility.convertMessageToJSON("Content deleted successfully");
        } catch(Content.DeleteContentException e){
            e.printStackTrace();
            response.status(400);
            return Utility.convertMessageToJSON(e.getMessage());
        } catch(Exception e){
            e.printStackTrace();
            response.status(400);
            return Utility.convertMessageToJSON("Failed to delete content from facility");
        }
    }

    public static String addTrainerToContent(Request request, Response response) {
        response.type("application/json");
        Gson g = new GsonBuilder().serializeNulls().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
        try{
            AddTrainerToContentDTO dto = new Gson().fromJson(request.body(), AddTrainerToContentDTO.class);
            Trainer trainer = new ContentService().addTrainerToContent(dto.trainer_id, dto.content_id);
            return g.toJson(trainer.getDTO());
        } catch(Content.AddTrainerToContentException e){
            e.printStackTrace();
            response.status(400);
            return Utility.convertMessageToJSON(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            response.status(400);
            return Utility.convertMessageToJSON("Failed to add trainer to content");
        }
    }
    public static String clearTrainerFromContent(Request request, Response response){
        response.type("application/json");
        try{
            ClearTrainerFromContentDTO dto = new Gson().fromJson(request.body(), ClearTrainerFromContentDTO.class);
            new ContentService().removeTrainerFromContent(dto.content_id);
            return Utility.convertMessageToJSON("Trainer cleared from content successfully");
        }
        catch(Content.ClearTrainerFromContentException e) {
            e.printStackTrace();
            response.status(400);
            return Utility.convertMessageToJSON(e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            response.status(400);
            return Utility.convertMessageToJSON("Failed to clear trainer from content");
        }
    }
    
    public static String getTrainersGroupContent(Request req, Response res) {
    	res.type("application/json");
    	String trainerUsername = req.params("trainerId");
    	ArrayList<Content> trainersGroupContent = new ContentService().getTrainersGroupContent(trainerUsername);
    	return new Gson().toJson(trainersGroupContent);
    }

    public static ArrayList<ContentDTO> contentToDTOs(ArrayList<Content> content){
        ArrayList<ContentDTO> DTOs = new ArrayList<>();
        UserService userService = new UserService();
        for(Content con: content) {
            ContentDTO temp = new ContentDTO(con);
            Trainer trainer = (Trainer) userService.getUser(con.trainer_id);
            if(trainer != null) temp.trainer = (TrainerDTO) trainer.getDTO();
            DTOs.add(temp);
        }
        return DTOs;
    }
}
