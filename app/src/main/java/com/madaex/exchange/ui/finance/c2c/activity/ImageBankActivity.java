package com.madaex.exchange.ui.finance.c2c.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseActivity;
import com.madaex.exchange.common.util.ToastUtils;
import com.madaex.exchange.ui.finance.c2c.bean.EntrusTwo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 项目：  sun
 * 类名：  ImageActivity.java
 * 时间：  2019/5/15 16:36
 * 描述：  ${TODO}
 */
public class ImageBankActivity extends BaseActivity {
    @BindView(R.id.bankname)
    TextView mBankname;
    @BindView(R.id.name)
    TextView mName;
    @BindView(R.id.user)
    TextView mUser;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bankimage;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initDatas() {
        EntrusTwo.DataBean.GoodsListBean.PaymentListBean paymentListBean = getIntent().getParcelableExtra("bean");
        mBankname.setText(paymentListBean.getBank());
        mUser.setText(paymentListBean.getAccount());
        mName.setText(paymentListBean.getName());
    }

    @OnClick({R.id.toolbar_left_btn_ll, R.id.copyname, R.id.copyuser})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.copyname:
                ClipboardManager clipboard = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText(null, mName.getText().toString());
                clipboard.setPrimaryClip(clipData);
                ToastUtils.showToast(getString(R.string.copyaddress));
                break;
            case R.id.toolbar_left_btn_ll:
                finish();
                break;
            case R.id.copyuser:
                ClipboardManager clipboard2 = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData2 = ClipData.newPlainText(null, mUser.getText().toString());
                clipboard2.setPrimaryClip(clipData2);
                ToastUtils.showToast(getString(R.string.copyaddress));
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
