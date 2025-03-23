package com.kuang.other;

import java.util.Optional;

/**
 * @Author: JunYu
 * @Date: 2024/6/7 20:15
 * @Description:
 * @Version: V1.0.0
 */
public class TestOptional {

    public static void main(String[] args) {
        // 1.将可能为空的对象转换为Optional对象
        User user = new User("张三", 36, 1);
        Optional<User> optionalUser = Optional.ofNullable(user);

        // 2. Optional链式操作
        Integer age = optionalUser.map(User::getAge).orElse(30);
        System.out.println(age);

        // 3. Optional短路
        // 从数据库中查出某个用户，如果不存在则向数据库中新建用户并返回
//        User user1 = optionalUser.orElseGet(() -> new User());

        // 4. Optional抛出异常
        optionalUser.orElseThrow(() -> new RuntimeException("没有用户"));

        optionalUser.map(User::getAge).filter(a -> a < 30).ifPresent(a -> System.out.println(a));

    }

}

class User {
    private String name;
    private Integer age;
    private Integer gender;

    public User() {
    }

    public User(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public User(String name, Integer age, Integer gender) {
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

    public void setAge(Integer age) {
        this.age = age;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
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