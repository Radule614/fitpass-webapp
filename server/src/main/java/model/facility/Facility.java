package model.facility;

public class Facility {
    public String name;
    public FacilityType facilityType;
    public boolean available;
    public Location location;
    public String logoUrl;
    public double grade;
    public WorkingHours workingHours;
    public String content;

    public Facility(){}
    public Facility(Facility f){
        name = f.name;
        facilityType = f.facilityType;
        available = f.available;
        location = f.location;
        logoUrl = f.logoUrl;
        grade = f.grade;
        workingHours = f.workingHours;
        content = f.content;
    }
}
