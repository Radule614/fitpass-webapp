package dto.facility;

import dto.user.UserDTO;
import model.facility.*;

import java.util.ArrayList;

public class FacilityDTO {
    public String name;
    public FacilityType facilityType;
    public boolean available;
    public Location location;
    public String logoUrl;
    public double grade;
    public WorkingHours workingHours;
    public UserDTO manager;
    public ArrayList<Content> content = new ArrayList<>();

    public FacilityDTO(){}

    public FacilityDTO(Facility f){
        this.name = f.name;
        this.facilityType = f.facilityType;
        this.available = f.available;
        this.logoUrl = f.logoUrl;
        this.location = f.location;
        this.grade = f.grade;
        this.workingHours = f.workingHours;
        this.content = f.content;
    }
}
