package model.admin;

import model.User;
import model.UserType;

public class Admin extends User {

    public Admin() {
        super(UserType.ADMIN);
    }
    
}
