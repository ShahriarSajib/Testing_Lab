package com.example.services;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String getUserName(int id) {
        User user = userRepository.findById(id);
        return user != null ? user.getName() : "User not found";
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }
}