package org.example.producer;

import lombok.Data;
import lombok.ToString;

/**
 * Created by pengfei on 2018/11/27.
 */
@ToString
@Data
public class PushRequest {
    private String pre;
    private String utmUrl;

    private String utmCnt;
    private String utmCnt_a;
    private String utmCnt_b;
    private String utmCnt_c;
    private String utmCnt_d;
    private String utmCnt_e;

    private String scr;
    private String os;
    private String k;
    private String b;
    private String lver;
    private String logType;

    //
    private String evt;
    private String bdata;
    private String mx;
    private String my;

    //
    private String userId;
//    private String userRole;
    private String userCategory;
    private String institution;

    //
    private String districtCode;
    private String ipAddr;
    private String uuid;

    /**
     * 操作身份id
     */
    private String uid;
    private String pathname;
    private long createTime;
    private long clientTime;
    private String origin;
    private String url;
    private String queryString;
    private String title;
    /**
     * app 手机机型
     */
    private String phoneModel;
    /**
     * 发起方
     */
    private String launchId;
    /**
     * 运营商 电信 联通 移动
     */
    private String carrier;
    /**
     * 网络 4G 5G
     */
    private String network;
    /**
     * 经度
     */
    private String lng;
    /**
     * 纬度
     */
    private String lat;

    /**
     * 小程序app id
     */
    private String appId;

    /**
     * 小程序店铺 id
     */
    private String shopId;

    /**
     * 来源
     */
    private String source;

    /**
     * 埋点桩
     */
    private String stub;

    /**
     * app 手机埋点相关数据
     */
    private String tech;

    /**
     * 环境，比如zcy/shanxi/wuxi/shanghai/jiangxi/...
     */
    private String env;

    /**
     * 模块名称
     */
    private String moduleName;

    /**
     * 曝光时长
     */
    private Integer duringTime;

    /**
     * 事件编码
     */
    private String actionCode;

    /**
     * 曝光标识位
     */
    private String utmExpose;

    /**
     * 用户页面停留时长
     */
    private Integer tp;

    /**
     * 第三方信息
     */
    private String thirdPartyInfo;
}
