package controller;

import com.google.gson.Gson;

import dto.LocationDTO;
import dto.facility.DeleteFacilityDTO;
import dto.facility.FacilityDTO;
import dto.FileDTO;
import model.facility.Facility;
import model.facility.FacilityType;
import model.facility.WorkingHours;
import service.FacilityService;
import spark.Request;
import spark.Response;
import utility.MessageResponse;
import utility.Utility;

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
		Gson g = new Gson();
		Part filePart = request.raw().getPart("file");

		FacilityService service = new FacilityService();

		String name = parseStringInput(request.raw().getPart("name"));
		String startTime = parseStringInput(request.raw().getPart("startTime"));
		String endTime = parseStringInput(request.raw().getPart("endTime"));
		String type = parseStringInput(request.raw().getPart("type"));
		String content = parseStringInput(request.raw().getPart("content"));
		String available = parseStringInput(request.raw().getPart("available"));
		FileDTO fileDTO = new FileDTO(filePart.getInputStream(), getFileName(filePart), getExtension(filePart));

		LocationDTO locationDTO = g.fromJson(parseStringInput(request.raw().getPart("location")), LocationDTO.class);

		MessageResponse messageObject = new MessageResponse();
		if(name == null || name.isEmpty()) messageObject.addMessage("Facility name can't be empty");
		if(service.getByName(name) != null) messageObject.addMessage("Facility with the same name already exists");
		if(!service.checkIfImageValid(fileDTO.extension)) messageObject.addMessage("No image selected");
		Facility facility = null;
		if(messageObject.isEmpty()){
			FacilityDTO facilityDTO = new FacilityDTO(name, FacilityType.valueOf(type), available != null && available.equals("on"), null, new WorkingHours(startTime, endTime), content);
			facility = service.addFacility(facilityDTO, fileDTO);
		}

		if(!messageObject.isEmpty() || facility == null){
			return messageObject.toJSON();
		}
		return new Gson().toJson(facility);
	}

	public static String deleteFacility(Request request, Response response){
		response.type("application/json");
		try{
			DeleteFacilityDTO dto = new Gson().fromJson(request.body(), DeleteFacilityDTO.class);
			if(new FacilityService().deleteFacility(dto)) return "[]";
		}catch (Exception e){
			e.printStackTrace();
			return Utility.convertMessageToJSON("Invalid or no name given");
		}
		return "[]";
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
