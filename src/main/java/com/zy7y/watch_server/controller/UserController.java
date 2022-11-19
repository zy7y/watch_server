package com.zy7y.watch_server.controller;

import com.zy7y.watch_server.pojo.User;
import com.zy7y.watch_server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public List<User> getUserAll(){
        return userService.getUserAll();
    }
}
