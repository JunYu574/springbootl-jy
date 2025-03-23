package com.jy.test;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @Author: JunYu
 * @Date: 2024/5/15 12:02
 * @Description:
 * @Version: V1.0.0
 */
public class RunTest {

    @Test
    public void test() throws InterruptedException {

        System.out.println(Runtime.getRuntime().availableProcessors());
        TimeUnit.SECONDS.sleep(2);
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("yyyy");
            }
        });
        //wait 同步代码块
    }

}
