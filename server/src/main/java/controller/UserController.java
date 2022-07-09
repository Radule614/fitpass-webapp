package controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dto.facility.DeleteFacilityDTO;
import dto.user.*;
import model.User;
import model.admin.Admin;
import model.customer.Customer;
import model.manager.Manager;
import model.trainer.Trainer;
import repository.util.LocalDateAdapter;
import service.UserService;
import spark.Request;
import spark.Response;
import utility.Utility;

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
}
