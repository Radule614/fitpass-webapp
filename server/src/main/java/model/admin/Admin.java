package model.admin;

import dto.user.AdminDTO;
import dto.user.UserDTO;
import model.User;
import model.UserType;

public class Admin extends User {

    public Admin() {
        super(UserType.ADMIN);
    }

    @Override
    public UserDTO getDTO() {
        return new AdminDTO(this);
    }

    @Override
    public UserDTO getDTO(boolean __) {
        return new AdminDTO(this);
    }
}
