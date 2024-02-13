package ru.kata.spring.boot_security.demo.services;


import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    void deleteUser(Long id);

    void addUser(User user);

    void changeUser(User user);

    User getUser(Long id);

    User findByEmail(String email);


}
