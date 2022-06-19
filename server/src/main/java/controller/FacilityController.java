package controller;

import com.google.gson.Gson;

import dto.AvgGradeRangeDTO;
import model.facility.FacilityType;
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
//    		if(facilityTypeText.trim().equalsIgnoreCase("all")) {
//    			if(avgGradeRangeText.trim().equalsIgnoreCase("all")) {
//        			return g.toJson(facService.getAllFacilities());
//    			} else {
//    				return g.toJson(facService.getRequestedFacilitiesByAvgGrade(AvgGradeRangeDTO.valueOf(avgGradeRangeText)));
//    			} 
//    		} else {
//    			if(avgGradeRangeText.trim().equalsIgnoreCase("all")) {
//    				return g.toJson(facService.getRequestedFacilitiesByType(facService.getFacilityTypeFromText(facilityTypeText)));
//    			} else {
//    	    		FacilityType facType = facService.getFacilityTypeFromText(facilityTypeText);
//    	    		AvgGradeRangeDTO avgGradeRange = AvgGradeRangeDTO.valueOf(avgGradeRangeText);
//    	    		return g.toJson(facService.getFacilitiesWithRequestedTypeAndAvgGrade(facType, avgGradeRange));
//    			}
//    		}
    	}
    	return g.toJson(facService.getAllFacilities());
    }
}
