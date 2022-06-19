package model.customer;

import java.util.HashMap;

import model.facility.Facility;
import model.User;
import model.UserType;

public class Customer extends User {
	public Membership membership;
	public HashMap<Facility, Integer> VisitedFacilities;
	public int points;
	public CustomerType type;

	public Customer() {
		super(UserType.CUSTOMER);
	}
}
