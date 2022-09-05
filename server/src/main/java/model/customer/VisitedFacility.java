package model.customer;

import java.time.LocalDate;

public class VisitedFacility {
	private String facilityName;
	private LocalDate checkinDate;
	
	public VisitedFacility() {
		
	}
	
	public VisitedFacility(String facilityName, LocalDate checkinDate) {
		this.facilityName = facilityName;
		this.checkinDate = checkinDate;
	}
	
	public String getFacilityName() {
		return facilityName;
	}
	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}
	public LocalDate getCheckinDate() {
		return checkinDate;
	}
	public void setCheckinDate(LocalDate checkinDate) {
		this.checkinDate = checkinDate;
	}
	
	
}
