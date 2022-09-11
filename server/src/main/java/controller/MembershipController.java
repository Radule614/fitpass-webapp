package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import dto.membership.MembershipCustomerDTO;
import model.customer.Membership;
import repository.util.LocalDateAdapter;
import service.MembershipService;
import service.UserService;
import spark.Request;
import spark.Response;
import utility.UIDGenerator;

import java.time.LocalDate;

public class MembershipController {
	public static String getMembership(Request req, Response res) {
		res.type("application/json");
		String username = req.params("username");
		Membership membership = new MembershipService().getByCustomer(username);
		if(membership == null) {
			res.status(400);
			return new Gson().toJson("Customer doesn't have membership");
		}
		MembershipCustomerDTO membershipCustomerDTO = membership.toDTO();
		if(membershipCustomerDTO.customer == null) {
			res.status(400);
			return new Gson().toJson("Customer doesn't exist");
		}
		return new GsonBuilder()
				.serializeNulls()
				.registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
				.create()
				.toJson(membership);
	}
	
	public static String addMembership(Request req, Response res) {
		res.type("application/json");
		Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
		try {
			Membership membership = gson.fromJson(req.body(), Membership.class);
			membership.id = UIDGenerator.generate();
			// dodaj clanarinu
			new MembershipService().save(membership);
			// updatuj useru clanarinu
			new UserService().addMembership(membership.customerUsername, membership.id);
			return new GsonBuilder()
					.serializeNulls()
					.registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
					.create()
					.toJson(membership);
		} catch(JsonSyntaxException ex) {
			res.status(400);
			return new Gson().toJson("Error parsing input data.");
		} catch(Exception ex) {
			res.status(400);
			return new Gson().toJson("Failed to add membership.");
		}

	}
	
	public static String removeMembership(Request req, Response res) {
		res.type("application/json");
		boolean success = new MembershipService().remove(req.params("membership_id"));
		// izbrisi i customeru
		new UserService().removeMembership(req.params("username"));
		String message;
		if(success) {
			message = "Membership removed";
		} else {
			message = "Failed to remove membership";
			res.status(400);
		}
		return new Gson().toJson(message);
	}
	
	public static String deactivateMembership(Request req, Response res) {
		res.type("application/json");
		boolean success = new MembershipService().deactivate(req.params("membership_id"));
		int points = new UserService().calculateCustomerPoints(req.params("username"));
		String message;
		if(success) {
			message = "Membership deactivated, you have " + points + " points";
		} else {
			message = "Failed to deactivate membership";
			res.status(400);
		}
		
		return new Gson().toJson(message);
	}
	
	
}
