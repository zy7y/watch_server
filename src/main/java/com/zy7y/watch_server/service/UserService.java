package com.zy7y.watch_server.service;

import com.zy7y.watch_server.pojo.User;
import com.zy7y.watch_server.pojo.rep.R;

import java.util.List;

public interface UserService {
    List<User> getUserAll();

    R login(User user);

    R register(User user);
}
