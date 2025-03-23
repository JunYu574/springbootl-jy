package com.kuang.demo01;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: JunYu
 * @Date: 2024/5/15 12:53
 * @Description:
 * @Version: V1.0.0
 */
//基本卖票的例子
public class SaleTicketDemo02 {
    public static void main(String[] args) {
        // 并发：多线程操作同一个资源类，把资源类丢入线程
        Ticket2 ticket = new Ticket2();

        // @FunctionalInterface 函数式接口
        new Thread(() -> {for (int i = 0; i < 110; i++)ticket.sale();},"A").start();
        new Thread(() -> {for (int i = 0; i < 110; i++)ticket.sale();},"B").start();
        new Thread(() -> {for (int i = 0; i < 110; i++)ticket.sale();},"C").start();
    }
}

//Lock
//1、new ReentrantLock();
//2、lock.lock();//加锁
//3、lock.unlock();//解锁
//4、lock.tryLock();;//尝试获得锁
class Ticket2{
    //属性、方法
    private int number = 100;

    Lock lock = new ReentrantLock();

    //买票的方式
    public void sale(){

        lock.lock();//加锁

        try {
            if (number > 0){
                System.out.println(Thread.currentThread().getName()+"卖出了"+(number--)+"票，剩余："+number);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();//解锁
        }
    }

}
