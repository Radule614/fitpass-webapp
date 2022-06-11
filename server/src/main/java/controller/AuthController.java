package controller;

import com.google.gson.Gson;
import dto.LoginDTO;
import dto.LoginResponseDTO;
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
        return null;
    }

    public static void authenticate(Request request, Response response){
        if(request.requestMethod().equals("OPTIONS")) return;
        String token = request.headers("Authorization").split(" ")[1];
        String username = new AuthService().isTokenValid(token);
        if(username == null){
            halt(401, "{\"message\": \"authentication error\"}");
        }
        request.attribute("username", username);
    }
}
