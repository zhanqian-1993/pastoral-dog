package org.example.consumer.biz;

import com.alibaba.fastjson.JSONObject;

import java.util.Objects;

public class BizCommonMockService {

    public void handleMessage(String jsonStr) {
        try {
            Thread.sleep(40);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
