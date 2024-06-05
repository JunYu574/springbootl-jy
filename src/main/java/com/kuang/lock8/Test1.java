package com.kuang.lock8;

import java.util.concurrent.TimeUnit;

/**
 * @Author: JunYu
 * 8锁，关于锁的8个问题
 * 1、标准情况下，两个线程先打印 发短信还是打电话？1、发短信 2、打电话
 * 2、sendSms延迟4秒，两个线程先打印 发短信还是打电话？1、发短信 2、打电话
 */
public class Test1 {

    public static void main(String[] args) {
        Phone phone = new Phone();

        new Thread(()->{
            phone.senSms();
        },"A").start();

        //捕获
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(()->{
            phone.call();
        },"B").start();

    }

}

class Phone{

    // synchronized 锁的对象是方法的调用者！
    // 两个方法用的同一个锁，谁先拿到谁执行
    public synchronized void senSms(){
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("发短信");
    }

    public synchronized void call(){
        System.out.println("打电话");
    }

}
