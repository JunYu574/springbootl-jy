package com.jy.emp.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: JunYu
 * @Date: 2024/2/27 18:19
 * @Description:
 * @Version: V1.0.0
 */
@Data
public class Dept implements Serializable {

    private Integer deptId;
    private String deptName;

}
