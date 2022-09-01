package dto.facility;

import dto.user.TrainerDTO;
import model.facility.Content;
import model.facility.ContentType;

public class ContentDTO {
    public String id;
    public String name;
    public ContentType type;
    public String facility_id;
    public TrainerDTO trainer;
    public ContentDTO(){}
    public ContentDTO(Content content){
        this.id = content.id;
        this.name = content.name;
        this.type = content.type;
        this.facility_id = content.facility_id;
    }
}
