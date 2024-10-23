package org.example.limiter;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 计数器算法
 */
public class CounterLimiter {

    public static AtomicLong atomicLong = new AtomicLong(0);
    // 间隔时间 1000 毫秒
    public static Integer timeGap = 1000;
    // 间隔时间内最大请求次数
    private static Integer maxRequestCount = 5;
    // 间隔时间内发起模拟请求的次数
    public static Integer mockCountPerTimeGap = 10;

    public static void main(String[] args) {
        new Thread(new CounterReset()).start();
        new MockRequest().start();
    }

    public static void request() {
        long i;
        if ((i = atomicLong.incrementAndGet()) <= maxRequestCount) {
            System.out.println("accept request: " + i);
        } else {
            System.out.println("reject request: " + i);
        }
    }
}

/**
 * 重置计数器
 */
class CounterReset implements Runnable {
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(CounterLimiter.timeGap);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            CounterLimiter.atomicLong.set(0);
            System.out.println("CounterReset 0");
        }
    }
}

/**
 *  模拟请求线程
 */
class MockRequest extends Thread {

    @Override
    public void run() {
        while (true) {
            for (int i = 0 ; i < CounterLimiter.mockCountPerTimeGap; i++) {
                CounterLimiter.request();
            }
            try {
                Thread.sleep(CounterLimiter.timeGap);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}

