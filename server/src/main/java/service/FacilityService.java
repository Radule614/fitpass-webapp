package service;

import dto.facility.DeleteFacilityDTO;
import dto.facility.FacilityDTO;
import dto.FileDTO;
import model.facility.Facility;
import model.facility.FacilityType;
import repository.FacilityRepository;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import dto.AvgGradeRangeDTO;
import webproj.Main;

public class FacilityService {
    public static final FacilityRepository facilityRepository = FacilityRepository.getInstance();
    public FacilityService(){}

    public ArrayList<Facility> getAllFacilities(){
        return facilityRepository.getAll();
    }
    public ArrayList<Facility> getRequestedFacilites(String searchText, String facType, String avgGradeRange) {
    	List<Facility> requestedFacilities = null;
    	if(facType.equalsIgnoreCase("all")) {
    		// Samo po tekstu
    		if(avgGradeRange.equalsIgnoreCase("all")) {
        		requestedFacilities = facilityRepository.getAll().stream()
    					.filter(facility -> facility.name.toLowerCase().contains(searchText.toLowerCase().trim()))
    					.collect(Collectors.toList());
    		} else {
    			// Po tekstu i po oceni
    			AvgGradeRangeDTO avgGradeRangeDTO = AvgGradeRangeDTO.valueOf(avgGradeRange);
    			requestedFacilities = facilityRepository.getAll().stream()
    				.filter(facility -> (facility.name.toLowerCase().contains(searchText.toLowerCase().trim()) 
    					&& (facility.grade >= avgGradeRangeDTO.lowerBound && facility.grade <= avgGradeRangeDTO.upperBound)))
    				.collect(Collectors.toList());
    		}
    	} else {
    		if(avgGradeRange.equalsIgnoreCase("all")) {
    			// Po tekstu i po tipu
    			FacilityType facilityType = getFacilityTypeFromText(facType);
    			requestedFacilities = facilityRepository.getAll().stream()
    					.filter(facility -> facility.name.toLowerCase().contains(searchText.toLowerCase().trim()) &&
    							(facility.facilityType == facilityType))
    					.collect(Collectors.toList());
    		} else {
    			// Po svemu
    			FacilityType facilityType = getFacilityTypeFromText(facType);
    			AvgGradeRangeDTO avgGradeRangeDTO = AvgGradeRangeDTO.valueOf(avgGradeRange);
    			requestedFacilities = facilityRepository.getAll().stream()
    					.filter(facility -> facility.name.toLowerCase().contains(searchText.toLowerCase().trim()) &&
    						(facility.grade >= avgGradeRangeDTO.lowerBound && facility.grade <= avgGradeRangeDTO.upperBound) &&
    						(facility.facilityType == facilityType))
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

	public Facility addFacility(FacilityDTO facilityDTO, FileDTO fileDTO){
		if(!checkIfImageValid(fileDTO.extension)) return null;
		String image = saveFile(fileDTO);
		String logoUrl = "img/facilities/" + image;
		Facility f = new Facility(facilityDTO.name, facilityDTO.facilityType, facilityDTO.available, facilityDTO.location, logoUrl, 0, facilityDTO.workingHours, facilityDTO.content);
		facilityRepository.add(f);
		return f;
	}

	public boolean deleteFacility(DeleteFacilityDTO dto){
		Facility f = getByName(dto.name);
		if(f != null && facilityRepository.delete(f)){
			facilityRepository.saveAll();
			return true;
		}
		return false;
	}

	public boolean checkIfImageValid(String extension){
		return extension.equals("jpg") || extension.equals("jpeg") || extension.equals("png");
	}

	public Facility getByName(String facilityName){
		for (Facility facility: facilityRepository.getAll()) {
			if(facilityName.equals(facility.name)){
				return facility;
			}
		}
		return null;
	}

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

	private void logInfo(String filename, Path tempFile) {
		System.out.println("Uploaded file '" + filename + "' saved as '" + tempFile.toAbsolutePath() + "'");
	}
   
}
