package com.zy7y.watch_server.service.impl;

import com.zy7y.watch_server.dao.UserDao;
import com.zy7y.watch_server.pojo.User;
import com.zy7y.watch_server.service.UserService;
import com.zy7y.watch_server.util.JWTUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Map<String, Object> login(User user){
        User userObj = userDao.getUserByFilter(user);
        Map<String, Object> result = new HashMap<>();
        if (userObj != null && encoder.matches(user.getPassword(), userObj.getPassword())) {
            Map<String, String > map = new HashMap<>(); //用来存放payload信息

            map.put("id", userObj.getId().toString());
            map.put("username",userObj.getUsername());

            // 生成token令牌
            String token = JWTUtil.generateToken(map);

            // 返回前端token
            result.put("code","200");
            result.put("msg","登录成功");
            result.put("token",token);

            return result;
        }
        return result;
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
