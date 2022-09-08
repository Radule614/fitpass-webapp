package dto.user;

import java.util.ArrayList;
import java.util.stream.Collectors;

import model.customer.Customer;
import model.customer.CustomerType;
import model.customer.Membership;
import model.customer.VisitedFacility;
import model.facility.Facility;
import model.trainer.Training;
import service.MembershipService;
import service.TrainingService;

public class CustomerDTO extends UserDTO {
    public int points;
    public CustomerType type;
    public Membership membership;
    public ArrayList<String> visitedFacilities;
    public ArrayList<Training> trainingHistory;

    public CustomerDTO(Customer customer){
        super(customer);
        this.points = customer.points;
        this.type = customer.customerType;
        this.membership = new MembershipService().getByCustomer(customer.username);
        if(customer.visitedFacilities != null) {
	    	this.visitedFacilities = (ArrayList<String>) customer.visitedFacilities.stream()
	         	.map(visitedFacility -> visitedFacility.getFacilityName())
	         	.collect(Collectors.toList());
        } else {
        	this.visitedFacilities = null;
        }
        if(customer.trainingHistory != null) {
        	this.trainingHistory = (ArrayList<Training>) customer.trainingHistory.stream()
    			.map(trainingId -> new TrainingService().get(trainingId))
    			.collect(Collectors.toList());
        } else {
        	this.trainingHistory = null;
        }
        
    }

    public CustomerDTO(Customer customer, boolean __){
        super(customer);
        this.points = customer.points;
        this.type = customer.customerType;
    }

}
