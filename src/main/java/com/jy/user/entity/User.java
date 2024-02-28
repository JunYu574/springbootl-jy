package com.jy.user.entity;

import lombok.Data;

/**
 * @Author: JunYu
 * @Date: 2024/2/22 20:02
 * @Description:
 * @Version: V1.0.0
 */
@Data
public class User {

    private Integer id;
    private String username;
    private String password;
    private String chName;

}
