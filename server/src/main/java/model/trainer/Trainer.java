package model.trainer;

import dto.user.TrainerDTO;
import dto.user.UserDTO;
import model.User;
import model.UserType;
import model.Workout;

import java.util.ArrayList;

public class Trainer extends User {
    public ArrayList<Workout> workoutHistory;

    public Trainer() {
        super(UserType.TRAINER);
    }

    @Override
    public UserDTO getDTO() {
        return new TrainerDTO(this);
    }
    @Override
    public UserDTO getDTO(boolean __) {
        return new TrainerDTO(this);
    }
}
