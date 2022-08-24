package dto.membership;

import model.customer.Customer;
import model.customer.Membership;
import service.UserService;

public class MembershipCustomerDTO {
	public Membership membership;
	public Customer customer;
	
	public MembershipCustomerDTO(Membership membership) {
		this.membership = membership;
		this.customer = (Customer) new UserService().getUser(membership.customerUsername);
	}
}
