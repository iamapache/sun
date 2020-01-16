package com.madaex.exchange.ui.buy.adapter;

public class DepthDataBean {

    private float mPrice;
    private float mVolume;

    public DepthDataBean(float price, float volume) {
        mPrice = price;
        mVolume = volume;
    }

    public float getVolume() {
        return mVolume;
    }

    public void setVolume(float volume) {
        this.mVolume = volume;
    }

    public float getPrice() {
        return mPrice;
    }

    public void setPrice(float price) {
        this.mPrice = price;
    }
}
