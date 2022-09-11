package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.user.PersonalTrainingDTO;
import dto.user.TrainingDTO;
import dto.user.UserTrainingsFilterDTO;
import model.customer.VisitedFacility;
import model.trainer.Training;
import model.trainer.TrainingType;
import repository.util.LocalDateTimeAdapter;
import service.MembershipService;
import service.TrainingService;
import service.UserService;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.utils.IOUtils;
import utility.Utility;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

public class TrainingController {
	
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
					+ "public" + fs + "img" + fs + "trainings" + fs + parameterMap.get("trainerUsername")[0] + uploadedFile.getSubmittedFileName(); 
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
	
	public static String cancelTraining(Request req, Response res) {
		res.type("application/json");
		String trainingId = req.params("training_id");
		boolean success = new TrainingService().cancel(trainingId);
		try {
			if(success) {
				// Increment user termins by 1
				new UserService().incrementAppointmentNumber(trainingId);
				// Remove from customers history
				new UserService().removeTraining(trainingId);
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
	
	public static String addPersonalTraining(Request req, Response res) {
		res.type("application/json");
		String username = req.params("username");
		try {
			PersonalTrainingDTO personalTrainingDTO = new Gson().fromJson(req.body(), PersonalTrainingDTO.class);
			boolean canCheckIn = new MembershipService().canCheckIn(username);
			if(!canCheckIn) {
				res.status(250);
				return new Gson().toJson("You don't have active membership or you ran out of termins.");
			}
			Training newTraining = createTrainingFromDTO(personalTrainingDTO);
			new TrainingService().add(newTraining);
			// add to user training history
			new UserService().addTraining(username, newTraining.getId());
			new MembershipService().handleAppointmentUsage(username);
			new UserService().addVisitedFacility(username, new VisitedFacility(newTraining.getFacilityName(), LocalDate.now()));
			return new GsonBuilder()
					.serializeNulls()
					.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
					.create()
					.toJson(newTraining.getDTO());
		} catch(Exception ex) {
			res.status(400);
			ex.printStackTrace();
			return new Gson().toJson("Failed to parse input data");
		}
		
	}
	
	public static String searchCustomerTrainings(Request req, Response res) {
		res.type("application/json");
		QueryParamsMap queryMap = req.queryMap();
		String username = queryMap.get("username").value();
		String name = queryMap.get("name").value();
		ArrayList<Training> requiredTrainings = new TrainingService().searchCustomerTrainings(username, name);
		return new GsonBuilder()
				.serializeNulls()
				.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
				.create()
				.toJson(requiredTrainings);
	}
	
	public static String getUserFilteredTrainings(Request req, Response res) {
		res.type("applicaiton/json");
		UserTrainingsFilterDTO dto = new Gson().fromJson(req.body(), UserTrainingsFilterDTO.class);
		ArrayList<Training> userTrainings = new TrainingService().getUserTrainings(dto);
		return new GsonBuilder()
				.serializeNulls()
				.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
				.create()
				.toJson(convertTrainingsToDTO(userTrainings));
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
	
	private static Training createTrainingFromDTO(PersonalTrainingDTO personalTrainingDTO) {
		Training newTraining = new Training();
		newTraining.setName(personalTrainingDTO.name);
		newTraining.setType(TrainingType.valueOf(personalTrainingDTO.type.toUpperCase().replace("&", "and")));
		newTraining.setFacilityName(personalTrainingDTO.facilityName);
		newTraining.setTrainerUsername(personalTrainingDTO.trainerUsername);
		newTraining.setDescription(personalTrainingDTO.description);
		newTraining.setDuration(personalTrainingDTO.duration);
		newTraining.setContentId(personalTrainingDTO.contentId);
		newTraining.setStart(parseLocalDateTime(personalTrainingDTO.date, personalTrainingDTO.time));
		newTraining.setImgUrl("http://localhost:9999/img/trainings/personalTraining.jpg");
		
		return newTraining;
	}
	
	private static ArrayList<TrainingDTO> convertTrainingsToDTO(ArrayList<Training> trainings) {
		return (ArrayList<TrainingDTO>)trainings.stream().map(training -> training.getDTO()).collect(Collectors.toList());
	}
}
