package com.zy7y.watch_server.service.impl;

import com.zy7y.watch_server.dao.UserDao;
import com.zy7y.watch_server.pojo.User;
import com.zy7y.watch_server.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private UserDao userDao;

    public UserServiceImpl(UserDao userDao){
        this.userDao = userDao;
    }

    @Override
    public List<User> getUserAll() {
        return userDao.findAll();
    }
}
