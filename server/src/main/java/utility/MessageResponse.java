package utility;

import com.google.gson.Gson;

import java.util.ArrayList;

public class MessageResponse {
    private ArrayList<String> messages;

    public MessageResponse(){
        messages = new ArrayList<>();
    }

    public MessageResponse addMessage(String message){
        messages.add(message);
        return this;
    }

    public boolean isEmpty(){
        return messages.isEmpty();
    }

    public String toJSON(){
        return new Gson().toJson(this);
    }
}
