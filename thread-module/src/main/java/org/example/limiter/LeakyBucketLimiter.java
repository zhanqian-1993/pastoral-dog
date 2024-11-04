package org.example.limiter;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class LeakyBucketLimiter {

    private static Integer capacity = 20; //队列容量
    public static Queue<Long> queue = new ArrayBlockingQueue<>(capacity);// 队列
    public static Integer mockQPS = 10; //模拟请求速度
    public static Integer limitQps = 5; // 处理速度 1秒5个

    public static void main(String[] args) {
        new Thread(new MockRequest2()).start();
        new Thread(new ConsumeRequest()).start();
    }

    public static void request(long timestamp) {
        boolean offer = queue.offer(timestamp);
        if (offer) {
            System.out.println("放入请求到队列中" + timestamp);
        } else {
            System.out.println("丢弃请求" + timestamp);
        }
    }
}

class MockRequest2 implements Runnable {

    @Override
    public void run() {
        while (true) {
            for (int i = 0; i < LeakyBucketLimiter.mockQPS; i++) {
                LeakyBucketLimiter.request(System.currentTimeMillis());
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}

class ConsumeRequest implements Runnable {

    @Override
    public void run() {
        while (true) {
            while(true) {
                Long poll = LeakyBucketLimiter.queue.poll();
                if (poll != null) {
                    System.out.println("处理了请求：" + poll);
                    break;
                }
            }
            try {
                Thread.sleep(1000/LeakyBucketLimiter.limitQps);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
