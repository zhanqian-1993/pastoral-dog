package org.example.consumer.config;

import org.example.consumer.biz.BizCommonMockService;
import org.example.consumer.biz.BizCommonService;
import org.example.consumer.biz.BizLogic3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PullConfigPreformanceStatic {

    private static String kafkaTopic = "test.hunyi";

    public static void main(String[] args) {
        System.out.println("KafkaConsumerService 初始化完成，执行回调方法");

        ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.submit(new PullConfig1(new BizCommonMockService(), "test-pull-hunyi-1", kafkaTopic));
        executorService.submit(new PullConfig2(new BizCommonMockService(), "test-pull-hunyi-2", kafkaTopic));
        executorService.submit(new PullConfig3(new BizCommonMockService(), "test-pull-hunyi-3", kafkaTopic));
    }

}
