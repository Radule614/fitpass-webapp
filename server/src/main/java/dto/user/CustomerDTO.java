package dto.user;

import java.util.ArrayList;
import model.customer.Customer;
import model.customer.CustomerType;
import model.customer.Membership;
import model.facility.Facility;
import service.MembershipService;

public class CustomerDTO extends UserDTO {
    public int points;
    public CustomerType type;
    public Membership membership;
    public ArrayList<Facility> visitedFacilities;

    public CustomerDTO(Customer customer){
        super(customer);
        this.points = customer.points;
        this.type = customer.customerType;
        this.membership = new MembershipService().getByCustomer(customer.username);
    }


}
