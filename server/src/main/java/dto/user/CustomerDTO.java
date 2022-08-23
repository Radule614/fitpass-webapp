package dto.user;

import model.customer.Customer;
import model.customer.CustomerType;

public class CustomerDTO extends UserDTO {
    public int points;
    public CustomerType type;

    public CustomerDTO(Customer customer){
        super(customer);
        this.points = customer.points;
        this.type = customer.customerType;
    }


}
