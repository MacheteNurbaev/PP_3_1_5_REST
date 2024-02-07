package ru.kata.spring.boot_security.demo.services;


import org.hibernate.NonUniqueResultException;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    void deleteUser(Long io);

    void addUser(User user);

    void changeUser(User user);

    User getUser(Long id);

    User findByUserName(String username);



}
