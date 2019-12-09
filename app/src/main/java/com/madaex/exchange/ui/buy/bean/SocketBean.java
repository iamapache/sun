package com.madaex.exchange.ui.buy.bean;

import java.io.Serializable;

/**
 * 项目：  WebSocketDemo-master
 * 类名：  bean.java
 * 时间：  2018/9/27 15:37
 * 描述：  ${TODO}
 */

public class SocketBean implements Serializable {
    private String event;
    private String channel;

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}
