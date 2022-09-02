package service;

import dto.AvgGradeRangeDTO;
import dto.FileDTO;
import dto.facility.*;
import model.facility.Content;
import model.facility.ContentType;
import model.facility.Facility;
import model.facility.FacilityType;
import model.manager.Manager;
import model.trainer.Trainer;
import repository.ContentRepository;
import repository.FacilityRepository;
import utility.UIDGenerator;
import webproj.Main;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ContentService {
    public static final ContentRepository contentRepository = ContentRepository.getInstance();
    public ContentService(){}


    public String createContent(String managerUsername, AddContentDTO dto) throws Content.CreateContentException {
        if(managerUsername == null || dto == null) throw new Content.CreateContentException("Something went wrong");
        Facility f = new FacilityService().getByManager(managerUsername);
        if(f == null) throw new Content.CreateContentException("Manager's facility not found");
        Content con = new Content(dto.name, dto.type);
        con.id = UIDGenerator.generate();
        con.facility_id = f.name;
        contentRepository.add(con);
        contentRepository.saveAll();
        return con.id;
    }

    public void deleteContent(String managerUsername, DeleteContentDTO dto) throws Content.DeleteContentException {
        if(managerUsername == null || dto == null) throw new Content.DeleteContentException("Something went wrong");
        Facility f = new FacilityService().getByManager(managerUsername);
        if(f == null) throw new Content.DeleteContentException("Manager's facility not found");
        Content con = contentRepository.getById(dto.id);
        if(con != null && con.facility_id.equals(f.name)){
            contentRepository.delete(con);
            contentRepository.saveAll();
        }else{
            throw new Content.DeleteContentException("Wrong facility or content not found");
        }
    }

    public ArrayList<Content> getFacilityContent(String facilityName){
        ArrayList<Content> content = new ArrayList<>();
        for(Content con: contentRepository.getAll()){
            if(con.facility_id.equals(facilityName)){
                content.add(con);
            }
        }
        return content;
    }

    public Trainer addTrainerToContent(String trainer_id, String content_id) throws Content.AddTrainerToContentException {
        Content content = contentRepository.getById(content_id);
        Trainer trainer = (Trainer) new UserService().getUser(trainer_id);
        if(trainer == null || content == null){
            throw new Content.AddTrainerToContentException("Something went wrong");
        }
        content.trainer_id = trainer.username;
        contentRepository.saveAll();
        return trainer;
    }

    public void removeTrainerFromContent(String content_id) throws Content.ClearTrainerFromContentException {
        Content content = contentRepository.getById(content_id);
        if(content == null){
            throw new Content.ClearTrainerFromContentException("Content with provided ID not found");
        }
        content.trainer_id = null;
        contentRepository.saveAll();
    }
    
    public ArrayList<Content> getTrainersGroupContent(String username) {    		
    	return (ArrayList<Content>) contentRepository.getAll()
    			.stream()
    			.filter(training -> training.trainer_id != null 
    				&& training.trainer_id.equals(username) 
    				&& training.type == ContentType.GROUP)
    			.collect(Collectors.toList());
    }
    
    public Content get(String id) {
    	return contentRepository.getById(id);
    }
}
