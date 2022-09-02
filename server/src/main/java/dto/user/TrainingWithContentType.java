package dto.user;

import model.facility.ContentType;
import model.trainer.Training;
import service.ContentService;

public class TrainingWithContentType {
	public Training training;
	public ContentType contentType;
	
	public TrainingWithContentType(Training t) {
		this.training = t;
		this.contentType = new ContentService().get(t.getContentId()).type;
	}
}
