package com.jy.emp.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: JunYu
 * @Date: 2024/2/26 19:13
 * @Description:
 * @Version: V1.0.0
 */
@Data
public class Emp implements Serializable {
    private Integer empId;
    private String name;
    private String sex;
    private Integer age;
    private Double sal;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    private String address;
    private Integer deptId;

    private Dept dept;
}
