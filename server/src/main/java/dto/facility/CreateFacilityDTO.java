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
    public WorkingHours workingHours;
    public UserDTO manager;
    public CreateFacilityDTO(){}

    public CreateFacilityDTO(String name, FacilityType facilityType, boolean available, Location location, WorkingHours workingHours) {
        this.name = name;
        this.facilityType = facilityType;
        this.available = available;
        this.location = location;
        this.workingHours = workingHours;
    }
}
