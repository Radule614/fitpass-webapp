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
    
    public static String searchFacilities(Request request, Response response) {
    	String searchText = request.params("searchText");
    	Gson g = new Gson();
    	FacilityService facService = new FacilityService();
    	response.type("application/json");
    	if(searchText == null) {
    		return g.toJson(facService.getAllFacilities());
    	}
    	if(searchText.contains("&")) {
    		String[] parts = searchText.split("&");
    		String searchName = parts[0];
    		String facilityTypeText = parts[1];
    		String avgGradeRangeText = parts[2];
    		return g.toJson(facService.getRequestedFacilites(searchName, facilityTypeText, avgGradeRangeText));
    	}
    	return g.toJson(facService.getAllFacilities());
    }

	public static void addFacility(Request request, Response response){
		
	}
}
