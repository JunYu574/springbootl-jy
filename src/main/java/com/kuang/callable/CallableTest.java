package com.kuang.callable;

import java.util.concurrent.*;

/**
 * @Author: JunYu
 * @Date: 2024/5/22 20:41
 * @Description:
 * @Version: V1.0.0
 */
public class CallableTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // new Thread(new Runnable).start();
        // new Thread(new FutureTask<V>()).start();
        // new Thread(new FutureTask<V>( Callable )).start();
        new Thread().start();// 怎么启动Callable

        MyThread thread = new MyThread();
        FutureTask<Integer> futureTask = new FutureTask<>(thread);// 适配类

        new Thread(futureTask,"A").start();
        new Thread(futureTask,"B").start();//结果会被缓存，效率高

        Integer o = futureTask.get();// 这个get方法可能会产生阻塞！把他放到最后
        //或者使用异步通信来处理
        System.out.println(o);
    }

}

class MyThread implements Callable<Integer> {

    @Override
    public Integer call() {
        System.out.println("call()");
        return 1024;
    }
}
