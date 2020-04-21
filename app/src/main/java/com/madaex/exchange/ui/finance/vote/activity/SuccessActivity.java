package com.madaex.exchange.ui.finance.vote.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 项目：  madaexchange
 * 类名：  SuccessActivity.java
 * 时间：  2018/10/23 14:47
 * 描述：  ${TODO}
 */

public class SuccessActivity extends BaseActivity {
    @BindView(R.id.img)
    ImageView mImg;
    @BindView(R.id.recharge)
    TextView mGoto;
    @BindView(R.id.ll_content)
    LinearLayout mLlContent;
    @BindView(R.id.msg)
    TextView mMsg;
    @BindView(R.id.text)
    TextView mText;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_success;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initDatas() {
        int status = getIntent().getIntExtra("status", 1);
        int mType = getIntent().getIntExtra("type", 1);
        String msg = getIntent().getStringExtra("msg");
        mMsg.setText(msg);
        mMsg.setText(msg);
        if (status == 0) {
            mImg.setImageResource(R.mipmap.pay_error);
//            mLlContent.setVisibility(View.VISIBLE);
//            mGoto.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    startActivity(new Intent(mContext, C2CTransationActivity.class));
//                }
//            });
        } else {

        }
//        if (mType == 1) {
//            mText.setText(R.string.faily);
//            mGoto.setText(R.string.tips);
//        } else {
//            mText.setText(R.string.noGRC);
//            mGoto.setText(R.string.recharge);
//        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

}
