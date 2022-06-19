package service;

import model.facility.Facility;
import model.facility.FacilityType;
import repository.FacilityRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import dto.AvgGradeRangeDTO;

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

   
}
