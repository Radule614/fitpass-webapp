package controller;

import java.time.LocalDate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dto.user.AdminDTO;
import dto.user.CustomerDTO;
import dto.user.ManagerDTO;
import dto.user.TrainerDTO;
import model.User;
import model.admin.Admin;
import model.customer.Customer;
import model.manager.Manager;
import model.trainer.Trainer;
import repository.util.LocalDateAdapter;
import service.UserService;
import spark.Request;
import spark.Response;

public class UserController {
    public static String getUser(Request request, Response response){
        Gson g = new GsonBuilder()
        		.registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
        		.create();
        String username = request.attribute("username");
        User user = new UserService().getUser(username);
        response.type("application/json");

        if      (user instanceof Customer)  return g.toJson(new CustomerDTO((Customer) user));
        else if (user instanceof Manager)   return g.toJson(new ManagerDTO((Manager) user));
        else if (user instanceof Trainer)   return g.toJson(new TrainerDTO((Trainer) user));
        else if (user instanceof Admin)     return g.toJson(new AdminDTO((Admin) user));

        return null;
    }

    public static String getUsers(Request request, Response response){
        response.type("application/json");
        
        return "";
    }
}
