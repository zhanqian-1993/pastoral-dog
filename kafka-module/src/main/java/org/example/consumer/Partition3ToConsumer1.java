package org.example.consumer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.extern.slf4j.Slf4j;
import org.example.consumer.biz.BizCommonService;

public class Partition3ToConsumer1 {

    private static String kafkaTopic = "test.hunyi";
    private static String groupId = "test-group-1";

    public static void main(String[] args) {
        System.out.println("KafkaConsumerService 初始化完成，执行回调方法");

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(new ConsumerRunnable(new BizCommonService(), groupId, kafkaTopic));
    }

}
