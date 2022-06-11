package model.trainer;

import model.User;
import model.UserType;
import model.Workout;

import java.util.ArrayList;

public class Trainer extends User {
    public ArrayList<Workout> workoutHistory;

    public Trainer() {
        super(UserType.TRAINER);
    }
}
