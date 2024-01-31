package ru.kata.spring.boot_security.demo.service;


import org.springframework.security.core.userdetails.UserDetailsService;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    void deleteUser(Long io);

    void addUser(User user);

    void changeUser(User user);

    User getUser(Long id);


}
