package model.facility;

public class Comment {
    public String id;           //TODO: generate during comment creation (must be unique)
    public String facility_id;  //facility name
    public String user_id;      //username

    public String content;
    public boolean approved;

    public Comment() {}
}
