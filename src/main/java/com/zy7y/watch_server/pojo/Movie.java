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
    private Date  createTime;
}
