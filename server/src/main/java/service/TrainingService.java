package service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Collectors;

import dto.user.TrainingWithContentType;
import model.facility.Content;
import model.facility.ContentType;
import model.trainer.Training;
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
	
	public ArrayList<Training> getUserTrainings(String username) {
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
}
