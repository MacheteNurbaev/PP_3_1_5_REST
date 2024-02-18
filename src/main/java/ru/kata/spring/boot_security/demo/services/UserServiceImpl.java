package ru.kata.spring.boot_security.demo.services;


import org.hibernate.NonUniqueResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.security.BCPasswordEncoder;

import javax.validation.Valid;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final BCPasswordEncoder bcPasswordEncoder;
    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao, BCPasswordEncoder bcPasswordEncoder) {
        this.bcPasswordEncoder = bcPasswordEncoder;
        this.userDao = userDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public void deleteUser(Long id) {
        userDao.deleteUser(id);

    }

    @Override
    public void addUser(User user, BindingResult bindingResult) {
        Map<String, String> response = new HashMap<>();
        User us = user;

        if (bindingResult.hasErrors()) {
            for (FieldError error : bindingResult.getFieldErrors()) {
                response.put(error.getField(), error.getDefaultMessage());
            }
        } else {

            us.setPassword(bcPasswordEncoder.PaspasswordEncoder().encode(user.getPassword()));
            userDao.addUser(us);
            response.put("message", "User added successfully");
        }
        for (Map.Entry<String,String> res  : response.entrySet()) {
            System.out.println("ERROR : Field " + res.getKey() + " " + res.getValue());

        }


    }

    @Override
    public void changeUser(User user, BindingResult bindingResult) {
        Map<String, String> response = new HashMap<>();
        User us = user;

        if (bindingResult.hasErrors()) {
            for (FieldError error : bindingResult.getFieldErrors()) {
                response.put(error.getField(), error.getDefaultMessage());
            }
        } else {
            if (us.getPassword().equals("")) {
                us.setPassword(getUser(user.getId()).getPassword());
            } else {
                us.setPassword(bcPasswordEncoder.PaspasswordEncoder().encode(user.getPassword()));
            }
            userDao.changeUser(us);
            response.put("message", "User added successfully");
        }
        for (Map.Entry<String,String> res  : response.entrySet()) {
            System.out.println("ERROR : Field" + res.getKey() + "  " + res.getValue());

        }




    }

    @Override
    @Transactional(readOnly = true)
    public User getUser(Long id) {
        return userDao.getUser(id);
    }


    @Transactional(readOnly = true)
    public User findByEmail(String email) throws NonUniqueResultException {
        return userDao.findUserByEmail(email);
    }


}
