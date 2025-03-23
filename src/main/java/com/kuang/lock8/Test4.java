package com.kuang.lock8;

import java.util.concurrent.TimeUnit;

/**
 * @Author: JunYu
 * 7、一个静态的同步方法，一个普通的同步方法，一个对象，先打印 发短信？打电话？ 打电话
 * 8、一个静态的同步方法，一个普通的同步方法，两个对象，先打印 发短信？打电话？ 打电话
 */
public class Test4 {

    public static void main(String[] args) {
        //两个对象的Class类模板只有一个，static，锁的是Class
        Phone4 phone1 = new Phone4();
        Phone4 phone2 = new Phone4();

        //锁的存在
        new Thread(()->{
            phone1.senSms();
        },"A").start();

        //捕获
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(()->{
            phone2.call();
        },"B").start();

    }

}

// Phone4唯一的一个 Class 对象
class Phone4{

    // 静态的同步方法
    public static synchronized void senSms(){
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("发短信");
    }

    // 普通的同步方法
    public synchronized void call(){
        System.out.println("打电话");
    }

}

