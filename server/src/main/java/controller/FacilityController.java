package controller;

import com.google.gson.Gson;

import service.FacilityService;
import spark.Request;
import spark.Response;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

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

	public static String addFacility(Request request, Response response) throws IOException, ServletException {
		File uploadDir = new File("src/main/resources/public/img/facilities");
		uploadDir.mkdir();
		request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));

		String extension = getExtension(request.raw().getPart("file"));

		if(extension.trim().equals(".")){
			return "no image";
		}
		Path tempFile = Files.createTempFile(uploadDir.toPath(), "", extension);
		try (InputStream input = request.raw().getPart("file").getInputStream()) { // getPart needs to use same "name" as input field in form
			Files.copy(input, tempFile, StandardCopyOption.REPLACE_EXISTING);
		}catch(Exception e){
			e.printStackTrace();
		}

		logInfo(request, tempFile);

		return "<h1>You uploaded this image:<h1><img src='" + tempFile.getFileName() + "'>";
	}

	private static void logInfo(Request req, Path tempFile) throws IOException, ServletException {
		System.out.println("Uploaded file '" + getFileName(req.raw().getPart("file")) + "' saved as '" + tempFile.toAbsolutePath() + "'");
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
				return "." + arr[arr.length-1].trim().replace("\"", "");
			}
		}
		return null;
	}


}
