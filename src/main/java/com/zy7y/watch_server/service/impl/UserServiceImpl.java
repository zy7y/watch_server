package com.zy7y.watch_server.service.impl;

import com.zy7y.watch_server.dao.UserDao;
import com.zy7y.watch_server.pojo.User;
import com.zy7y.watch_server.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private UserDao userDao;
    private BCryptPasswordEncoder encoder;

    public UserServiceImpl(UserDao userDao, BCryptPasswordEncoder encoder) {
        this.userDao = userDao;
        this.encoder = encoder;
    }

    @Override
    public List<User> getUserAll() {
        return userDao.findAll();
    }

    @Override
    public String login(User user){
        User userObj = userDao.getUserByFilter(user);
        if (userObj != null && encoder.matches(user.getPassword(), userObj.getPassword())) {
            return userObj.getUsername();
        }
        return "用户名或密码错误";
    }

    @Override
    public String register(User user) {
        // 1. 查用户是否存在
        User userObj = userDao.getUserByFilter(user);
        if (userObj != null) {
            return "用户已存在";
        }
        // register
        user.setPassword(encoder.encode(user.getPassword()));
        userDao.insert(user);
        userDao.getUserByFilter(user);
        return user.toString();
    }
}
