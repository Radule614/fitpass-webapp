package model.facility;

public class Content {
    public String id;
    public String name;
    public ContentType type;
    public String trainer_id;
    public String facility_id;

    public Content() {}
    public Content(String name, ContentType type) {
        this.name = name;
        this.type = type;
    }

    public static class CreateContentException extends Exception{
        public CreateContentException(String message){
            super(message);
        }
    }

    public static class DeleteContentException extends Exception{
        public DeleteContentException(String message){
            super(message);
        }
    }

    public static class AddTrainerToContentException extends Exception{
        public AddTrainerToContentException(String message){
            super(message);
        }
    }

    public static class ClearTrainerFromContentException extends Exception{
        public ClearTrainerFromContentException(String message){
            super(message);
        }
    }
}
