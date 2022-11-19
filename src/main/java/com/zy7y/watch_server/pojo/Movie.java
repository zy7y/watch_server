package com.zy7y.watch_server.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {
    private Integer id;
    private String movieName;
    private String movieYear;
    /**
     * 1 - 热映中
     * 2 - 待上映
     * 3 - 已停止
     */
    private Integer status;
    private Date  createTime;
    /**
     * 导演ID
     */
    private Integer directorId;
}
