package model.customer;

import java.util.Date;

public class Membership {
	public String id;
	public MembershipType type;
	public Date paymentDate;
	public Date expirationDate;
	public Double cost;
	public Customer customer;
	public boolean Active;
	public int AppointmentNumber;
}
