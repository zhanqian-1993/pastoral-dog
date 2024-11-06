package org.example.limiter;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 令牌池
 */
public class TokenPoolLimiter {

    public static final Integer lifeCycle = 1000; // 令牌生命周期（毫秒）
    public static final Integer buildCycle = 50;//令牌生成周期
    public static final Queue<Long> pool = new LinkedList<>();//令牌池
    public static final Integer mockQps = 10;

    public static void main(String[] args) {
        new Thread(new MockRequest3());
        new Thread(new BuildTokenClass());
    }

    public static void request(long timestamp) {
        long now = System.currentTimeMillis();
        Long token = pool.poll();

        while (token != null && (token + lifeCycle) < now) {
            token = pool.poll();
        }
        if (token == null) {
            System.out.println("没有令牌，失败");
        } else {
            System.out.println("获取令牌，开始处理");
        }
    }
}

class MockRequest3 implements Runnable {

    @Override
    public void run() {
        while (true) {
            for (int i = 0; i < TokenPoolLimiter.mockQps; i++) {
                TokenPoolLimiter.request(System.currentTimeMillis());
            }
        }
    }
}

class BuildTokenClass implements Runnable {

    @Override
    public void run() {
        while (true) {
            TokenPoolLimiter.pool.offer(System.currentTimeMillis());
            try {
                Thread.sleep(TokenPoolLimiter.buildCycle);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
