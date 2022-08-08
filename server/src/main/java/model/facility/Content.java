package model.facility;

import java.util.ArrayList;

public class Content {
    public String name;
    public ContentType type;

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
}
