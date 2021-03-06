package com.madaex.exchange.common.net;

import com.madaex.exchange.BuildConfig;

/**
 * 项目：  frame
 * 类名：  Constant.java
 * 时间：  2018/6/28 14:46
 * 描述：  ${TODO}
 */
public class Constant {

    public static final String HTTP = BuildConfig.DEBUG ? "https://www.topa.vip/" : "https://www.topa.vip/";//http://192.168.2.117/appapi/

    public static final String Websocket = BuildConfig.DEBUG ? "ws://alsc.uoou.net:8888" : "ws://sock.vcoin.be:8888";

    public static final String JAVA_HTTP = "http://123.58.34.198/";

    public static final String APIKEY = "token";

    /**
     * 请求状态
     */
    public static final int RESPONSE_ERROR = 0;
    //失败
    public static final int RESPONSE_SUCCESS = 1;
    //成功
    public static final int RESPONSE_UN_LOGIN = 99;
    //未登录
    public static final int RESPONSE_EXCEPTION = -1;
    //访问异常
    public static final int RESPONSE_FREEZE = 102;
    //账户冻结
    public static final int RESPONSE_LINK_SUCCESS = 200;
    //链接成功
    public static final int RESPONSE_LINK_ERROE = 400;
    //链接失败

    public static final String APP_APIS = "appapi";

    public static final String LOGIN = "market/getMarket";
    public static final String IMG_BASE_URL = BuildConfig.DEBUG ? "https://www.topa.vip/" : "https://www.topa.vip/";
}
