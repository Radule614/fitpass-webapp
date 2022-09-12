package dto.user;

import model.facility.Content;
import model.trainer.Training;
import model.trainer.TrainingType;
import service.ContentService;

import java.time.LocalDateTime;

public class TrainingDTO {
	public String id;
	public String name;
	public TrainingType type;
	public String facilityName;
	public double duration;
	public String trainerUsername;
	public String description;
	public String imgUrl;
	public LocalDateTime start;
	public Content content;
	
	public TrainingDTO(Training t) {
		this.id = t.getId();
		this.name = t.getName();
		this.type = t.getType();
		this.facilityName = t.getFacilityName();
		this.duration = t.getDuration();
		this.trainerUsername = t.getTrainerUsername();
		this.description = t.getDescription();
		this.imgUrl = t.getImgUrl();
		this.start = t.getStart();
		this.content = new ContentService().get(t.getContentId());
	}
}
