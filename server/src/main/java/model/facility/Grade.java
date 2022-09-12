package model.facility;

public class Grade {
	private String username;
	private String facilityName;
	private double grade;
	
	public Grade() {
		
	}
	
	public Grade(String username, String facilityName, double grade) {
		this.username = username;
		this.facilityName = facilityName;
		this.grade = grade;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFacilityName() {
		return facilityName;
	}
	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}
	public double getGrade() {
		return grade;
	}
	public void setGrade(double grade) {
		this.grade = grade;
	}
	
}
