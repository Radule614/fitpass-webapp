package dto.user;

import dto.facility.FacilityDTO;
import model.manager.Manager;

public class ManagerDTO extends UserDTO {
    public FacilityDTO facility;
    public ManagerDTO(Manager manager){
        super(manager);
    }
}
