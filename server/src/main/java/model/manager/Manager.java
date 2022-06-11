package model.manager;

import model.Facility;
import model.User;
import model.UserType;

public class Manager extends User {
    public Facility facility;

    public Manager() {
        super(UserType.MANAGER);
    }
}
