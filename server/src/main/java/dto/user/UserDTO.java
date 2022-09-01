package dto.user;

import java.time.LocalDate;

import model.User;
import model.UserType;
import model.admin.Admin;
import model.customer.Customer;
import model.facility.Facility;
import model.manager.Manager;
import model.trainer.Trainer;
import model.utility.Gender;
import repository.util.LocalDateAdapter;

public abstract class UserDTO {
    public UserType userType;
    public String username;
    public String firstname;
    public String lastname;
    public Gender gender;
    public LocalDate dateOfBirth;
    public String password;

    public UserDTO(User user){
        this.userType = user.userType;
        this.username = user.username;
        this.firstname = user.firstname;
        this.lastname = user.lastname;
        this.gender = user.gender;
        this.dateOfBirth = user.dateOfBirth;
        this.password = user.password;
    }
}
