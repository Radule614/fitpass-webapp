package model.customer;

import java.util.Comparator;
import java.util.HashMap;

import dto.user.CustomerDTO;
import dto.user.UserDTO;
import model.facility.Facility;
import model.User;
import model.UserType;

public class Customer extends User {
	public Membership membership;
	public HashMap<Facility, Integer> visitedFacilities;
	public int points;
	public CustomerType customerType;

	public Customer() {
		super(UserType.CUSTOMER);
	}

	@Override
	public UserDTO getDTO() {
		return new CustomerDTO(this);
	}
}
