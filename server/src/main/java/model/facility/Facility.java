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
    public String manager_id;

    public Facility(){}

    public Facility(String name, FacilityType facilityType, boolean available, Location location, String logoUrl, double grade, WorkingHours workingHours, String content) {
        this.name = name;
        this.facilityType = facilityType;
        this.available = available;
        this.location = location;
        this.logoUrl = logoUrl;
        this.grade = grade;
        this.workingHours = workingHours;
        this.content = content;
    }

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
