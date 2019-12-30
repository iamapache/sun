package com.madaex.exchange.ui.market.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseNetActivity;
import com.madaex.exchange.common.util.DataUtil;
import com.madaex.exchange.ui.constant.ConstantUrl;
import com.madaex.exchange.ui.market.bean.Message;
import com.madaex.exchange.ui.market.contract.MessageContract;
import com.madaex.exchange.ui.market.presenter.MessagePresenter;

import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 项目：  sun
 * 类名：  MessageDetailActivity.java
 * 时间：  2019/12/30 16:19
 * 描述：
 */
public class MessageDetailActivity extends BaseNetActivity<MessagePresenter> implements MessageContract.View {

    @BindView(R.id.tv_login)
    TextView mTvLogin;
    private int CODE_REQUEST = 0x002;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_messagedetail;
    }

    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }
    @OnClick({R.id.toolbar_left_btn_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_btn_ll:
                finish();
                break;
        }
    }
    @Override
    protected void initView() {
        mTvLogin.setText(getIntent().getStringExtra("title"));
    }

    @Override
    protected void initDatas() {
        TreeMap params = new TreeMap<>();
        params.put("act", ConstantUrl.USER_ADD_READ_NEWS);
        mPresenter.read(DataUtil.sign(params));
    }

    @Override
    public void requestSuccess(List<Message.DataBean> bean) {

    }

    @Override
    public void requestError(String msg) {

    }

    @Override
    public void requestSuccess(String bean) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
