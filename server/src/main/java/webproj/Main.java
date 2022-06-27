package webproj;

import static spark.Spark.*;

import controller.*;

public class Main {
	static int port = 9999;
    public static void main(String[] args) {
    	port(port);
    	staticFiles.location("/public");

        path("/api", () -> {
            before("/*", (req, res) -> {
                res.header("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE,OPTIONS");
                res.header("Access-Control-Allow-Origin", "*");
                res.header("Access-Control-Allow-Headers","*");
            });
            options("/*", (req, res) -> {
                res.status(200);
                return "";
            });
            path("/auth", () -> {
                post("/login", AuthController::login);
                post("/register", AuthController::register);
            });
            path("/users", () -> {
                before("/*", AuthController::authenticate);
                get("/get", UserController::getUser);
            });
            path("/facilities", () -> {
                get("/all", FacilityController::getAllFacilities);
                get("/search/:searchText", FacilityController::searchFacilities);
                get("/search/", FacilityController::searchFacilities);

                before("/add", AuthController::authenticate);
                post("/add", FacilityController::addFacility);
            });
        });
    }
}










