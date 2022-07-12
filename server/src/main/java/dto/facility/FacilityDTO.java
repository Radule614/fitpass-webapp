package dto.facility;

import model.facility.FacilityType;
import model.facility.Location;
import model.facility.WorkingHours;

public class FacilityDTO {
    public String name;
    public FacilityType facilityType;
    public boolean available;
    public Location location;
    public WorkingHours workingHours;
    public String content;
    public FacilityDTO(){}

    public FacilityDTO(String name, FacilityType facilityType, boolean available, Location location, WorkingHours workingHours, String content) {
        this.name = name;
        this.facilityType = facilityType;
        this.available = available;
        this.location = location;
        this.workingHours = workingHours;
        this.content = content;
    }
}
