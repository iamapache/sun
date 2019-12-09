package com.madaex.exchange.ui.finance.activity;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 项目：  madaexchange
 * 类名：  FeeActivity.java
 * 时间：  2018/8/30 16:15
 * 描述：  ${TODO}
 */

public class FeeActivity extends BaseActivity {
    BaseQuickAdapter mAdapter;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_fee;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initView() {

        List<String>  stringList = getIntent().getStringArrayListExtra("bean");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerview.setLayoutManager(linearLayoutManager);

        mAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_fee, stringList) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                helper.setText(R.id.textview, item);
            }
        };
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("fee", (String) mAdapter.getItem(position));
                setResult(RESULT_OK,resultIntent);
                finish();
            }
        });
        mRecyclerview.setAdapter(mAdapter);
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
    protected void initDatas() {

    }
}
