package com.jy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: JunYu
 * @Date: 2024/1/26 19:40
 * @Description:
 * @Version: V1.0.0
 */

@SpringBootApplication
@MapperScan("com.jy.*.mapper")
public class JdzdApplication {

    public static void main(String[] args) {
        SpringApplication.run(JdzdApplication.class);
    }
}
