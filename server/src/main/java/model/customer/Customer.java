package model.customer;

import java.util.ArrayList;
import dto.user.CustomerDTO;
import dto.user.UserDTO;
import model.User;
import model.UserType;
import service.MembershipService;
import service.UserService;

public class Customer extends User {
	public String membershipId;
	public ArrayList<String> visitedFacilitiesName;
	public int points;
	public CustomerType customerType;

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
	}

	@Override
	public UserDTO getDTO() {
		return new CustomerDTO(this);
	}
	
}
