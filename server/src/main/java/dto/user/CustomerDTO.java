package dto.user;

import model.customer.Customer;

public class CustomerDTO extends UserDTO {
    public int points;

    public CustomerDTO(Customer customer){
        super(customer);
        this.points = customer.points;
    }


}
