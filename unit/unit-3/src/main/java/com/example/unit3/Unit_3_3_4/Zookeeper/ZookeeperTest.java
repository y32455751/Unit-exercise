package com.example.unit3.Unit_3_3_4.Zookeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * 分布式锁测试代码
 */
public class ZookeeperTest {
    public static void main(String[] args) throws IOException {
        // 新建10个countDOwn，用来让每个锁请求都能成功的执行完
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for(int i=0;i<10;i++){
            new Thread(() -> {  // 使用线程启动10个请求
                try {
                    countDownLatch.await(); //在这里等待一个请求执行完
                    ZookeeperLockDemo zookeeperLockDemo = new ZookeeperLockDemo();
                    zookeeperLockDemo.lock();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            },"线程"+i).start();
            countDownLatch.countDown();
        }
        System.in.read();// 保持进程运行
    }
}
