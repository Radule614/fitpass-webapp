package service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Collectors;

import dto.user.TrainingWithContentType;
import dto.user.TrainingsFilterDTO;
import dto.user.UserTrainingsFilterDTO;
import model.User;
import model.UserType;
import model.customer.Customer;
import model.facility.Content;
import model.facility.ContentType;
import model.facility.FacilityType;
import model.trainer.Training;
import model.trainer.TrainingType;
import repository.TrainingRepository;

public class TrainingService {
	private static final TrainingRepository trainingRepository = TrainingRepository.getInstance();
	
	public TrainingService() {
		
	}
	
	public ArrayList<Training> getAll() {
		return (ArrayList<Training>) trainingRepository.getAll()
				.stream()
				.sorted((Training t1, Training t2) -> t1.getStart().compareTo(t2.getStart()))
				.collect(Collectors.toList());
	}
	
	public Training get(String trainingId) {
		Training requiredTraining = null;
		for(Training t : trainingRepository.getAll()) {
			if(t.getId().equals(trainingId)) {
				requiredTraining = t;
				break;
			}
		}
		
		return requiredTraining;
	}
	
	public ArrayList<Training> getTrainerTrainings(String username) {
		ArrayList<Training> userTrainings = (ArrayList<Training>) trainingRepository
				.getAll()
				.stream()
				.filter(training -> training.getTrainerUsername().equals(username))
				.sorted((Training t1, Training t2) -> t1.getStart().compareTo(t2.getStart()))
				.collect(Collectors.toList());
		
		return userTrainings;
	}
	
	public void add(Training training) {
		trainingRepository.add(training);
	}

	public void removeByFacility(String facility_id){
		trainingRepository.getAll().removeIf(c -> c.getFacilityName().equals(facility_id));
		for(Training t: trainingRepository.getAll()){
			if(t.getFacilityName().equals(facility_id)) trainingRepository.delete(t);
		}
		trainingRepository.saveAll();
	}
	
	public boolean cancel(String trainingId) {
		Training trainingToRemove = get(trainingId);
		if(trainingToRemove == null) return false;
		
		if(LocalDateTime.now().plusDays(2).isAfter(trainingToRemove.getStart())) return false;
		trainingRepository.delete(trainingToRemove);
		trainingRepository.saveAll();
		return true;
	}

	public ArrayList<Training> getFacilityTrainings(String facility_id){
		return (ArrayList<Training>) trainingRepository.getAll().stream().filter(t -> t.getFacilityName().equals(facility_id)).collect(Collectors.toList());
	}
	
	public ArrayList<Training> getTrainingsWithRequiredContentTypes(String[] contentTypes) {
		ArrayList<Training> allTrainings = this.getAll();
		if(contentTypes == null || contentTypes.length == 0) return allTrainings;
		ArrayList<ContentType> allowedContentTypes = new ArrayList<ContentType>();
		for(String ct : contentTypes) {
			allowedContentTypes.add(ContentType.valueOf(ct));
		}
		return (ArrayList<Training>) allTrainings
				.stream()
				.map(training -> new TrainingWithContentType(training))
				.filter(trainingWithContType -> allowedContentTypes.contains(trainingWithContType.contentType))
				.map(trainingWithContType -> trainingWithContType.training)
				.collect(Collectors.toList());
	}
	
	public ArrayList<Training> searchCustomerTrainings(String username, String name) {
		Customer customer = (Customer) new UserService().getUser(username);
		if(customer.trainingHistory != null) {
			return (ArrayList<Training>) customer.trainingHistory.stream()
					.map(trainingId -> new TrainingService().get(trainingId))
					.filter(training -> training.getName().toLowerCase().contains(name.toLowerCase()))
					.collect(Collectors.toList());
		}
		return null;
	}
	
	public ArrayList<Training> getUserTrainings(UserTrainingsFilterDTO dto) {
		User user = new UserService().getUser(dto.username);
		ArrayList<Training> requiredTrainings = new ArrayList<Training>();
		if(user.userType == UserType.CUSTOMER) {
			Customer customer = (Customer) user;
			requiredTrainings = (ArrayList<Training>) customer.trainingHistory.stream()
					.map(trainingId -> new TrainingService().get(trainingId))
					.collect(Collectors.toList());
		}
		if(user.userType == UserType.TRAINER) {
			requiredTrainings = new TrainingService().getTrainerTrainings(user.username);
		}
		
		if(dto.filters == null) return requiredTrainings;
		
		if(dto.filters.search != null && dto.filters.search.facilityName != null && !dto.filters.search.facilityName.trim().isEmpty()) {
			String query = dto.filters.search.facilityName.toLowerCase();
			requiredTrainings = (ArrayList<Training>) requiredTrainings.stream()
					.filter(training -> training.getFacilityName().toLowerCase().contains(query))
					.collect(Collectors.toList());
			
		}
		
		if(dto.filters.sort != null) {
			if(dto.filters.sort.by.equalsIgnoreCase("facilityName")) {
				if(!dto.filters.sort.reverse) {
					requiredTrainings = (ArrayList<Training>) requiredTrainings.stream()
						.sorted((t1, t2) -> t1.getFacilityName().compareToIgnoreCase(t2.getFacilityName()))
						.collect(Collectors.toList());
				} else {
					requiredTrainings = (ArrayList<Training>) requiredTrainings.stream()
							.sorted((t1, t2) -> t2.getFacilityName().compareToIgnoreCase(t1.getFacilityName()))
							.collect(Collectors.toList());
				}
			} else if(dto.filters.sort.by.equalsIgnoreCase("date")) {
				if(!dto.filters.sort.reverse) {
					requiredTrainings = (ArrayList<Training>) requiredTrainings.stream()
							.sorted((t1, t2) -> t1.getStart().isBefore(t2.getStart()) ? -1 : 1)
							.collect(Collectors.toList());
				} else {
					requiredTrainings = (ArrayList<Training>) requiredTrainings.stream()
							.sorted((t1, t2) -> t1.getStart().isBefore(t2.getStart()) ? 1 : -1)
							.collect(Collectors.toList());
				}
			}
		}
		
		if(dto.filters.filter != null) {
			try {
				if(dto.filters.filter.facilityType != null && !dto.filters.filter.facilityType.trim().isEmpty()) {
					requiredTrainings = (ArrayList<Training>) requiredTrainings.stream()
							.filter(t -> new FacilityService().getByName(t.getFacilityName()).facilityType == 
									FacilityType.valueOf(dto.filters.filter.facilityType))
							.collect(Collectors.toList());
				}
				
			
				if(dto.filters.filter.trainingType != null && !dto.filters.filter.trainingType.trim().isEmpty()) {
					requiredTrainings = (ArrayList<Training>) requiredTrainings.stream()
							.filter(t -> t.getType() == TrainingType.valueOf(dto.filters.filter.trainingType))
							.collect(Collectors.toList());
				}
				
				if(dto.filters.filter.contentType != null && !dto.filters.filter.contentType.trim().isEmpty()) {
					requiredTrainings = (ArrayList<Training>) requiredTrainings.stream()
							.map(t -> new TrainingWithContentType(t))
							.filter(t -> t.contentType.equals(ContentType.valueOf(dto.filters.filter.contentType.trim().toUpperCase())))
							.map(t -> t.training)
							.collect(Collectors.toList());
				}
			} catch(Exception ex) {
				ex.printStackTrace();
				System.out.println(ex.getMessage());
			}
			
		}
		
		return requiredTrainings;
	}
}
