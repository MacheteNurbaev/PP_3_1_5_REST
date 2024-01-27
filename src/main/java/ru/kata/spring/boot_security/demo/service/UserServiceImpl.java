package ru.kata.spring.boot_security.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.Dao;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;


@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final Dao userDao;

    @Autowired
    public UserServiceImpl(Dao userDao) {
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
    public void addUser(User user) {
        userDao.addUser(user);

    }

    @Override
    public void changeUser(User user) {
        userDao.changeUser(user);

    }

    @Override
    public User getUser(Long id) {
        return userDao.getUser(id);
    }
}
