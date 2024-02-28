package com.jy.test;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.DigestUtils;

/**
 * @Author: JunYu
 * @Date: 2024/2/24 12:20
 * @Description:
 * @Version: V1.0.0
 */

public class CryptTest {

    @Test
    public void test(){
        //md5 spring提供的加密方法，加盐得自己处理
        /*String s1 = DigestUtils.md5DigestAsHex("123456".getBytes());
        String s2 = DigestUtils.md5DigestAsHex("123456".getBytes());
        System.out.println(s1);
        System.out.println(s2);*/

        //sprinhg安全框架提供的加密方法，可以自动加盐，无需自己保存盐值
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode1 = passwordEncoder.encode("123456");
        String encode2 = passwordEncoder.encode("123456");
        System.out.println(encode1);
        System.out.println(encode2);

        //验证
        boolean matches1 = passwordEncoder.matches("123456", "$2a$10$YAi54h7WZ5H3g6Hql0qA/uWD1Stn1Mk5fkgGPFBB3FAEHPp0w4.c6");
        boolean matches2 = passwordEncoder.matches("123456", "$2a$10$Y8kfTY7qV2LfVwJRtn0GTOODbKy3FSZYWZJ.h52QX4i.r5AGZ7/n6");
        System.out.println(matches1);
        System.out.println(matches2);
    }
}
