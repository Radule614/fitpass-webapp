package dto.user;

import model.UserType;
import model.manager.Manager;

public class ManagerDTO extends UserDTO {
    public ManagerDTO(Manager manager){
        super(manager);
    }
}
