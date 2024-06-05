package com.kuang.lock8;

import java.util.concurrent.TimeUnit;

/**
 * @Author: JunYu
 * 3、增加了一个普通方法后，先发短信还是hello？ 普通方法
 * 4、两个对象，两个同步方法，发短信还是打电话？ 1、打电话 2、发短信
 */
public class Test2 {

    public static void main(String[] args) {
        //两个对象，两个调用者，两把锁
        Phone2 phone1 = new Phone2();
        Phone2 phone2 = new Phone2();

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

class Phone2{

    // synchronized 锁的对象是方法的调用者！
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

    //这里没有锁！不是同步方法，不受锁的影响
    public void hello(){
        System.out.println("hello");
    }
}
