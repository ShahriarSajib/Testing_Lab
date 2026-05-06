package com.example.services;

public interface UserRepository {
    User findById(int id);
    void save(User user);
}