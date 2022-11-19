package com.zy7y.watch_server.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zy7y.watch_server.pojo.User;
import com.zy7y.watch_server.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public PageInfo<User> getUserAll(Integer pageNum, Integer pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<User> list =  userService.getUserAll();
        return new PageInfo<>(list);
    }
}
