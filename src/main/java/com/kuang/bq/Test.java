package com.kuang.bq;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @Author: JunYu
 * @Date: 2024/7/5 12:48
 * @Description:
 * @Version: V1.0.0
 */
public class Test {
    public static void main(String[] args) {
        test2();
    }
    /**
     * 抛出异常
     */
    public static void test1(){
        // 队列的大小
        ArrayBlockingQueue blockingQueue = new ArrayBlockingQueue<>(3);

        System.out.println(blockingQueue.add("a"));
        System.out.println(blockingQueue.add("b"));
        System.out.println(blockingQueue.add("c"));

        System.out.println("=========================");

        // java.lang.IllegalStateException: Queue full 抛出异常！
        // System.out.println(blockingQueue.add("d"));

        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());

        // java.util.NoSuchElementException 抛出异常
        // System.out.println(blockingQueue.remove());

    }

    /**
     *
     */
    public static void test2(){
        // 队列的大小
        ArrayBlockingQueue blockingQueue = new ArrayBlockingQueue<>(3);

        System.out.println(blockingQueue.offer("a"));
        System.out.println(blockingQueue.offer("b"));
        System.out.println(blockingQueue.offer("c"));

        // System.out.println(blockingQueue.offer("d"));
        System.out.println("=========================");

        System.out.println(blockingQueue.element());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());

    }
}
