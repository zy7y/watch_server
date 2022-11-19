package com.zy7y.watch_server.service.impl;

import com.zy7y.watch_server.dao.UserDao;
import com.zy7y.watch_server.pojo.User;
import com.zy7y.watch_server.pojo.rep.GetToken;
import com.zy7y.watch_server.pojo.rep.R;
import com.zy7y.watch_server.service.UserService;
import com.zy7y.watch_server.util.JWTUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.zy7y.watch_server.pojo.rep.ResultCode.USER_LOGIN_ERROR;

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
    public R login(User user){
        User userObj = userDao.getUserByFilter(user);
        if (userObj != null && encoder.matches(user.getPassword(), userObj.getPassword())) {
            Map<String, String > map = new HashMap<>(); //用来存放payload信息
            map.put("id", userObj.getId().toString());
            map.put("username",userObj.getUsername());
            GetToken data = new GetToken(userObj.getId(), userObj.getUsername(), JWTUtil.generateToken(map));
            return R.success(data);
        }
        return R.fail(USER_LOGIN_ERROR);
    }

    @Override
    public R register(User user) {
        User userObj = userDao.getUserByFilter(user);
        if (userObj != null) {
            return R.fail(2003, "用户已存在");
        }
        user.setPassword(encoder.encode(user.getPassword()));
        userDao.insert(user);
        return R.success();
    }
}
