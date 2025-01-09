package org.example.producer;

import com.alibaba.fastjson.JSONObject;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Date;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.TimeoutException;
import javax.xml.crypto.Data;

public class BizProducer {

    private static String servers = "bigdata-kafka01.cai-inc.com:9092,bigdata-kafka02.cai-inc.com:9092,bigdata-kafka03.cai-inc.com:9092";

    private static String acks = "1";

    private static int retries = 1;

    private static int batchSize = 16384;

    private static long lingerMs = 1;

    private static long bufferMemory = 33554432;

    private static KafkaProducer kafkaProducer = null;

    private static String kafkaTopic = "test.hunyi";

    public static void main(String[] args) {
        // Bean初始化完成后执行的逻辑
        System.out.println("KafkaProducerService 初始化完成，执行回调方法");
        kafkaProducer = initKafkaService();

        // 1348053
        for (int i = 0; i < 50000000; i++) {
            PushRequest pushRequest = new PushRequest();
            pushRequest.setAppId(String.valueOf(i));
            pushRequest.setActionCode("aaaa");
            pushRequest.setBdata("{\"aa\":\"bbb\"}");
            pushRequest.setB("b");
            pushRequest.setUtmCnt_a("a");
            pushRequest.setUtmCnt_b("b");
            pushRequest.setUtmCnt_c("c");
            pushRequest.setUtmCnt_d("d");
            pushRequest.setUtmCnt_e(UUID.randomUUID().toString());
            pushRequest.setOs("oss");
            pushRequest.setClientTime(System.currentTimeMillis());
            pushRequest.setCreateTime(System.currentTimeMillis());
            pushRequest.setUid(String.valueOf(i));
            pushRequest.setUrl("http://www.google.com");
            push(pushRequest);
        }
        // 关闭生产者
        kafkaProducer.close();
    }

    private static KafkaProducer initKafkaService() {
        Properties props = new Properties();
        props.put(org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        props.put(org.apache.kafka.clients.producer.ProducerConfig.ACKS_CONFIG, acks);
        props.put(org.apache.kafka.clients.producer.ProducerConfig.RETRIES_CONFIG, retries);
//        props.put(org.apache.kafka.clients.producer.ProducerConfig.BATCH_SIZE_CONFIG, batchSize);
//        props.put(org.apache.kafka.clients.producer.ProducerConfig.LINGER_MS_CONFIG, lingerMs);
//        props.put(org.apache.kafka.clients.producer.ProducerConfig.BUFFER_MEMORY_CONFIG, bufferMemory);
        props.put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return new KafkaProducer(props);
    }

    public static void push(PushRequest req) {
        String json = JSONObject.toJSONString(req);
        ProducerRecord record = new ProducerRecord<String, String>(kafkaTopic, json);
        System.out.println(record);

        // 发送消息并提供回调函数
        kafkaProducer.send(record, new Callback() {
            @Override
            public void onCompletion(RecordMetadata metadata, Exception exception) {
                if (exception == null) {
//                    System.out.println("Message sent to topic " + metadata.topic() + ", partition " + metadata.partition() + ", offset " + metadata.offset());
                } else {
                    if (exception instanceof TimeoutException) {
                        System.err.println("TimeoutException: " + exception.getMessage());
                    } else {
                        System.err.println("Error sending message: " + exception.getMessage());
                    }
                    exception.printStackTrace();
                }
            }
        });

    }
}
