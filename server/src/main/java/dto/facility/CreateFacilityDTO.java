package dto.facility;

import dto.user.UserDTO;
import model.facility.FacilityType;
import model.facility.Location;
import model.facility.WorkingHours;

public class CreateFacilityDTO {
    public String name;
    public FacilityType facilityType;
    public boolean available;
    public Location location;
    public double grade;
    public WorkingHours workingHours;
    public String content;
    public UserDTO manager;
    public CreateFacilityDTO(){}

    public CreateFacilityDTO(String name, FacilityType facilityType, boolean available, Location location, WorkingHours workingHours, String content) {
        this.name = name;
        this.facilityType = facilityType;
        this.available = available;
        this.location = location;
        this.workingHours = workingHours;
        this.content = content;
    }
}
