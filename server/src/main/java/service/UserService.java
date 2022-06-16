package service;

import model.User;
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
}
