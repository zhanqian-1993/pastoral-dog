package org.example.consumer.biz;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.openservices.log.common.LogItem;
import org.example.producer.PushRequest;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.Objects;

public class BizLogic1 {

    private static final String AD_TYPE_NORMAL = "normal";
    private static final String AD_TYPE_TAG = "adType";
    private static final String AD_TYPE_CPUV = "cpuv";

    /**
     * sleep none, 1000000
     * start Thu Jan 09 14:47:17 CST 2025
     * end Thu Jan 09 14:47:21 CST 2025
     * cost 3737
     *
     * start Thu Jan 09 14:49:02 CST 2025
     * end Thu Jan 09 14:49:05 CST 2025
     * cost 3557
     *
     * start Thu Jan 09 14:52:45 CST 2025
     * end Thu Jan 09 14:52:49 CST 2025
     * cost 3361
     *
     * start Thu Jan 09 15:14:08 CST 2025
     * end Thu Jan 09 15:14:11 CST 2025
     * cost 3410
     */
    public static void main(String[] args) {
        int num = 1000000;
        PushRequest sample = PushRequest.getSample();
        String jsonString = JSON.toJSONString(sample);
        Date date = new Date();
        System.out.println("BizLogic1 start " + date);
        long time = date.getTime();
        for (int i = 0; i < num; i++) {
            new BizLogic1().handleMessage(jsonString);
        }
        date = new Date();
        System.out.println("BizLogic1 end " + date);
        System.out.println("BizLogic1 cost " + (date.getTime() - time));
    }

    public static long getCostTime() {
        int num = 1000000;
        PushRequest sample = PushRequest.getSample();
        String jsonString = JSON.toJSONString(sample);
        Date date = new Date();
        long time = date.getTime();
        for (int i = 0; i < num; i++) {
            new BizLogic1().handleMessage(jsonString);
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
        push2LogService(jsonStr);

        JSONObject req = null;
        try {
            req = JSONObject.parseObject(jsonStr);
        } catch (Exception e) {
            System.out.println("MqService json parse error " + e.getMessage());
        }
        if (!Objects.isNull(req)) {
            String bdata = req.getString("bdata");
            if (bdata != null && bdata.startsWith("{")) {
                JSONObject bdataJSONObject;
                try {
                    bdataJSONObject = JSONObject.parseObject(bdata);
                    String adType = (String) bdataJSONObject.get(AD_TYPE_TAG);
                    sendDeductMsg(jsonStr, adType);
                    sendCpuvAdMsg(jsonStr, adType);
                } catch (Exception e) {
                    System.out.println("MqService json parse error " + e.getMessage());
                }
            }
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

    private void sendDeductMsg(String jsonStr, String adType) {
        // mock send TOPIC
        if (adType != null && AD_TYPE_NORMAL.equals(adType)) {
//            try {
//                Thread.sleep(5);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
        }
    }

    private void sendCpuvAdMsg(String jsonStr, String adType) {
        // mock send TOPIC
        if (adType != null && AD_TYPE_CPUV.equals(adType)) {
//            try {
//                Thread.sleep(5);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
        }
    }
}
