package com.kuang.demo01;

/**
 * @Author: JunYu
 * @Date: 2024/5/15 12:53
 * @Description:
 * @Version: V1.0.0
 */
//基本卖票的例子
public class SaleTicketDemo01 {
    public static void main(String[] args) {
        // 并发：多线程操作同一个资源类，把资源类丢入线程
        Ticket ticket = new Ticket();

        // @FunctionalInterface 函数式接口
        new Thread(() -> {
            for (int i = 0; i < 110; i++) {
                ticket.sale();
            }
        },"A").start();
        new Thread(() -> {
            for (int i = 0; i < 110; i++) {
                ticket.sale();
            }
        },"B").start();
        new Thread(() -> {
            for (int i = 0; i < 110; i++) {
                ticket.sale();
            }
        },"C").start();
    }
}

//资源类 OOP
class Ticket{
    //属性、方法
    private int number = 100;

    //买票的方式
    public synchronized void sale(){
        if (number > 0){
            System.out.println(Thread.currentThread().getName()+"卖出了"+(number--)+"票，剩余："+number);
        }
    }

}
