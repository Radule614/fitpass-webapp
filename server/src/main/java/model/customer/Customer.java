package model.customer;

import dto.user.CustomerDTO;
import dto.user.UserDTO;
import model.User;
import model.UserType;
import model.customer.CustomerType.CustomerTypeEnum;
import service.MembershipService;

import java.util.ArrayList;

public class Customer extends User {
	public String membershipId;
	public ArrayList<VisitedFacility> visitedFacilities;
	public int points;
	public CustomerType customerType;
	public ArrayList<String> trainingHistory; // array lista training id-eva

	public Customer() {
		super(UserType.CUSTOMER);
	}
	
	public void updatePoints() {
		Membership membership = new MembershipService().getById(membershipId);
		points += (int) Math.round(membership.cost / 1000 * membership.usedAppointments);
		if(membership.usedAppointments < (membership.appointmentNumber / 3)) {
			points -= membership.cost / (1000 * 133 * 4);
			if(points < 0) {
				points = 0;
			}
		}
		updateType();
	}
	
	private void updateType() {
		if			(points > 4500) customerType = new CustomerType(CustomerTypeEnum.GOLD, 5, 4500);
		else if		(points > 2500) customerType = new CustomerType(CustomerTypeEnum.SILVER, 2.5, 2500);
		else if		(points > 1500) customerType = new CustomerType(CustomerTypeEnum.BRONZE, 1.5, 1500);
	}

	@Override
	public UserDTO getDTO() {
		return new CustomerDTO(this);
	}

	@Override
	public UserDTO getDTO(boolean __){
		return new CustomerDTO(this, true);
	}
	
}
