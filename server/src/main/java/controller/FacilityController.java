package controller;

import com.google.gson.Gson;

import com.google.gson.GsonBuilder;
import dto.facility.*;
import dto.FileDTO;
import dto.user.TrainerDTO;
import model.User;
import model.facility.*;
import model.manager.Manager;
import model.trainer.Trainer;
import repository.util.LocalDateAdapter;
import service.ContentService;
import service.FacilityService;
import service.UserService;
import spark.Request;
import spark.Response;
import utility.MessageResponse;
import utility.Utility;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.http.Part;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

import static utility.Utility.parseStringInput;

public class FacilityController {
    public static String getAllFacilities(Request request, Response response) {
        response.type("application/json");
		Gson g = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
		try{
			return g.toJson(facilitiesToDTOs(new FacilityService().getAllFacilities()));
		}catch (Exception e){
			e.printStackTrace();
			response.status(400);
			return Utility.convertMessageToJSON("Couldn't fetch facilities");
		}
    }
    public static String searchFacilities(Request request, Response response) {
    	String searchText = request.params("searchText");
		Gson g = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
    	FacilityService facService = new FacilityService();
    	response.type("application/json");
    	if(searchText == null) {
    		return g.toJson(facilitiesToDTOs(facService.getAllFacilities()));
    	}
    	if(searchText.contains("&")) {
    		String[] parts = searchText.split("&");
    		String searchName = parts[0];
    		String facilityTypeText = parts[1];
    		String avgGradeRangeText = parts[2];
    		return g.toJson(facilitiesToDTOs(facService.getRequestedFacilites(searchName, facilityTypeText, avgGradeRangeText)));
    	}
    	return g.toJson(facilitiesToDTOs(facService.getAllFacilities()));
    }

	public static String addFacility(Request request, Response response) throws ServletException, IOException {
		request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
		response.type("application/json");
		Part filePart = request.raw().getPart("file");

		FacilityService service = new FacilityService();
		CreateFacilityDTO facilityDTO = null;
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
			return Utility.convertMessageToJSON("Error while parsing data");
		}
		return Utility.convertMessageToJSON("Facility not found");
	}

	public static String setManager(Request request, Response response){
		Gson g = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
		response.type("application/json");
		try{
			SetManagerDTO dto = new Gson().fromJson(request.body(), SetManagerDTO.class);
			Manager manager = new FacilityService().setManager(dto);
			return g.toJson(manager.getDTO());
		}catch (Exception e){
			e.printStackTrace();
			response.status(400);
			return Utility.convertMessageToJSON("Error while parsing data");
		}
	}

	public static String clearManager(Request request, Response response){
		response.type("application/json");
		try{
			ClearManagerDTO dto = new Gson().fromJson(request.body(), ClearManagerDTO.class);
			new FacilityService().clearManager(dto);
			return Utility.convertMessageToJSON("Facility manager cleared");
		}catch (Exception e){
			e.printStackTrace();
			response.status(400);
			return Utility.convertMessageToJSON("Error while parsing data");
		}
	}

	public static String getManagerFacility(Request request, Response response) {
		response.type("application/json");
		try{
			String manager = request.attribute("username");
			Facility f = new FacilityService().getByManager(manager);
			if(f == null) throw new Exception();
			return new Gson().toJson(f);
		}catch(Exception e){
			e.printStackTrace();
			response.status(400);
			return Utility.convertMessageToJSON("Couldn't fetch facility.");
		}
	}

	public static String getFacilityTrainers(Request request, Response response){
		response.type("application/json");
		Gson g = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
		try{
			String facility_id = request.params("id");
			ArrayList<User> temp = new FacilityService().getFacilityTrainers(facility_id);
			return g.toJson(UserController.usersToDTOs(temp));
		}catch (Exception e){
			e.printStackTrace();
			response.status(400);
			return Utility.convertMessageToJSON("Couldn't fetch facility's trainers");
		}
	}

	public static String getFacilityVisitors(Request request, Response response){
		response.type("application/json");
		Gson g = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
		try{
			String facility_id = request.params("id");
			ArrayList<User> temp = new FacilityService().getFacilityVisitors(facility_id);
			return g.toJson(UserController.usersToDTOs(temp));
		}catch (Exception e){
			e.printStackTrace();
			response.status(400);
			return Utility.convertMessageToJSON("Couldn't fetch facility's trainers");
		}
	}

	//PRIVATE

	private static ArrayList<FacilityDTO> facilitiesToDTOs(ArrayList<Facility> facilities){
		ArrayList<FacilityDTO> DTOs = new ArrayList<>();
		UserService userService = new UserService();
		ContentService contentService = new ContentService();
		for(Facility f: facilities){
			FacilityDTO temp = new FacilityDTO(f);
			User user = userService.getUser(f.manager_id);
			if(user != null) temp.manager = userService.getUser(f.manager_id).getDTO();
			temp.content = ContentController.contentToDTOs(contentService.getFacilityContent(f.name));
			DTOs.add(temp);
		}
		return DTOs;
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
	private static MessageResponse validateData(CreateFacilityDTO facilityDTO, FileDTO fileDTO){
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
	private static CreateFacilityDTO extractFacilityData(Request request) throws ServletException, IOException {
		String name = parseStringInput(request.raw().getPart("name"));
		String startTime = parseStringInput(request.raw().getPart("startTime"));
		String endTime = parseStringInput(request.raw().getPart("endTime"));
		String type = parseStringInput(request.raw().getPart("type"));
		String available = parseStringInput(request.raw().getPart("available"));
		Location location = new Gson().fromJson(parseStringInput(request.raw().getPart("location")), Location.class);

		return new CreateFacilityDTO(name, FacilityType.valueOf(type), available != null && available.equals("on"), location, new WorkingHours(startTime, endTime));
	}
}
