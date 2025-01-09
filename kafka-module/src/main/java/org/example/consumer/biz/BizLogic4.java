package org.example.consumer.biz;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.openservices.log.common.LogItem;
import org.example.producer.PushRequest;

import java.util.Date;
import java.util.Objects;

public class BizLogic4 {

    private static final String AD_TYPE_NORMAL = "normal";
    private static final String AD_TYPE_TAG = "adType";
    private static final String AD_TYPE_CPUV = "cpuv";

    /**
     * sleep none, 1000000
     *
     * start Thu Jan 09 15:08:53 CST 2025
     * end Thu Jan 09 15:08:56 CST 2025
     * cost 2224
     *
     * start Thu Jan 09 15:09:10 CST 2025
     * end Thu Jan 09 15:09:12 CST 2025
     * cost 2821
     *
     * start Thu Jan 09 15:09:23 CST 2025
     * end Thu Jan 09 15:09:25 CST 2025
     * cost 2404
     *
     * start Thu Jan 09 15:09:55 CST 2025
     * end Thu Jan 09 15:09:58 CST 2025
     * cost 2508
     *
     * start Thu Jan 09 15:10:26 CST 2025
     * end Thu Jan 09 15:10:29 CST 2025
     * cost 2641
     *
     * start Thu Jan 09 15:11:20 CST 2025
     * end Thu Jan 09 15:11:23 CST 2025
     * cost 2538
     *
     * start Thu Jan 09 15:12:42 CST 2025
     * end Thu Jan 09 15:12:45 CST 2025
     * cost 2473
     */
    public static void main(String[] args) {
        int num = 1000000;
        PushRequest sample = PushRequest.getSample();
        String jsonString = JSON.toJSONString(sample);
        JSONObject jsonObject = JSON.parseObject(jsonString);
        jsonObject.put("adTypeMq", "10");
        jsonString = jsonObject.toJSONString();
        Date date = new Date();
        System.out.println("BizLogic4 start " + date);
        long time = date.getTime();
        for (int i = 0; i < num; i++) {
            new BizLogic4().handleMessage(jsonString);
        }
        date = new Date();
        System.out.println("BizLogic4 end " + date);
        System.out.println("BizLogic4 cost " + (date.getTime() - time));
    }

    public static long getCostTime() {
        int num = 1000000;
        PushRequest sample = PushRequest.getSample();
        String jsonString = JSON.toJSONString(sample);
        JSONObject jsonObject = JSON.parseObject(jsonString);
        jsonObject.put("adTypeMq", "10");
        jsonString = jsonObject.toJSONString();
        Date date = new Date();
        System.out.println("BizLogic4 start " + date);
        long time = date.getTime();
        for (int i = 0; i < num; i++) {
            new BizLogic4().handleMessage(jsonString);
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
        JSONObject req = null;
        try {
            req = JSONObject.parseObject(jsonStr);
        } catch (Exception e) {
            System.out.println("MqService json parse error " + e.getMessage());
        }
        // 发送到阿里云日志云服务loghub
        push2LogService(req);

        String adType1 = req.getString("adTypeMq");
        if (adType1.charAt(0) == '1') {
            sendDeductMsg(jsonStr);
        }
        if (adType1.charAt(1) == '1') {
            sendCpuvAdMsg(jsonStr);
        }

    }

    /**
     * 推送到sls
     *
     * @param jsonObject
     */
    private void push2LogService(JSONObject jsonObject) {

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
        //            try {
        //                Thread.sleep(5);
        //            } catch (InterruptedException e) {
        //                throw new RuntimeException(e);
        //            }
    }

    private void sendCpuvAdMsg(String jsonStr) {
        // mock send TOPIC
        //            try {
        //                Thread.sleep(5);
        //            } catch (InterruptedException e) {
        //                throw new RuntimeException(e);
        //            }
    }
}
