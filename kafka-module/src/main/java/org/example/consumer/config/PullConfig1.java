package org.example.consumer.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.example.consumer.biz.BizCommonMockService;
import org.example.consumer.biz.BizCommonService;
import org.example.consumer.biz.BizLogic3;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Properties;

public class PullConfig1 implements Runnable {

    private BizCommonMockService bizCommonService;
    private String groupId;
    private String kafkaTopic;
    private long offset = 0;
    private Date date;

    public PullConfig1(BizCommonMockService bizCommonService, String groupId, String kafkaTopic) {
        this.bizCommonService = bizCommonService;
        this.groupId = groupId;
        this.kafkaTopic = kafkaTopic;
        date = new Date();

        System.out.println(groupId + " create ConsumerRunnable");
    }

    @Override
    public void run() {
        try {
            KafkaConsumer<String, String> consumer = createConsumer();
            consumer.subscribe(Collections.singletonList(kafkaTopic));
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                records.forEach(record -> {
                    bizCommonService.handleMessage(record.value());
                    offset += 1;
                });

                long gap = System.currentTimeMillis() - date.getTime();
                System.out.println("group_id: " + groupId + " offset: " + offset + ", cost_time: " + gap);
                if (gap > 600000) {
                    break;
                }
            }
        } catch (Exception e) {
            System.err.println("Consumer crashed, restarting...");
            e.printStackTrace();
        }
    }

    private KafkaConsumer<String, String> createConsumer() {
        // Kafka 配置
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "bigdata-kafka01.cai-inc.com:9092,bigdata-kafka02.cai-inc.com:9092,bigdata-kafka03.cai-inc.com:9092"); // Kafka 服务器地址
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId); // 消费者组 ID
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName()); // 键反序列化器
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName()); // 值反序列化器
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"); // 从最早的消息开始消费
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 500);

        // 创建 Kafka 消费者
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        return consumer;
    }
}
