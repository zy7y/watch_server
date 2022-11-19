package com.zy7y.watch_server.pojo.rep;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class GetToken {
    private Integer id;
    private String username;
    private String token;
}
