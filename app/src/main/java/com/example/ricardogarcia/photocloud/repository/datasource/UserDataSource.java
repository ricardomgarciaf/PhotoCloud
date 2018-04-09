package com.example.ricardogarcia.photocloud.repository.datasource;

import com.example.ricardogarcia.photocloud.repository.dao.UserDao;
import com.example.ricardogarcia.photocloud.repository.entity.User;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Ricardo Garcia on 4/8/2018.
 */

public class UserDataSource implements DataSource<User> {

    private UserDao userDao;

    @Inject
    public UserDataSource(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    public void addItem(User item) {
        userDao.insert(item);
    }

    @Override
    public void deleteItem(User item) {
        userDao.delete(item);
    }


    public User findByName(String name) {
        return userDao.findByUsername(name);
    }


}
