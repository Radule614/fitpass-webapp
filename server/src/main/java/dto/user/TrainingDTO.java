package dto.user;

import java.time.LocalDateTime;

import model.facility.Content;
import model.trainer.Training;
import model.trainer.TrainingType;
import service.ContentService;

public class TrainingDTO {
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
