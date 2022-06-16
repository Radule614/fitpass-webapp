package service;

import model.facility.Facility;
import repository.FacilityRepository;

import java.util.ArrayList;

public class FacilityService {
    public static final FacilityRepository facilityRepository = FacilityRepository.getInstance();
    public FacilityService(){}

    public ArrayList<Facility> getAllFacilities(){
        return facilityRepository.getAll();
    }
}
