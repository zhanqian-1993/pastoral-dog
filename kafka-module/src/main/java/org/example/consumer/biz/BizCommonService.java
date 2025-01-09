package org.example.consumer.biz;

import com.alibaba.fastjson.JSONObject;
import java.util.Objects;

public class BizCommonService {

    private static final String AD_TYPE_TAG = "adType";
    private String[] fields = new String[]{"pre","utmUrl","utmCnt","utmCnt_a","utmCnt_b","utmCnt_c","utmCnt_d","utmCnt_e",
            "scr","os","k","b","lver","logType","evt","bdata","mx","my","userId","userRole","userCategory","institution",
            "districtCode","ipAddr","uuid","uid","pathname","createTime","clientTime","origin","url",
            "queryString","title","phoneModel","launchId","carrier","network","lng","lat","appId","shopId","source","stub",
            "tech","env","moduleName","duringTime","actionCode","utmExpose","tp","thirdPartyInfo"};

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
        // mock send sls log
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendDeductMsg(String jsonStr, String adType) {
        // mock send TOPIC
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendCpuvAdMsg(String jsonStr, String adType) {
        // mock send TOPIC
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
