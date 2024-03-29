package service;

import dto.AvgGradeRangeDTO;
import dto.FileDTO;
import dto.facility.ClearManagerDTO;
import dto.facility.CreateFacilityDTO;
import dto.facility.DeleteFacilityDTO;
import dto.facility.SetManagerDTO;
import model.User;
import model.customer.Customer;
import model.customer.VisitedFacility;
import model.facility.Content;
import model.facility.Facility;
import model.facility.FacilityType;
import model.manager.Manager;
import repository.FacilityRepository;
import webproj.Main;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FacilityService {
    public static final FacilityRepository facilityRepository = FacilityRepository.getInstance();
    public FacilityService(){}

    public ArrayList<Facility> getAllFacilities(){
        return facilityRepository.getAll();
    }
	public Facility getByName(String facilityName){
		return facilityRepository.getByName(facilityName);
	}
    public ArrayList<Facility> getRequestedFacilites(String searchText, String facType, String avgGradeRange) {
    	List<Facility> requestedFacilities = null;
    	if(facType.equalsIgnoreCase("all")) {
    		// Samo po tekstu
    		if(avgGradeRange.equalsIgnoreCase("all")) {
        		requestedFacilities = facilityRepository.getAll().stream()
    					.filter(facility -> {
    						String name = facility.name != null ? facility.name : "";
    						String city = facility.location != null && facility.location.getCity() != null ? 
    								facility.location.getCity().toLowerCase() : 
    								"";
    						String country = facility.location != null && facility.location.getCountry() != null ?
    								facility.location.getCountry().toLowerCase() :
    								"";
    						String processedSearch = searchText.toLowerCase().trim();
    						boolean nameContains = name.contains(processedSearch);
    						boolean cityContains = city.contains(processedSearch);
    						boolean countryContains = country.contains(processedSearch);
    						return nameContains || cityContains || countryContains;
    					})
    					.collect(Collectors.toList());
    		} else {
    			// Po tekstu i po oceni
    			AvgGradeRangeDTO avgGradeRangeDTO = AvgGradeRangeDTO.valueOf(avgGradeRange);
    			requestedFacilities = facilityRepository.getAll().stream()
    				.filter(facility -> {
    					String name = facility.name != null ? facility.name : "";
						String city = facility.location != null && facility.location.getCity() != null ? 
								facility.location.getCity().toLowerCase() : 
								"";
						String country = facility.location != null && facility.location.getCountry() != null ?
								facility.location.getCountry().toLowerCase() :
								"";
						String processedSearch = searchText.toLowerCase().trim();
						boolean nameContains = name.contains(processedSearch);
						boolean cityContains = city.contains(processedSearch);
						boolean countryContains = country.contains(processedSearch);
						return (nameContains || cityContains || countryContains) && 
		    					(facility.grade >= avgGradeRangeDTO.lowerBound && facility.grade <= avgGradeRangeDTO.upperBound);
    				})
    				.collect(Collectors.toList());
    		}
    	} else {
    		if(avgGradeRange.equalsIgnoreCase("all")) {
    			// Po tekstu i po tipu
    			FacilityType facilityType = getFacilityTypeFromText(facType);
    			requestedFacilities = facilityRepository.getAll().stream()
    					.filter(facility -> {
    						String name = facility.name != null ? facility.name : "";
    						String city = facility.location != null && facility.location.getCity() != null ? 
    								facility.location.getCity().toLowerCase() : 
    								"";
    						String country = facility.location != null && facility.location.getCountry() != null ?
    								facility.location.getCountry().toLowerCase() :
    								"";
    						String processedSearch = searchText.toLowerCase().trim();
    						boolean nameContains = name.contains(processedSearch);
    						boolean cityContains = city.contains(processedSearch);
    						boolean countryContains = country.contains(processedSearch);
    						return (nameContains || cityContains || countryContains) &&
    								facility.facilityType == facilityType;
    					})
    					.collect(Collectors.toList());
    		} else {
    			// Po svemu
    			FacilityType facilityType = getFacilityTypeFromText(facType);
    			AvgGradeRangeDTO avgGradeRangeDTO = AvgGradeRangeDTO.valueOf(avgGradeRange);
    			requestedFacilities = facilityRepository.getAll().stream()
    					.filter(facility -> {
    						String name = facility.name != null ? facility.name : "";
    						String city = facility.location != null && facility.location.getCity() != null ? 
    								facility.location.getCity().toLowerCase() : 
    								"";
    						String country = facility.location != null && facility.location.getCountry() != null ?
    								facility.location.getCountry().toLowerCase() :
    								"";
    						String processedSearch = searchText.toLowerCase().trim();
    						boolean nameContains = name.contains(processedSearch);
    						boolean cityContains = city.contains(processedSearch);
    						boolean countryContains = country.contains(processedSearch);
    						return (nameContains || cityContains || countryContains) &&
    								facility.facilityType == facilityType &&
    			    				(facility.grade >= avgGradeRangeDTO.lowerBound && facility.grade <= avgGradeRangeDTO.upperBound);
    					})
    					.collect(Collectors.toList());
    		}
    	}
    	
    	return (ArrayList<Facility>) requestedFacilities;
    }
    public FacilityType getFacilityTypeFromText(String text) {
    	FacilityType facilityType = null;
    	text = text.toUpperCase().trim();
    	switch(text) {
	    	case "GYM": {
    			facilityType = FacilityType.GYM;
	    		break;
	    	}
	    	case "POOL": {
	    		facilityType = FacilityType.POOL;
	    		break;
	    	}
	    	case "SPORTS CENTER": {
	    		facilityType = FacilityType.SPORTS_CENTER;
	    		break;
	    	}
	    	case "DANCE STUDIO": {
	    		facilityType = FacilityType.DANCE_STUDIO;
	    		break;
	    	}
	    	case "OTHER": {
	    		facilityType = FacilityType.OTHER;
	    		break;
	    	}
    	}
    	
    	return facilityType;
    }

	public Facility addFacility(CreateFacilityDTO facilityDTO, FileDTO fileDTO){
		if(!checkIfImageValid(fileDTO.extension)) return null;
		String image = saveFile(fileDTO);
		String logoUrl = "img/facilities/" + image;
		Facility f = new Facility(facilityDTO.name, facilityDTO.facilityType, facilityDTO.available, facilityDTO.location, logoUrl, 0, facilityDTO.workingHours);
		facilityRepository.add(f);
		return f;
	}

	public boolean deleteFacility(DeleteFacilityDTO dto){
		Facility f = facilityRepository.getByName(dto.name);
		if(f != null && facilityRepository.delete(f)){
			deleteFile(f.logoUrl);
			new TrainingService().removeByFacility(f.name);
			new ContentService().removeByFacility(f.name);
			new CommentService().removeByFacility(f.name);
			facilityRepository.saveAll();
			new UserService().clearFacilityReferences(f.name);
			return true;
		}
		return false;
	}

	public Manager setManager(SetManagerDTO dto){
		Facility f = getByName(dto.facilityName);
		UserService userService = new UserService();
		if(f != null && userService.setFacility(dto)){
			clearManagerReferences(dto.managerUsername);
			f.manager_id = dto.managerUsername;
			facilityRepository.saveAll();
			return (Manager)userService.getUser(dto.managerUsername);
		}
		return null;
	}

	public void clearManager(ClearManagerDTO dto){
		if (dto == null) return;
		Facility f = getByName(dto.facilityName);

		if(f != null) clearManagerReferences(f.manager_id);
		new UserService().clearFacilityReferences(dto.facilityName);
	}

	public void clearManagerReferences(String managerUsername){
		if(managerUsername == null) return;
		for(Facility f: facilityRepository.getAll()) {
			if(managerUsername.equals(f.manager_id)) {
				f.manager_id = null;
				facilityRepository.saveAll();
			}
		}
	}

	public Facility getByManager(String managerUsername){
		if(managerUsername == null) return null;
		for(Facility f: facilityRepository.getAll()) {
			if(managerUsername.equals(f.manager_id)) {
				return f;
			}
		}
		return null;
	}

	public boolean checkIfImageValid(String extension){
		return extension.equals("jpg") || extension.equals("jpeg") || extension.equals("png");
	}

	public ArrayList<User> getFacilityTrainers(String facility_id){
		ArrayList<User> temp = new ArrayList<>();
		UserService userService = new UserService();
		for(Content c: new ContentService().getAll()){
			if(c.facility_id.equals(facility_id) && c.trainer_id != null){
				User user = userService.getUser(c.trainer_id);
				if(user != null){
					temp.add(user);
				}
			}
		}
		return temp;
	}

	public ArrayList<User> getFacilityVisitors(String facility_id){
		ArrayList<User> temp = new ArrayList<>();
		for(User u: new UserService().getAll()){
			if(u instanceof Customer){
				Customer c = (Customer) u;
				if(c.visitedFacilities != null){
					for(VisitedFacility vf: c.visitedFacilities){
						String f = vf.getFacilityName();
						if(f != null && f.equals(facility_id)){
							temp.add(c);
						}
					}
				}
			}
		}
		return temp;
	}

	public void setAvailability(String facility_id, boolean val){
		if(facility_id == null) return;
		for(Facility f: facilityRepository.getAll()){
			if(f.name.equals(facility_id)){
				f.available = val;
				facilityRepository.saveAll();
				return;
			}
		}
	}


	//PRIVATE

	private String saveFile(FileDTO fileDTO) {
		File uploadDir = new File(Main.uploadDirPath + "facilities");
		uploadDir.mkdir();
		String extension = fileDTO.extension;
		if(extension.trim().isEmpty()) return null;

		Path tempFile = null;
		try{
			tempFile = Files.createTempFile(uploadDir.toPath(), "", "." + extension);
			Files.copy(fileDTO.inputStream, tempFile, StandardCopyOption.REPLACE_EXISTING);
			logInfo(fileDTO.filename, tempFile);
		}catch (Exception e){
			e.printStackTrace();
		}
		return tempFile != null ? String.valueOf(tempFile.getFileName()) : null;
	}
	private void deleteFile(String uri){
		String[] parts = uri.split("/");
		String filename = parts[parts.length-1];

		File uploadDir = new File(Main.uploadDirPath + "facilities");
		File[] matchingFiles = uploadDir.listFiles((dir, name) -> name.equals(filename));
		if(matchingFiles == null) return;
		for (File file: matchingFiles) file.delete();
	}
	private void logInfo(String filename, Path tempFile) {
		System.out.println("Uploaded file '" + filename + "' saved as '" + tempFile.toAbsolutePath() + "'");
	}
   
}
