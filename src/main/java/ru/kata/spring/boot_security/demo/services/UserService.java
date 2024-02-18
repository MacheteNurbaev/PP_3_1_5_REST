package ru.kata.spring.boot_security.demo.services;


import org.springframework.validation.BindingResult;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    void deleteUser(Long id);

    void addUser(User user, BindingResult bindingResult);

    void changeUser(User user, BindingResult bindingResult);

    User getUser(Long id);

    User findByEmail(String email);


}
