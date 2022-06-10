package service;

import model.User;
import repository.UserRepository;

public class UserService {
    private static UserRepository userRepository;
    public UserService(){
        userRepository = UserRepository.getInstance();
    }

    public User getUser(String username) {
        for(User u: userRepository.getUsers()) {
            if(u.username.equals(username)) {
                return u;
            }
        }
        return null;
    }
}
