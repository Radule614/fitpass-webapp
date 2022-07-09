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
        Gson g = new GsonBuilder()
        		.registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
        		.create();
        String username = request.attribute("username");
        User user = new UserService().getUser(username);
        response.type("application/json");

        return g.toJson(user.getDTO());
    }

    public static String getFilteredUsers(Request request, Response response){
        response.type("application/json");
        Gson g = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();

        UsersFilterDTO dto = new Gson().fromJson(request.body(), UsersFilterDTO.class);

        ArrayList<UserDTO> userDTOs = new ArrayList<>();
        String json = "";
        try{
            ArrayList<User> users = new UserService().getAll();
            if(dto.sort.type != null){
                if (dto.sort.reverse)   users.sort(User.COMPARATORS.get(dto.sort.type).reversed());
                else                    users.sort(User.COMPARATORS.get(dto.sort.type));
            }
            for (User user: users) userDTOs.add(user.getDTO());
            json = g.toJson(userDTOs);
        }catch (Exception e){
            e.printStackTrace();
        }
        return json;
    }
}
