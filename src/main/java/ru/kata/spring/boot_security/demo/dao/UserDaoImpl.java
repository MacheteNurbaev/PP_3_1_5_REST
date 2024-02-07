package ru.kata.spring.boot_security.demo.dao;


import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.util.List;


@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    @Override
    public void deleteUser(Long id) {
        User us = entityManager.find(User.class, id);
        if (us == null) {
            throw new EntityNotFoundException();
        }
        entityManager.remove(us);
        entityManager.flush();
    }

    @Override
    public void addUser(User user) {
        entityManager.persist(user);
        entityManager.flush();

    }

    @Override
    public void changeUser(User user) {
        User us = entityManager.find(User.class, user.getId());
        if (us == null) {
            throw new EntityNotFoundException();
        }
        us.setId(user.getId());
        us.setName(user.getName());
        us.setLastName(user.getLastName());
        us.setAge(user.getAge());
        us.setUsername(user.getUsername());
        us.setPassword(user.getPassword());
        us.setRoles(user.getRoles());
        entityManager.flush();


    }

    @Override
    public User getUser(Long id) {
        User us = entityManager.find(User.class, id);
        if (us == null) {
            throw new EntityNotFoundException();
        }
        return us;
    }

    @Override
    public User findUserByName(String username) {
        return entityManager.createQuery("select u from User u left join fetch u.roles where u.username=:username", User.class)
                .setParameter("username", username).getSingleResult();

    }


}
