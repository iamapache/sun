package com.madaex.exchange.ui.mine.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseActivity;
import com.madaex.exchange.common.languagelib.LanguageType;
import com.madaex.exchange.common.languagelib.MultiLanguageUtil;
import com.madaex.exchange.common.util.JsonUitls;
import com.madaex.exchange.common.view.RecycleViewDivider;
import com.madaex.exchange.ui.mine.bean.Region;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;

/**
 * 项目：  madaexchange
 * 类名：  RegionActivity.java
 * 时间：  2018/8/29 14:13
 * 描述：  ${TODO}
 */

public class RegionActivity extends BaseActivity {

    private List<Region> mDatas = new ArrayList<>();
    private List<Region> mResultBeans = new ArrayList<>();
    private BaseQuickAdapter mAdapter;
    private EditText mClearEditText;
    private RecyclerView mRecyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_region;
    }

    @Override
    protected void initInjector() {

    }

    private int areastatus = 0;

    @Override
    protected void initView() {
        int selectedLanguage = MultiLanguageUtil.getInstance().getLanguageType();
        mClearEditText = findViewById(R.id.ed_selectreg);
        mRecyclerView = findViewById(R.id.recyclerview);
        if (getIntent().hasExtra("areastatus")) {
            areastatus = 1;
        }
        mAdapter = new BaseQuickAdapter<Region, BaseViewHolder>(R.layout.item_region, mDatas) {
            @Override
            protected void convert(BaseViewHolder helper, Region item) {

                helper.setText(R.id.tv_regionid, "+"+item.getPhoneCode());

                if (selectedLanguage == LanguageType.LANGUAGE_CHINESE_SIMPLIFIED) {
                    helper.setText(R.id.tv_regionname, item.getChineseName());
                } else if (selectedLanguage == LanguageType.LANGUAGE_EN) {
                    helper.setText(R.id.tv_regionname, item.getEnglishName());
                }else {
                    helper.setText(R.id.tv_regionname, item.getChineseName());
                }
//                if(areastatus==1){
//                    helper.setGone(R.id.tv_regionid,true);
//                }
            }
        };
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Region resultBean = (Region) adapter.getItem(position);
                Intent data = new Intent();
                if(areastatus==1) {
                    data.putExtra("data", resultBean.getPhoneCode());
                }else {

                    if (selectedLanguage == LanguageType.LANGUAGE_CHINESE_SIMPLIFIED) {
                        data.putExtra("data", resultBean.getChineseName());
                    } else if (selectedLanguage == LanguageType.LANGUAGE_EN) {
                        data.putExtra("data", resultBean.getEnglishName());
                    }else {
                        data.putExtra("data", resultBean.getChineseName());
                    }
                }
                setResult(RESULT_OK, data);
                finish();
            }
        });
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new RecycleViewDivider(
                mContext, LinearLayoutManager.HORIZONTAL, 1, getResources().getColor(R.color.backGroundColor)));
        mClearEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                findRegion(s.toString());
                Log.i("OkHTTP", "---->" + s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    private void findRegion(String keyword) {
        if (!TextUtils.isEmpty(keyword)) {
            mDatas.clear();
            mDatas.addAll(mResultBeans);

            List<Region> result = new ArrayList<>();
            for (Region resultBean : mDatas) {
                if (resultBean.getChineseName().contains(keyword) || resultBean.getPhoneCode().contains(keyword)) {
                    result.add(resultBean);
                }
            }
            if (result != null || result.size() > 0) {
                mDatas.clear();
                mDatas.addAll(result);
                mAdapter.notifyDataSetChanged();
            }
        } else {
            mDatas.clear();
            mDatas.addAll(mResultBeans);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void initDatas() {
        String json = JsonUitls.getJson(mContext, "area.json");
        Gson gson = new Gson();
        List<Region> regionList = gson.fromJson(json, new TypeToken<List<Region>>() {
        }.getType());
        mDatas.addAll(regionList);
        mResultBeans.addAll(regionList);
        mAdapter.notifyDataSetChanged();
    }

    @OnClick({R.id.toolbar_left_btn_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_btn_ll:
                finish();
                break;
        }
    }
}
