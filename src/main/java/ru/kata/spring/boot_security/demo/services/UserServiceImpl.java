package ru.kata.spring.boot_security.demo.services;


import org.hibernate.NonUniqueResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.security.BCPasswordEncoder;


import java.util.List;


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
    public void addUser(User user) {
        User us = user;

        us.setPassword(bcPasswordEncoder.PaspasswordEncoder().encode(user.getPassword()));
        userDao.addUser(us);
    }


    @Override
    public void changeUser(User user) {
        User us = user;

        if (us.getPassword().equals("")) {
            us.setPassword(getUser(user.getId()).getPassword());
        } else {
            us.setPassword(bcPasswordEncoder.PaspasswordEncoder().encode(user.getPassword()));
        }
        userDao.changeUser(us);
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
