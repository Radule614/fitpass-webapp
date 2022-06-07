package webproj;

import static spark.Spark.*;

public class Main {
	static int port = 9999;
    public static void main(String[] args) {
    	port(port);
    	staticFiles.location("/public");
    	
        get("/hello", (req, res) -> "Hello World");
        
        before("*", (req, res) -> {
        	res.redirect("/");
    	});
        
    }
}