package org.example.consumer.biz;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.openservices.log.common.LogItem;
import org.example.producer.PushRequest;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.Objects;

public class BizLogic2 {

    private static final String AD_TYPE_NORMAL = "normal";
    private static final String AD_TYPE_TAG = "adType";
    private static final String AD_TYPE_CPUV = "cpuv";

    /**
     * sleep none, 1000000
     * start Thu Jan 09 14:47:45 CST 2025
     * end Thu Jan 09 14:47:47 CST 2025
     * cost 2672
     *
     * start Thu Jan 09 14:49:19 CST 2025
     * end Thu Jan 09 14:49:21 CST 2025
     * cost 2546
     *
     * start Thu Jan 09 14:52:27 CST 2025
     * end Thu Jan 09 14:52:30 CST 2025
     * cost 2760
     *
     * start Thu Jan 09 14:54:15 CST 2025
     * end Thu Jan 09 14:54:18 CST 2025
     * cost 2517
     */
    public static void main(String[] args) {
        int num = 1000000;
        PushRequest sample = PushRequest.getSample();
        String jsonString = JSON.toJSONString(sample);
        Date date = new Date();
        System.out.println("BizLogic2 start " + date);
        jsonString = "01" + jsonString;
        long time = date.getTime();
        for (int i = 0; i < num; i++) {
            new BizLogic2().handleMessage(jsonString);
        }
        date = new Date();
        System.out.println("BizLogic2 end " + date);
        System.out.println("BizLogic2 cost " + (date.getTime() - time));
    }

    public static long getCostTime() {
        int num = 1000000;
        PushRequest sample = PushRequest.getSample();
        String jsonString = JSON.toJSONString(sample);
        Date date = new Date();
        System.out.println("BizLogic2 start " + date);
        jsonString = "01" + jsonString;
        long time = date.getTime();
        for (int i = 0; i < num; i++) {
            new BizLogic2().handleMessage(jsonString);
        }
        date = new Date();
        return date.getTime() - time;
    }

    private String[] fields =
            new String[] {"pre", "utmUrl", "utmCnt", "utmCnt_a", "utmCnt_b", "utmCnt_c", "utmCnt_d", "utmCnt_e",
                    "scr", "os", "k", "b", "lver", "logType", "evt", "bdata", "mx", "my", "userId", "userRole",
                    "userCategory", "institution",
                    "districtCode", "ipAddr", "uuid", "uid", "pathname", "createTime", "clientTime", "origin", "url",
                    "queryString", "title", "phoneModel", "launchId", "carrier", "network", "lng", "lat", "appId",
                    "shopId", "source", "stub",
                    "tech", "env", "moduleName", "duringTime", "actionCode", "utmExpose", "tp", "thirdPartyInfo"};

    public void handleMessage(String jsonStr) {
        // 发送到阿里云日志云服务loghub
        push2LogService(jsonStr.substring(2));

        char c0 = jsonStr.charAt(0);
        char c1 = jsonStr.charAt(1);
        if (c0 == '1') {
            sendDeductMsg(jsonStr);
        }
        if (c1 == '1') {
            sendCpuvAdMsg(jsonStr);
        }

    }

    /**
     * 推送到sls
     *
     * @param jsonStr
     */
    private void push2LogService(String jsonStr) {
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);

        LogItem logItem = new LogItem((int) (System.currentTimeMillis() / 1000));
        for (String field : fields) {
            logItem.PushBack(field, String.valueOf(jsonObject.getOrDefault(field, "")));
        }
        // mock send sls log
//        try {
//            Thread.sleep(5);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
    }

    private void sendDeductMsg(String jsonStr) {
        // mock send TOPIC
//        try {
//            Thread.sleep(5);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
    }

    private void sendCpuvAdMsg(String jsonStr) {
        // mock send TOPIC
//        try {
//            Thread.sleep(5);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
    }
}
