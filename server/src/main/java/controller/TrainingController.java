package controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.Part;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.jsonwebtoken.io.IOException;
import model.trainer.Training;
import model.trainer.TrainingType;
import service.TrainingService;
import spark.Request;
import spark.Response;
import spark.utils.IOUtils;

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
	
	public static String getTrainings(Request req, Response res) {
		res.type("application/json");
		ArrayList<Training> trainings = new TrainingService().getAll();
		try {
			return new Gson().toJson(trainings);
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
		String individuality = parameterMap.get("individuality")[0].toUpperCase();
		String type = parameterMap.get("type")[0].toUpperCase().replace("&", "and");
		training.setType(TrainingType.valueOf(individuality + "_" + type));
		training.setImgUrl("http://localhost:9999/img/trainings/" + training.getTrainerUsername() + uploadedFile.getSubmittedFileName());
		
		new TrainingService().add(training);
		return new GsonBuilder().serializeNulls().create().toJson(training);
	}
}
