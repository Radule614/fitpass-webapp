package webproj;

import static spark.Spark.*;

import controller.*;
import model.facility.Grade;
import service.MembershipService;

public class Main {
    public final static String uploadDirPath = "src/main/resources/public/img/";

	static int port = 9999;
    public static void main(String[] args) {
    	port(port);
    	//staticFiles.location("/public");
        String projectDir = System.getProperty("user.dir");
        String staticDir = "/src/main/resources/public";
        staticFiles.externalLocation(projectDir + staticDir);
        
        path("/api", () -> {
            before("/*", (req, res) -> {
                res.header("Access-Control-Allow-Methods",  "GET,PUT,POST,DELETE,OPTIONS,PATCH");
                res.header("Access-Control-Allow-Origin",   "*");
                res.header("Access-Control-Allow-Headers",  "*");
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

                post("/create", UserController::createUser);
                post("/delete", UserController::deleteUser);
                patch("/update", UserController::updateUser);
                patch("/updatePassword", UserController::changePassword);

                path("/get", () -> {
                    before("/*", AuthController::authenticate);

                    get("", UserController::getUser);
                    post("/filtered", UserController::getFilteredUsers);
                });
            });
            path("/facilities", () -> {
                get("/all", FacilityController::getAllFacilities);
                get("/search/:searchText", FacilityController::searchFacilities);
                get("/search/", FacilityController::searchFacilities);

                before("/add", AuthController::authenticate);
                before("/delete", AuthController::authenticate);
                before("/manager", AuthController::authenticate);

                post("/add", FacilityController::addFacility);
                post("/delete", FacilityController::deleteFacility);

                path("/manager", () -> {
                    before("/*", AuthController::authenticate);

                    post("/set", FacilityController::setManager);
                    post("/clear", FacilityController::clearManager);
                    get("", FacilityController::getManagerFacility);
                });
            });

            path("/comments", () -> {
                get("/all/:facility_id", CommentController::getAllComments);
                post("/approval", CommentController::commentApproval);
                before("/add", AuthController::authenticate);
                post("/add", CommentController::addComment);
            });
            
            path("/memberships", () -> {
            	before("/*", AuthController::authenticate);
        		get("/:username", MembershipController::getMembership);
        		post("/create", MembershipController::addMembership);
        		delete("/remove/:username/:membership_id", MembershipController::removeMembership);
        		patch("/deactivate/:username/:membership_id", MembershipController::deactivateMembership);
            });
            
            path("/grades", () -> {
            	before("/*", AuthController::authenticate);
            	post("/add", GradeController::addGrade);
            });

            path("/trainings", () -> {
            	get("/all", TrainingController::getTrainings);
            	before("/add", AuthController::authenticate);
            	post("/add", TrainingController::addTraining);

            path("/content", () -> {
                before("/*", AuthController::authenticate);

                post("/create", ContentController::createContent);
                post("/delete", ContentController::deleteContent);
                post("/trainer/add", ContentController::addTrainerToContent);
                post("/trainer/clear", ContentController::clearTrainerFromContent);
            });
        });
    }
}










