package com.jy.test;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: JunYu
 * @Date: 2024/2/24 12:20
 * @Description:
 * @Version: V1.0.0
 */

public class CryptTest implements Comparable<CryptTest> {

    private int num;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Test
    public void test() {

        List<User> list = new ArrayList<>();
        Comparator<User> users = Comparator.comparing(User::getAge);
        list.add(new User("张三", 18, 0));
        list.add(new User("李四", 16, 1));
        list.add(new User("王五", 20, 0));
        list.add(new User("赵六", 17, 1));

        Map<Integer, List<User>> groups = list.stream().collect(Collectors.groupingBy(User::getGender));
        Map<String, List<User>> groups2 = list.stream().collect(Collectors.groupingBy(user -> {
            int age = user.getAge();
            if (age >= 18 && age <= 20) {
                return "18-20";
            } else if (age >= 21 && age <= 23) {
                return "21-23";
            } else {
                return "其他";
            }
        }));

        Map<Integer, Long> count = list.stream().collect(Collectors.groupingBy(User::getGender, Collectors.counting()));

        HashMap<Integer, Long> reduce = list.stream().reduce(new HashMap<Integer, Long>(), (m, u) -> {
            m.put(u.getGender(), m.getOrDefault(u.getGender(), 0L) + 1);
            return m;
        }, (m1, m2) -> {
            m1.putAll(m2);
            return m1;
        });

//        IntSummaryStatistics collect = list.stream().collect(Collectors.summarizingInt(User::getAge));
//
//        System.out.println("collect.getSum() = " + collect.getSum());
//        System.out.println("collect.getAverage() = " + collect.getAverage());

        //md5 spring提供的加密方法，加盐得自己处理
        /*String s1 = DigestUtils.md5DigestAsHex("123456".getBytes());
        String s2 = DigestUtils.md5DigestAsHex("123456".getBytes());
        System.out.println(s1);
        System.out.println(s2);*/

        //sprinhg安全框架提供的加密方法，可以自动加盐，无需自己保存盐值
        /*BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode1 = passwordEncoder.encode("123456");
        String encode2 = passwordEncoder.encode("123456");
        System.out.println(encode1);
        System.out.println(encode2);

        //验证
        boolean matches1 = passwordEncoder.matches("123456", "$2a$10$YAi54h7WZ5H3g6Hql0qA/uWD1Stn1Mk5fkgGPFBB3FAEHPp0w4.c6");
        boolean matches2 = passwordEncoder.matches("123456", "$2a$10$Y8kfTY7qV2LfVwJRtn0GTOODbKy3FSZYWZJ.h52QX4i.r5AGZ7/n6");
        System.out.println(matches1);
        System.out.println(matches2);*/

        /*try {
            // 需要计算哈希值的字符串
            String str = "Hello World!";

            // 创建SHA-256哈希算法的MessageDigest对象
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");

            // 使用hash()方法计算字符串的哈希值
            byte[] digest = sha256.digest(str.getBytes("UTF-8"));

            // 将哈希值转换为十六进制字符串
            StringBuilder hexString = new StringBuilder();
            for (byte b : digest) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append("0");
                }
                hexString.append(hex);
            }
            System.out.println("SHA-256 Hash：" + hexString.toString());

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }*/

    }

    @Override
    public int compareTo(CryptTest o) {
        return o.getNum() - this.num;
    }
}

class User {
    private String name;
    private int age;
    private int gender;

    public User() {
    }

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public User(String name, int age, int gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", gender=" + gender +
                '}';
    }
}
