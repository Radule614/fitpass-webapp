package model.customer;

import java.util.HashMap;

import model.Facility;
import model.User;

public class Customer extends User {
	public Membership membership;
	public HashMap<Facility, Integer> VisitedFacilities;
	public int points;
	public CustomerType type;
}
