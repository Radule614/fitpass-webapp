package service;

import model.facility.Grade;
import repository.GradeRepository;

public class GradeService {
	public static final GradeRepository gradeRepository = GradeRepository.getInstance();
	
	public void addGrade(Grade g) {
		if(!overrideGrade(g) ) {			
			gradeRepository.add(g);
		}
	}
	
	private boolean overrideGrade(Grade g) {
		boolean exists = false;
		for(Grade grade : gradeRepository.getAll()) {
			if(grade.getUsername().equals(g.getUsername()) && grade.getFacilityName().equals(g.getFacilityName())) {
				exists = true;
				break;
			}
		}
		
		if(exists) {
			gradeRepository.update(g);
			return true;
		}
		return false;
	}
}
