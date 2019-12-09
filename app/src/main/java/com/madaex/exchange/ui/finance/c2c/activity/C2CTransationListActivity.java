package com.madaex.exchange.ui.finance.c2c.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseNetActivity;
import com.madaex.exchange.common.util.DataUtil;
import com.madaex.exchange.common.util.ToastUtils;
import com.madaex.exchange.ui.constant.ConstantUrl;
import com.madaex.exchange.ui.finance.c2c.bean.C2CTransation;
import com.madaex.exchange.ui.finance.c2c.bean.C2CTransationDetail;
import com.madaex.exchange.ui.finance.c2c.bean.TransationDetail;
import com.madaex.exchange.ui.finance.c2c.contract.C2CTransationContract;
import com.madaex.exchange.ui.finance.c2c.presenter.C2CTransationPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 项目：  madaexchange
 * 类名：  C2CTransationListActivity.java
 * 时间：  2018/8/30 12:24
 * 描述：  ${TODO}
 */

public class C2CTransationListActivity extends BaseNetActivity<C2CTransationPresenter> implements C2CTransationContract.View {
    ArrayList<C2CTransation.DataBean> testBeans = new ArrayList<>();
    BaseQuickAdapter mAdapter;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;


int position =1;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_c2ctransation_list;
    }

    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initView() {
        position=  getIntent().getIntExtra("position",0)+1;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerview.setLayoutManager(linearLayoutManager);

        mAdapter = new BaseQuickAdapter<C2CTransation.DataBean, BaseViewHolder>(R.layout.item_c2ctransation, testBeans) {
            @Override
            protected void convert(BaseViewHolder helper, C2CTransation.DataBean item) {
                helper.setText(R.id.transtime, item.getAddtime()).setText(R.id.transmoney,"￥ " + item.getNum())
                        .setText(R.id.transtcount,getString(R.string.number)+ item.getNumber()).setText(R.id.transstatus, item.getStatus());
                if(item.getState()==1){
                    helper.setText(R.id.transtype, getString(R.string.item_buy)+"GRC");
                }else {
                    helper.setText(R.id.transtype, getString(R.string.item_seller)+"GRC");
                }
                if(position==2){
                    helper.setGone(R.id.info,false).setGone(R.id.view,false);
                }
            }
        };
        if(position==1){
            mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    Intent intent = new Intent();
                    C2CTransation.DataBean dataBean = (C2CTransation.DataBean) adapter.getItem(position);
                    intent.setClass(mContext, C2CTransationDetailActivity.class);
                    intent.putExtra("id",dataBean.getId());
                    startActivity(intent);
                }
            });
        }

        mRecyclerview.setAdapter(mAdapter);
        View top = getLayoutInflater().inflate(R.layout.view_empty_data, (ViewGroup) mRecyclerview.getParent(), false);
        TextView textView = top.findViewById(R.id.nodata);
        textView.setText(R.string.notransdata);
        mAdapter.setEmptyView(top);


    }

    @Override
    protected void initDatas() {
        TreeMap params = new TreeMap<>();
        params.put("act", ConstantUrl.FINANCE_C2C_LIST);
        params.put("type", getIntent().getIntExtra("position",0)+1+"");
        mPresenter.getData(DataUtil.sign(params));

//        TreeMap params2 = new TreeMap<>();
//        params2.put("act", ConstantUrl.FINANCE_C2C_LIST);
//        params2.put("type", "2");
//        mPresenter.getData2(DataUtil.sign(params2));
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
    public void requestSuccess(List<C2CTransation.DataBean> commonBean) {
        mAdapter.setNewData(commonBean);
    }

    @Override
    public void requestSuccess2(List<C2CTransation.DataBean> commonBean) {
    }

    @Override
    public void requestSuccess3(TransationDetail commonBean) {

    }

    @Override
    public void requestError(String msg) {
    }

    @Override
    public void nodata(String msg) {
        ToastUtils.showToast(getString(R.string.loginexpired));
    }

    @Override
    public void requestSuccess(C2CTransationDetail commonBean) {

    }
}
