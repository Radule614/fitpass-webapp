package controller;

import com.google.gson.Gson;
import dto.auth.LoginDTO;
import dto.auth.LoginResponseDTO;
import dto.auth.RegisterDTO;
import dto.auth.RegisterResponseDTO;
import model.customer.Customer;
import service.AuthService;
import spark.Request;
import spark.Response;

import static spark.Spark.halt;

public class AuthController {
    public static String login(Request request, Response response) {
        Gson g = new Gson();
        LoginDTO loginDTO = g.fromJson(request.body(), LoginDTO.class);

        LoginResponseDTO ret = new AuthService().login(loginDTO);
        if(ret == null){
            halt(401, "{\"message\": \"authentication error\"}");
        }
        String json = g.toJson(ret);
        response.type("application/json");
        return json;
    }

    public static String register(Request request, Response response){
        Gson gson = new Gson();
        RegisterDTO registerDTO = gson.fromJson(request.body(), RegisterDTO.class);
        
        Customer registredCustomer = new AuthService().register(registerDTO);
        if(registredCustomer == null) {
        	halt(401, "Username already in use.");
        }
        RegisterResponseDTO registerResponseDTO = new RegisterResponseDTO();
        registerResponseDTO.username = registredCustomer.username;
        registerResponseDTO.password = registredCustomer.password;
    	String responseData = gson.toJson(registerResponseDTO);
        response.type("application/json");
        return responseData;
    }

    public static void authenticate(Request request, Response response){
        if(request.requestMethod().equals("OPTIONS")) return;
        String token = null;
        try{
            token = request.headers("Authorization").split(" ")[1];
        }catch (Exception e){
            halt(401, "{\"message\": \"no token present\"}");
        }
        String username = new AuthService().isTokenValid(token);
        if(username == null){
            halt(401, "{\"message\": \"authentication error\"}");
        }
        request.attribute("username", username);
    }
}
