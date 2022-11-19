package com.zy7y.watch_server.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zy7y.watch_server.pojo.User;
import com.zy7y.watch_server.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name="管理员相关")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "分页查询所有管理员")
    @GetMapping("/user")
    public PageInfo<User> getUserAll(Integer pageNum, Integer pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<User> list =  userService.getUserAll();
        return new PageInfo<>(list);
    }
}
