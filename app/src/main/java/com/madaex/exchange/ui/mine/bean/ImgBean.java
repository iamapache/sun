package com.madaex.exchange.ui.mine.bean;

/**
 * 项目：  sun
 * 类名：  ImgBean.java
 * 时间：  2020/6/13 11:19
 * 描述：
 */
public class ImgBean {
    private int type;
    private String file;

    public ImgBean(int type, String file) {
        this.type = type;
        this.file = file;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
