package controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dto.user.*;
import model.User;
import model.UserType;
import repository.util.LocalDateAdapter;
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
        Gson g = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();

        try{
            String username = request.attribute("username");
            User user = new UserService().getUser(username);
            return g.toJson(user.getDTO());
        }catch (Exception e){
            e.printStackTrace();
            response.status(400);
            return Utility.convertMessageToJSON("Error while parsing input data");
        }
    }

    public static String getFilteredUsers(Request request, Response response){
        response.type("application/json");
        Gson g = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();

        ArrayList<UserDTO> temp = new ArrayList<>();
        try{
            UsersFilterDTO dto = new Gson().fromJson(request.body(), UsersFilterDTO.class);
            for (User user: new UserService().getFilteredUsers(dto)) temp.add(user.getDTO());
            return g.toJson(temp);
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



    //PRIVATE

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
