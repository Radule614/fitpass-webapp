package dto.user;

import model.User;
import model.UserType;
import model.utility.Gender;

public abstract class UserDTO {
    public UserType userType;
    public String username;
    public String firstname;
    public String lastname;
    public Gender gender;

    public UserDTO(User user){
        this.userType = user.userType;
        this.username = user.username;
        this.firstname = user.firstname;
        this.lastname = user.lastname;
        this.gender = user.gender;
    }
}
