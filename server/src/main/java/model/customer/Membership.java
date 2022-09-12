package model.customer;

import dto.membership.MembershipCustomerDTO;

import java.time.LocalDate;

public class Membership {
	public String id;
	public MembershipType type;
	public LocalDate paymentDate;
	public LocalDate expirationDate;
	public Double cost;
	public String customerUsername;
	public boolean active;
	public int appointmentNumber;
	public int usedAppointments; // da bi se izracunao broj bodova

	public enum MembershipType {
		MONTHLY,
		YEARLY
	}
	
	public MembershipCustomerDTO toDTO() {
		return new MembershipCustomerDTO(this);
	}
}
