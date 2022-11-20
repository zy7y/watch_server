package com.zy7y.watch_server.controller;

import com.zy7y.watch_server.pojo.rep.R;
import com.zy7y.watch_server.pojo.User;
import com.zy7y.watch_server.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name="认证相关")
@RestController
public class UserController {

    @Autowired
    private UserService userService;
//
//    @Operation(summary = "分页查询所有管理员")
//    @GetMapping("/user")
//    public PageInfo<User> getUserAll(Integer pageNum, Integer pageSize){
//        PageHelper.startPage(pageNum, pageSize);
//        List<User> list =  userService.getUserAll();
//        return new PageInfo<>(list);
//    }

    @Operation(summary = "管理员注册")
    @PostMapping("/register")
    public R register(@RequestBody User user){
        return userService.register(user);
    }

    @Operation(summary = "管理员登录")
    @PostMapping("/login")
    public R login(@RequestBody User user) {
        return userService.login(user);
    }
}
