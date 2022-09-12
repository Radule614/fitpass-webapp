package model;

import java.time.LocalDate;

public class Workout {
	private LocalDate checkinTime;
	private String buyerUsername; 
	private String trainerUsername;
	private String trainingId;
	
	public Workout() {
		
	}
	
	public Workout(LocalDate checkinTime, String buyerUsername, String trainerUsername, String trainingId) {
		super();
		this.checkinTime = checkinTime;
		this.buyerUsername = buyerUsername;
		this.trainerUsername = trainerUsername;
		this.trainingId = trainingId;
	}
	
	public LocalDate getCheckinTime() {
		return checkinTime;
	}
	public void setCheckinTime(LocalDate checkinTime) {
		this.checkinTime = checkinTime;
	}
	public String getBuyerUsername() {
		return buyerUsername;
	}
	public void setBuyerUsername(String buyerUsername) {
		this.buyerUsername = buyerUsername;
	}
	public String getTrainerUsername() {
		return trainerUsername;
	}
	public void setTrainerUsername(String trainerUsername) {
		this.trainerUsername = trainerUsername;
	}
	public String getTrainingId() {
		return trainingId;
	}
	public void setTrainingId(String trainingId) {
		this.trainingId = trainingId;
	}
	
	
}
