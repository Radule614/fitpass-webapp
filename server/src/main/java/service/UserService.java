package service;

import java.util.ArrayList;

import dto.RegisterDTO;
import model.User;
import model.customer.Customer;
import repository.UserRepository;

public class UserService {
    private static final UserRepository userRepository = UserRepository.getInstance();
    public UserService(){}

    public User getUser(String username) {
        for(User u: userRepository.getAll()) {
            if(u.username.equals(username)) {
                return u;
            }
        }
        return null;
    }
    
    public ArrayList<User> getAll() {
    	return userRepository.getAll();
    }
    
    public Customer createNewCustomer(RegisterDTO registerDTO) {
    	Customer newCustomer = null;
    	// Ako username nije unique vratimo null
    	if(!isUsernameUnique(registerDTO.username)) {
    		return newCustomer;
    	}
    	
    	newCustomer = new Customer();
    	newCustomer.username = registerDTO.username;
    	newCustomer.password = registerDTO.password;
    	newCustomer.firstname = registerDTO.firstName;
    	newCustomer.lastname = registerDTO.lastName;
    	newCustomer.dateOfBirth = registerDTO.getParsedDateOfBirth();
    	newCustomer.gender = registerDTO.getResolveGender();
    	newCustomer.points = 0;
    	
    	userRepository.addNewUser(newCustomer);
    	
    	return newCustomer;
    }
    
    private boolean isUsernameUnique(String username) {
		boolean unique = true;
		ArrayList<User> users = new UserService().getAll();
    	for(User u : new UserService().getAll()) {
    		if(u.username.equalsIgnoreCase(username)) {
    			unique = false;
    			break;
    		}
    	}
    	
    	return unique;
    }

}
