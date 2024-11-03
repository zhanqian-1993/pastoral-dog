package org.example.limiter;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 滑动窗口计数器
 */
public class WindowsCountLimiter {

    public static final Integer timeGap = 1000; // 窗口时间
    public static final Integer mockQps = 10; // 模拟请求次数
    public static final Integer limitQps = 5; // QPS极限
    public static final Queue<Long> slideWindow = new LinkedList<>();

    public static void main(String[] args) {
        // 模拟请求启动
        new Thread(new MockCounter()).start();

    }

    public static void request(Long timestamp) {
        if (allow(timestamp)) {
            slideWindow.offer(timestamp);
            System.out.println("请求成功");
        } else {
            System.out.println("请求失败");
        }
    }

    private static boolean allow(Long timestamp) {
        Long requestTimestamp = slideWindow.peek();
        if (requestTimestamp == null) {
            return true;
        }

        if ((requestTimestamp + timeGap) < timestamp) {
            slideWindow.poll();
        }
        if (slideWindow.size() < limitQps) {
            return true;
        }
        return false;
    }
}

class MockCounter implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < WindowsCountLimiter.mockQps; i++) {
            WindowsCountLimiter.request(System.currentTimeMillis());
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

/**
 *  1 2 3 4 5 6 7 8 9
 *
 *
 *
 */
