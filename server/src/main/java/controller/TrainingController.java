package controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.Part;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dto.user.TrainingDTO;
import io.jsonwebtoken.io.IOException;
import model.facility.Facility;
import model.trainer.Training;
import model.trainer.TrainingType;
import repository.util.LocalDateTimeAdapter;
import service.FacilityService;
import service.TrainingService;
import spark.Request;
import spark.Response;
import spark.utils.IOUtils;
import utility.Utility;

public class TrainingController {
	
	public static String getUserTrainings(Request req, Response res) {
		res.type("application/json");
		String username = req.params("username");
		ArrayList<Training> userTrainings = new TrainingService().getUserTrainings(username);
		try {
			return new Gson().toJson(userTrainings);
		} catch(Exception ex) {
			res.status(400);
			return new Gson().toJson("Failed to parse data");
		}
	}
	
	public static String getTrainingWithRequiredContentTypes(Request req, Response res) {
		res.type("application/json");
		String[] contentTypes = new Gson().fromJson(req.body(), String[].class);
		ArrayList<Training> trainings = new TrainingService().getTrainingsWithRequiredContentTypes(contentTypes);
		try {
			return new GsonBuilder()
					.serializeNulls()
					.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
					.create()
					.toJson(convertTrainingsToDTO(trainings));
		} catch(Exception ex) {
			res.status(400);
			return new Gson().toJson("Failed to parse data");
		}
	}
	
	public static String addTraining(Request req, Response res) {
		res.type("application/json");
		req.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
		Training training = new Training();
		Map<String, String[]> parameterMap = req.raw().getParameterMap();
		
		Part uploadedFile = null;
		try {
			uploadedFile = req.raw().getPart("image");
		} catch(Exception ex) {
			ex.printStackTrace();
			res.status(400);
			return new Gson().toJson("Failed to upload file.");
		}
		
		try(InputStream inStream = uploadedFile.getInputStream()) {
			String fs = File.separator;
			String outputPath = System.getProperty("user.dir") + fs + "src" + fs + "main" + fs + "resources" + fs 
					+ "public" + fs + "img" + fs + "trainings" + fs + parameterMap.get("trainerUsername")[0] + 					uploadedFile.getSubmittedFileName(); 
			OutputStream outputStream = new FileOutputStream(outputPath);
			IOUtils.copy(inStream, outputStream);
			outputStream.close();
		} catch(Exception ex) {
			ex.printStackTrace();
			res.status(400);
			return new Gson().toJson("Failed to upload file.");
		}
		
		
		training.setName(parameterMap.get("name")[0]);
		training.setDuration(Integer.parseInt(parameterMap.get("duration")[0]));
		training.setDescription(parameterMap.get("description")[0]);
		training.setTrainerUsername(parameterMap.get("trainerUsername")[0]);
		training.setImgUrl("http://localhost:9999/img/trainings/" + training.getTrainerUsername() + uploadedFile.getSubmittedFileName());
		training.setContentId(parameterMap.get("content")[0]);
		training.setStart(parseLocalDateTime(parameterMap.get("date")[0], parameterMap.get("time")[0]));
		training.setType(TrainingType.valueOf(parameterMap.get("type")[0].toUpperCase().replace("&", "and")));
		training.setFacilityName(parameterMap.get("facilityName")[0]);
		new TrainingService().add(training);
		try {
			return new GsonBuilder()
					.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
					.serializeNulls()
					.create()
					.toJson(training.getDTO());
		} catch(Exception ex) {
			res.status(400);
			ex.printStackTrace();
			System.out.println(ex.getMessage());
			return new Gson().toJson("Failed to serialize data");
		}

	}
	
	public static String removeTraining(Request req, Response res) {
		res.type("application/json");
		String trainingId = req.params("training_id");
		boolean success = new TrainingService().cancel(trainingId);
		try {
			if(success) {
				return new Gson().toJson("Training has been canceled successfully.");
			} else {
				res.status(450);
				return new Gson().toJson("Failed to cancel training, canceling treshold has passed.");
			}
		} catch(Exception ex) {
			res.status(400);
			return new Gson().toJson("Failed to serialize data.");
		}
	}

	public static String getFacilityTrainings(Request request, Response response){
		response.type("application/json");
		try{
			String facility_id = request.params("id");
			ArrayList<Training> trainings = new TrainingService().getFacilityTrainings(facility_id);
			if(trainings == null) throw new Exception();
			return new GsonBuilder()
					.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
					.serializeNulls()
					.create()
					.toJson(trainings);
		}catch(Exception e){
			e.printStackTrace();
			response.status(400);
			return Utility.convertMessageToJSON("Couldn't fetch facility's trainings.");
		}
	}
	
	// PRIVATE HELPERS
	
	private static LocalDateTime parseLocalDateTime(String date, String time) {
		String[] dateParts = date.split("/");
		String[] timeParts = time.split(":");
		int day = Integer.parseInt(dateParts[0]);
		int month = Integer.parseInt(dateParts[1]);
		int year = Integer.parseInt(dateParts[2]);
		int hour = Integer.parseInt(timeParts[0]);
		int minute = Integer.parseInt(timeParts[1]);
		return LocalDateTime.of(year, month, day, hour, minute);
	}
	
	private static ArrayList<TrainingDTO> convertTrainingsToDTO(ArrayList<Training> trainings) {
		return (ArrayList<TrainingDTO>)trainings.stream().map(training -> training.getDTO()).collect(Collectors.toList());
	}
}
