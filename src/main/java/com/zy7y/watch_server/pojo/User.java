package com.zy7y.watch_server.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * 用户实体类
 */
public class User {
    private Integer id;
    private String username;
    private String password;
}
