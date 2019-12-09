package com.madaex.exchange.ui.finance.pay.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseNetLazyFragment;
import com.madaex.exchange.common.util.DataUtil;
import com.madaex.exchange.common.util.DateUtil;
import com.madaex.exchange.common.util.SPUtils;
import com.madaex.exchange.common.util.ToastUtils;
import com.madaex.exchange.common.view.RecycleViewDivider;
import com.madaex.exchange.ui.constant.ConstantUrl;
import com.madaex.exchange.ui.constant.Constants;
import com.madaex.exchange.ui.finance.pay.bean.PlatRecord;
import com.madaex.exchange.ui.finance.pay.contract.PlatContrct;
import com.madaex.exchange.ui.finance.pay.presenter.PlatPresenter;

import java.util.TreeMap;

import butterknife.BindView;

/**
 * 项目：  madaexchange
 * 类名：  TransationBuyFragment.java
 * 时间：  2018/8/30 10:19
 * 描述：  ${TODO}
 */

public class PlatRecordFragment extends BaseNetLazyFragment<PlatPresenter> implements PlatContrct.View{

    BaseQuickAdapter mAdapter;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;

    public static PlatRecordFragment newInstance(int string) {
        PlatRecordFragment fragment = null;
        if (fragment == null) {
            fragment = new PlatRecordFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.ARGS, string);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        isVisible = false;
        isPrepared = false;

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_platrecord;
    }

    @Override
    public void initInjector() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerview.setLayoutManager(linearLayoutManager);

        mAdapter = new BaseQuickAdapter<PlatRecord.DataBean, BaseViewHolder>(R.layout.item_platrecord) {
            @Override
            protected void convert(BaseViewHolder helper, final PlatRecord.DataBean item) {
                helper.setVisible(R.id.name,true);
                if(type==1){
                    helper.setText(R.id.coinname,item.getName()).setText(R.id.price, getString(R.string.number)+item.getNum()) .setText(R.id.name, getString(R.string.coin)+item.getMove())
                            .setText(R.id.coinprice, item.getUserid()) .setText(R.id.mum,item.getAddtime());
                }else {
                    helper.setText(R.id.coinname,item.getName()).setText(R.id.price, getString(R.string.number)+item.getNum()).setText(R.id.name, getString(R.string.coin)+item.getMove())
                            .setText(R.id.coinprice, item.getUserid()) .setText(R.id.mum, item.getAddtime());
                }

            }
        };
        mRecyclerview.addItemDecoration(new RecycleViewDivider(
                mContext, LinearLayoutManager.HORIZONTAL, 1, getResources().getColor(R.color.white)));
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        mAdapter.setEmptyView(R.layout.view_empty_history, (ViewGroup) mRecyclerview.getParent());
        mRecyclerview.setAdapter(mAdapter);
    }
    int type =1;
    @Override
    protected void initDatas() {
       type =  getArguments().getInt(Constants.ARGS);
        TreeMap params = new TreeMap<>();
        params.put("act", ConstantUrl.TRADE_PLAT_RECORD);
        params.put("type", type+"");
        params.put("user_id", SPUtils.getString(Constants.TOKEN,""));
        mPresenter.getData(DataUtil.sign(params));
    }

    @Override
    public void requestSuccess(String msg) {
        ToastUtils.showToast(msg);
//        startActivity(new Intent(mContext, C2CTransationListActivity.class));
    }

    @Override
    public void requestSuccess(PlatRecord commonBean) {
        mAdapter.setNewData(commonBean.getData());
    }

    @Override
    public void requestError(String msg) {
        ToastUtils.showToast(msg);
    }


    private String one_xnb = "";

}
