package controller;

import com.google.gson.Gson;

import dto.facility.DeleteFacilityDTO;
import dto.facility.FacilityDTO;
import dto.FileDTO;
import model.facility.Facility;
import model.facility.FacilityType;
import model.facility.Location;
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
		FacilityDTO facilityDTO = null;
		try{
			facilityDTO = extractFacilityData(request);
		}catch(Exception e){
			e.printStackTrace();
		}
		FileDTO fileDTO = new FileDTO(filePart.getInputStream(), getFileName(filePart), getExtension(filePart));

		MessageResponse messageObject = validateData(facilityDTO, fileDTO);

		Facility facility = null;
		if(messageObject.isEmpty()) facility = service.addFacility(facilityDTO, fileDTO);

		if(!messageObject.isEmpty() || facility == null) {
			response.status(400);
			return messageObject.toJSON();
		}
		return new Gson().toJson(facility);
	}

	public static String deleteFacility(Request request, Response response){
		response.type("application/json");
		try{
			DeleteFacilityDTO dto = new Gson().fromJson(request.body(), DeleteFacilityDTO.class);
			if(new FacilityService().deleteFacility(dto)) return Utility.convertMessageToJSON("Facility deleted");
		}catch (Exception e){
			e.printStackTrace();
			response.status(400);
			return Utility.convertMessageToJSON("Invalid or no name given");
		}
		return Utility.convertMessageToJSON("Facility not found");
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
	private static MessageResponse validateData(FacilityDTO facilityDTO, FileDTO fileDTO){
		FacilityService service = new FacilityService();
		MessageResponse messageObject = new MessageResponse();

		if(facilityDTO == null || fileDTO == null){
			messageObject.addMessage("Error while parsing data");
			return messageObject;
		}

		if(facilityDTO.name == null || facilityDTO.name.isEmpty()) messageObject.addMessage("Facility name can't be empty");
		if(service.getByName(facilityDTO.name) != null) messageObject.addMessage("Facility with the same name already exists");
		if(!service.checkIfImageValid(fileDTO.extension)) messageObject.addMessage("No image selected");
		return messageObject;
	}
	private static FacilityDTO extractFacilityData(Request request) throws ServletException, IOException {
		String name = parseStringInput(request.raw().getPart("name"));
		String startTime = parseStringInput(request.raw().getPart("startTime"));
		String endTime = parseStringInput(request.raw().getPart("endTime"));
		String type = parseStringInput(request.raw().getPart("type"));
		String content = parseStringInput(request.raw().getPart("content"));
		String available = parseStringInput(request.raw().getPart("available"));
		Location location = new Gson().fromJson(parseStringInput(request.raw().getPart("location")), Location.class);

		return new FacilityDTO(name, FacilityType.valueOf(type), available != null && available.equals("on"), location, new WorkingHours(startTime, endTime), content);
	}
}
