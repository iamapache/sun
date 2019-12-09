package com.madaex.exchange.common.base.activity;

import android.os.Bundle;
import android.view.View;

import io.reactivex.annotations.Nullable;

/**
 * 项目：  frame
 * 类名：  LazyLoadFragment.java
 * 时间：  2018/6/29 15:00
 * 描述：  ${TODO}
 */
public abstract class LazyLoadFragment extends BaseFragment  {

    protected boolean isVisible;
    // 标志位，标志已经初始化完成。
    protected boolean isPrepared;



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        isPrepared = true;
        lazyLoad();
    }



    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    protected void onVisible() {
        lazyLoad();
    }

    protected abstract void lazyLoad();

    protected void onInvisible() {
    }

//    @Override
//    protected void lazyLoad() {
//        if (isVisible && isVisible) {
//            //做加载数据的网络操作
//            isVisible = false;
//        }
//    }

}
