package model.trainer;

import java.time.LocalDateTime;

import dto.user.TrainingDTO;
import utility.UIDGenerator;

public class Training {
	private String id;
	private String name;
	private TrainingType type;
	private String facilityName;
	private double duration;
	private String trainerUsername;
	private String description;
	private String imgUrl;
	private LocalDateTime start;
	private String contentId;
	
	public Training() {
		this.id = UIDGenerator.generate();
	}
	
	public Training(String name, TrainingType type, String facilityName, double duration,
			String trainerUsername, String description, String imgUrl, LocalDateTime start, String contentId) {
		super();
		this.id = UIDGenerator.generate();
		this.name = name;
		this.type = type;
		this.facilityName = facilityName;
		this.duration = duration;
		this.trainerUsername = trainerUsername;
		this.description = description;
		this.imgUrl = imgUrl;
		this.start = start;
		this.contentId = contentId;
	}
	
	public TrainingDTO getDTO() {
		return new TrainingDTO(this);
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public TrainingType getType() {
		return type;
	}
	public void setType(TrainingType type) {
		this.type = type;
	}
	public String getFacilityName() {
		return facilityName;
	}
	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}
	public double getDuration() {
		return duration;
	}
	public void setDuration(double duration) {
		this.duration = duration;
	}
	public String getTrainerUsername() {
		return trainerUsername;
	}
	public void setTrainerUsername(String trainerUsername) {
		this.trainerUsername = trainerUsername;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public LocalDateTime getStart() {
		return start;
	}
	public void setStart(LocalDateTime ldt) {
		this.start = ldt;
	}
	public String getContentId() {
		return contentId;
	}
	public void setContentId(String id) {
		this.contentId = id;
	}
}
