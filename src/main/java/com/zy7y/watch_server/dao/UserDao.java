package com.zy7y.watch_server.dao;

import com.zy7y.watch_server.pojo.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface UserDao {

    /**
     * 查所有用户
     * @return
     */
    List<User> findAll();
}
