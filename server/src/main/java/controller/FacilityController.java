package controller;

import com.google.gson.Gson;

import dto.FacilityDTO;
import dto.FileDTO;
import model.facility.Facility;
import model.facility.FacilityType;
import model.facility.WorkingHours;
import service.FacilityService;
import spark.Request;
import spark.Response;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.http.Part;
import java.io.*;

import java.util.stream.Collectors;

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

	public static String addFacility(Request request, Response response) throws ServletException, IOException {
		request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
		response.type("application/json");

		String name = parseStringInput(request.raw().getPart("name"));
		String startTime = parseStringInput(request.raw().getPart("startTime"));
		String endTime = parseStringInput(request.raw().getPart("endTime"));
		String type = parseStringInput(request.raw().getPart("type"));
		String content = parseStringInput(request.raw().getPart("content"));
		String available = parseStringInput(request.raw().getPart("available"));
		FacilityDTO facilityDTO = new FacilityDTO(name, FacilityType.valueOf(type), available != null && available.equals("on"), null, new WorkingHours(startTime, endTime), content);

		Part filePart = request.raw().getPart("file");
		FileDTO fileDTO = new FileDTO(filePart.getInputStream(), getFileName(filePart), getExtension(filePart));
		Facility facility = new FacilityService().addFacility(facilityDTO, fileDTO);

		if(facility == null) return "{\"message\": \"error\"}";
		return new Gson().toJson(facility);
	}

	private static String parseStringInput(Part part){
		try(InputStream input = part.getInputStream()){
			return new BufferedReader(new InputStreamReader(input)).lines().collect(Collectors.joining("\n"));
		}catch (Exception e){
			//e.printStackTrace();
		}
		return null;
	}

	private static String getFileName(Part part) {
		for (String cd : part.getHeader("content-disposition").split(";")) {
			if (cd.trim().startsWith("filename")) {
				return cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		return null;
	}
	private static String getExtension(Part part){
		for (String cd : part.getHeader("content-disposition").split(";")) {
			if (cd.trim().startsWith("filename")) {
				String[] arr = cd.substring(cd.indexOf('=') + 1).split("\\.");
				return arr[arr.length-1].trim().replace("\"", "");
			}
		}
		return null;
	}
}
