package org.example.consumer;

import org.example.consumer.biz.BizCommonService;
import org.example.consumer.config.ConsumerRunnable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Partition3ToConsumer3 {

    private static String kafkaTopic = "test.hunyi";
    private static String groupId = "test-group-hunyi-3";

    public static void main(String[] args) {
        int num = 3;
        ExecutorService executorService = Executors.newFixedThreadPool(num);
        for (int i = 0; i < num; i++) {
            executorService.submit(new ConsumerRunnable(new BizCommonService(), groupId, kafkaTopic));
        }
    }

}
