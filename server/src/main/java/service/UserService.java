package service;

import dto.auth.RegisterDTO;
import dto.facility.SetManagerDTO;
import dto.user.*;
import model.User;
import model.UserType;
import model.customer.Customer;
import model.customer.CustomerType;
import model.customer.Membership;
import model.customer.VisitedFacility;
import model.facility.Content;
import model.facility.ContentType;
import model.manager.Manager;
import model.trainer.Training;
import model.utility.Gender;
import repository.UserRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class UserService {
    private static final UserRepository userRepository = UserRepository.getInstance();
    public UserService(){}

    public User getUser(String username) {
        return userRepository.getByUsername(username);
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

//		CustomerType customerType = new CustomerType();
//		customerType.type = CustomerType.CustomerTypeEnum.BRONZE;
//		newCustomer.customerType = customerType;
    	
    	userRepository.addNewUser(newCustomer);
    	
    	return newCustomer;
    }

	public User createUser(CreateUserDTO dto) throws Exception {
		if(!isUsernameUnique(dto.username)) throw new User.CreateUserException("Username is not unique");
		User user = User.createInstance(dto.type);
		if(user == null) throw new User.CreateUserException("User type doesn't exist");

		user.username = dto.username;
		user.password = dto.password;
		user.firstname = dto.firstname;
		user.lastname = dto.lastname;
		user.gender = Gender.valueOf(dto.gender.toUpperCase());
		user.dateOfBirth = dto.dateOfBirth.isEmpty() ? null : LocalDate.parse(dto.dateOfBirth);

		userRepository.addNewUser(user);

		return user;
	}

	public boolean deleteUser(DeleteUserDTO dto){
		User user = userRepository.getByUsername(dto.username);
		if(user != null && userRepository.delete(user)){
			userRepository.saveAll();
			new FacilityService().clearManagerReferences(user.username);
			new ContentService().clearTrainer(user.username);
			new CommentService().removeByUser(user.username);
			return true;
		}
		return false;
	}
	
	public User updateUser(UpdateUserDTO toUpdate) {
		User updatedUser = userRepository.update(toUpdate);
		if(updatedUser != null) {
			userRepository.saveAll();
		}
		
		return updatedUser;
	}

	public boolean setFacility(SetManagerDTO dto){
		User user = getUser(dto.managerUsername);
		if(user instanceof Manager){
			clearFacilityReferences(dto.facilityName);
			((Manager)user).facility_id = dto.facilityName;
			userRepository.saveAll();
			return true;
		}
		return false;
	}

	public void clearFacilityReferences(String facilityName){
		if (facilityName == null) return;
		for (User u: getAll()) {
			if(u instanceof Manager && facilityName.equals(((Manager) u).facility_id)) {
				((Manager) u).facility_id = null;
				userRepository.saveAll();
			}
		}
	}

	public ArrayList<User> getFilteredUsers(UsersFilterDTO dto){
		ArrayList<User> users = getAll();
		if(dto == null) return users;

		if(dto.userFilter != null && dto.userFilter.type != null){
			ArrayList<User> temp = new ArrayList<>();
			for(String type: dto.userFilter.type.split(",")){
				temp.addAll(users.stream().filter(user -> user.userType.equals(UserType.valueOf(type.toUpperCase()))).collect(Collectors.toList()));
			}
			users = temp;
		}

		if(dto.customerFilter != null && dto.customerFilter.type != null){
			ArrayList<User> temp = new ArrayList<>();
			for(String type: dto.customerFilter.type.split(",")){
				temp.addAll(users.stream().filter(user ->
						user instanceof Customer &&
						((Customer) user).customerType != null &&
						((Customer) user).customerType.type.equals(CustomerType.CustomerTypeEnum.valueOf(type.toUpperCase()))).collect(Collectors.toList()));
			}
			users = temp;
		}

		if(dto.search != null) {
			if (dto.search.username != null && !dto.search.username.trim().isEmpty())
				users = new ArrayList<>(users.stream().filter(user ->
						user.username.toLowerCase().contains(dto.search.username.trim().toLowerCase())).collect(Collectors.toList()));
			if (dto.search.firstname != null && !dto.search.firstname.trim().isEmpty())
				users = new ArrayList<>(users.stream().filter(user ->
						user.firstname.toLowerCase().contains(dto.search.firstname.trim().toLowerCase())).collect(Collectors.toList()));
			if (dto.search.lastname != null && !dto.search.lastname.trim().isEmpty())
				users = new ArrayList<>(users.stream().filter(user ->
						user.lastname.toLowerCase().contains(dto.search.lastname.trim().toLowerCase())).collect(Collectors.toList()));
		}

		if(dto.sort != null && dto.sort.type != null){
			if (dto.sort.reverse)   users.sort(User.COMPARATORS.get(dto.sort.type).reversed());
			else                    users.sort(User.COMPARATORS.get(dto.sort.type));
		}

		return users;
	}
    
    public boolean isUsernameUnique(String username) {
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
    
    public void addMembership(String username, String membershipId) {
    	((Customer) getUser(username)).membershipId = membershipId;
    	userRepository.saveAll();
    }
    
    public void removeMembership(String username) {
    	((Customer) getUser(username)).membershipId = null;
    	userRepository.saveAll();
    }
    
    public int calculateCustomerPoints(String username) {
    	Customer customer = (Customer) getUser(username);
    	customer.updatePoints();
    	userRepository.saveAll();
    	return customer.points;
    }
    
    public boolean changePassword(ChangePasswordDTO changePassDTO) {
    	User user = getUser(changePassDTO.username);
    	if(user == null) return false;
    	
    	if(!user.password.equals(changePassDTO.oldPassword)) return false;
    	
    	user.password = changePassDTO.newPassword;
    	userRepository.saveAll();
    	return true;
    }
    
    public boolean addTraining(String username, String trainingId) {
    	Customer customer = (Customer) getUser(username);
    	if(customer.trainingHistory == null) customer.trainingHistory = new ArrayList<String>();
    	// Does same group training already exist
    	for(String tId : customer.trainingHistory) {
    		if(tId.equals(trainingId)) {
    			Training t = new TrainingService().get(tId);
    			Content trainingContent = new ContentService().get(t.getContentId());
    			if(trainingContent.type == ContentType.GROUP) {
    				return false;
    			}
    		}
    	}
    	
    	customer.trainingHistory.add(trainingId);
    	userRepository.saveAll();
    	return true;
    }
    
    public void addVisitedFacility(String username, VisitedFacility facility) {
    	Customer customer = (Customer) getUser(username);
    	if(customer.visitedFacilities == null) customer.visitedFacilities = new ArrayList<VisitedFacility>();
    	// Has he already visited this facility
    	for(VisitedFacility vf : customer.visitedFacilities) {
    		if(vf.getFacilityName().equals(facility.getFacilityName())) return;
    	}
    	customer.visitedFacilities.add(facility);
    	userRepository.saveAll();
    }
    
    // Removes training from users history
    public void removeTraining(String trainingId) {
    	userRepository.getAll().forEach(user -> {
    		if(user.userType == UserType.CUSTOMER) {
        		Customer temp = (Customer) user;
        		if(temp.trainingHistory != null ) {        			
        			temp.trainingHistory.remove(trainingId);
        		}
    		}
    	});
    	userRepository.saveAll();
    }
    
    public void incrementAppointmentNumber(String training_id) {
    	// find customer which scheduled this training
    	ArrayList<Customer> customers = (ArrayList<Customer>) userRepository.getAll()
    			.stream()
    			.filter(user -> user.userType == UserType.CUSTOMER)
    			.map(user -> (Customer)user)
    			.collect(Collectors.toList());
    	if(customers != null) {
    		customers.forEach(customer -> {
    			if(customer.trainingHistory != null && customer.trainingHistory.contains(training_id)) {
    				Membership customerMembership = new MembershipService().getByCustomer(customer.username);
    				customerMembership.appointmentNumber++;
    				customerMembership.usedAppointments--;
    				new MembershipService().saveAll();
    			}
    		});
    	}
    }
}
