package dto.user;

import model.UserType;

public class CreateUserDTO {
    public String username;
    public String password;
    public String passwordConfirm;
    public String firstname;
    public String lastname;
    public String dateOfBirth;
    public String gender;
    public UserType type;

    public CreateUserDTO(){}

    public CreateUserDTO(String username, String password, String passwordConfirm, String firstname, String lastname, String dateOfBirth, String gender, UserType type) {
        this.username = username;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.firstname = firstname;
        this.lastname = lastname;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.type = type;
    }
}
