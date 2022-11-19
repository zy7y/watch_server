package com.zy7y.watch_server.service;

import com.zy7y.watch_server.pojo.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    List<User> getUserAll();

    Map<String, Object> login(User user);

    String register(User user);
}
