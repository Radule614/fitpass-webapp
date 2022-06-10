package controller;

import spark.Request;
import spark.Response;

public class UserController {
    public static String getUser(Request request, Response response){
        System.out.println((String) request.attribute("username"));
        return "";
    }
}
