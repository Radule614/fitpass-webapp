package controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dto.user.*;
import model.User;
import model.UserType;
import model.facility.Facility;
import model.manager.Manager;
import repository.util.LocalDateAdapter;
import service.FacilityService;
import service.UserService;
import spark.Request;
import spark.Response;
import utility.MessageResponse;
import utility.Utility;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;

import static utility.Utility.parseStringInput;

public class UserController {
    public static String getUser(Request request, Response response){
        response.type("application/json");
        Gson g = new GsonBuilder().serializeNulls().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();

        try{
            String username = request.attribute("username");
            User user = new UserService().getUser(username);
            return g.toJson(userToDTO(user));
        }catch (Exception e){
            e.printStackTrace();
            response.status(400);
            return Utility.convertMessageToJSON("Error while parsing input data");
        }
    }

    public static String getFilteredUsers(Request request, Response response){
        response.type("application/json");
        Gson g = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();

        try{
            UsersFilterDTO dto = g.fromJson(request.body(), UsersFilterDTO.class);
            return g.toJson(usersToDTOs(new UserService().getFilteredUsers(dto)));
        }catch (Exception e){
            e.printStackTrace();
            response.status(400);
            return Utility.convertMessageToJSON("Error while parsing filter parameters");
        }
    }

    public static String createUser(Request request, Response response){
        response.type("application/json");
        request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
        Gson g = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();

        try{
            CreateUserDTO dto = extractCreateUserData(request);
            MessageResponse messageObject = validateData(dto);
            if(!messageObject.isEmpty()) {
                response.status(400);
                return messageObject.toJSON();
            }
            return g.toJson(new UserService().createUser(dto));
        }catch(User.CreateUserException e){
            e.printStackTrace();
            response.status(400);
            return Utility.convertMessageToJSON(e.getMessage());
        }catch(Exception e){
            e.printStackTrace();
            response.status(400);
            return Utility.convertMessageToJSON("Error while parsing input data");
        }
    }

    public static String deleteUser(Request request, Response response){
        response.type("application/json");
        try{
            DeleteUserDTO dto = new Gson().fromJson(request.body(), DeleteUserDTO.class);
            if(request.attribute("username").equals(dto.username)) return Utility.convertMessageToJSON("Can't delete this user");
            if(new UserService().deleteUser(dto)) return Utility.convertMessageToJSON("User deleted");
        }catch(Exception e){
            e.printStackTrace();
            response.status(400);
            return Utility.convertMessageToJSON("Error while parsing filter parameters");
        }
        return Utility.convertMessageToJSON("User not found");
    }
    
    public static String updateUser(Request req, Response res) {
    	res.type("application/json");
    	
    	try {
    		User updatedUser = new UserService().updateUser(new Gson().fromJson(req.body(), UpdateUserDTO.class));
        	if(updatedUser != null) {
        		return new GsonBuilder()
        				.registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
        				.create().toJson(updatedUser);
        	}
    	} catch(Exception ex) {
    		ex.printStackTrace();
    		res.status(400);
    		return Utility.convertMessageToJSON("Error while parsing input date");
    	}
    	
    	return Utility.convertMessageToJSON("Failed to updated user.");
    }



    //PRIVATE

    private static UserDTO userToDTO(User user){
        UserDTO temp = user.getDTO();
        if(temp instanceof ManagerDTO && user instanceof Manager) temp.facility = new FacilityService().getByName(((Manager) user).facility_id);
        return temp;
    }

    private static ArrayList<UserDTO> usersToDTOs(ArrayList<User> users){
        ArrayList<UserDTO> DTOs = new ArrayList<>();
        for(User u: users){
            UserDTO temp = u.getDTO();
            if (temp instanceof ManagerDTO && u instanceof Manager) temp.facility = new FacilityService().getByName(((Manager) u).facility_id);
            DTOs.add(temp);
        }
        return DTOs;
    }

    private static MessageResponse validateData(CreateUserDTO dto){
        UserService service = new UserService();
        MessageResponse messageObject = new MessageResponse();

        if(dto == null){
            messageObject.addMessage("Error while parsing data");
            return messageObject;
        }

        Pattern p = Pattern.compile(".*[0-9]+.*");
        Matcher m = p.matcher(dto.password);

        if(dto.username == null || dto.username.isEmpty()) messageObject.addMessage("Username can't be empty");
        if(!service.isUsernameUnique(dto.username)) messageObject.addMessage("User with the same username already exists");
        if(!dto.password.equals(dto.passwordConfirm)) messageObject.addMessage("Password confirmation does not match");
        if(dto.password.length() < 8) messageObject.addMessage("Password has to have 8 or more characters");
        if(!m.matches()) messageObject.addMessage("Password has to contain at least 1 digit");
        return messageObject;
    }
    private static CreateUserDTO extractCreateUserData(Request request) throws ServletException, IOException {
        String username = parseStringInput(request.raw().getPart("username"));
        String firstname = parseStringInput(request.raw().getPart("firstname"));
        String lastname = parseStringInput(request.raw().getPart("lastname"));
        String type = parseStringInput(request.raw().getPart("type"));
        String password = parseStringInput(request.raw().getPart("password"));
        String passwordConfirm = parseStringInput(request.raw().getPart("passwordConfirm"));
        String dateOfBirth = parseStringInput(request.raw().getPart("dateOfBirth"));
        String gender = parseStringInput(request.raw().getPart("gender"));
        return new CreateUserDTO(username, password, passwordConfirm, firstname, lastname, dateOfBirth, gender, UserType.valueOf(type));
    }
}
