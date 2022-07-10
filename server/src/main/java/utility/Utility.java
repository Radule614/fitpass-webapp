package utility;

import javax.servlet.http.Part;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class Utility {
    public static String convertMessageToJSON(String message){
        return "{\"message\": \"" + message + "\"}";
    }
    public static String parseStringInput(Part part){
        try(InputStream input = part.getInputStream()){
            return new BufferedReader(new InputStreamReader(input)).lines().collect(Collectors.joining("\n"));
        }catch (Exception e){
            //e.printStackTrace();
            return null;
        }
    }
}
