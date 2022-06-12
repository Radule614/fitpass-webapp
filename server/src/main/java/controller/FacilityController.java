package controller;

import com.google.gson.Gson;
import service.FacilityService;
import spark.Request;
import spark.Response;

public class FacilityController {

    public static String getAllFacilities(Request request, Response response) {
        Gson g = new Gson();
        response.type("application/json");
        return g.toJson(new FacilityService().getAllFacilities());
    }
}
