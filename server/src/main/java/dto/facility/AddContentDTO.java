package dto.facility;

import model.facility.ContentType;

public class AddContentDTO {
    public String facility_id;
    public String name;
    public ContentType type;

    public AddContentDTO() {}

    public AddContentDTO(String name, ContentType type) {
        this.name = name;
        this.type = type;
    }
}
