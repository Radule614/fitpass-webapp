package service;

import java.util.ArrayList;
import java.util.stream.Collectors;

import model.trainer.Training;
import repository.TrainingRepository;

public class TrainingService {
	private static final TrainingRepository trainingRepository = TrainingRepository.getInstance();
	
	public TrainingService() {
		
	}
	
	public ArrayList<Training> getAll() {
		return trainingRepository.getAll();
	}
	
	public ArrayList<Training> getUserTrainings(String username) {
		ArrayList<Training> userTrainings = (ArrayList<Training>) trainingRepository
				.getAll()
				.stream()
				.filter(training -> training.getTrainerUsername().equals(username))
				.collect(Collectors.toList());
		
		return userTrainings;
	}
	
	public void add(Training training) {
		trainingRepository.add(training);
	}
}
