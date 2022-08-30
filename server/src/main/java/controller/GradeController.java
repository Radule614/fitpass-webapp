package controller;

import com.google.gson.Gson;

import model.facility.Grade;
import service.GradeService;
import spark.Request;
import spark.Response;

public class GradeController {
	
	public static String addGrade(Request req, Response res) {
		res.type("application/json");
		Grade grade;
		try {			
			grade = new Gson().fromJson(req.body(), Grade.class);
			new GradeService().addGrade(grade);
		} catch(Exception ex) {
			ex.printStackTrace();
			res.status(400);
			return new Gson().toJson("Failed to parse input data.");
		}
		return new Gson().toJson(grade);
	}
}
