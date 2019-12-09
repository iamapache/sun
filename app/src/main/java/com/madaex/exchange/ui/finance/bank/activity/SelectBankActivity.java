package com.madaex.exchange.ui.finance.bank.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseNetActivity;
import com.madaex.exchange.common.util.DataUtil;
import com.madaex.exchange.common.util.ToastUtils;
import com.madaex.exchange.common.view.RecycleViewDivider;
import com.madaex.exchange.ui.constant.ConstantUrl;
import com.madaex.exchange.ui.finance.bank.contract.SelectBank;
import com.madaex.exchange.ui.finance.bank.contract.SelectBankContract;
import com.madaex.exchange.ui.finance.bank.presenter.SelectBankPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 项目：  madaexchange
 * 类名：  SelectBankActivity.java
 * 时间：  2018/8/29 16:27
 * 描述：  ${TODO}
 */

public class SelectBankActivity extends BaseNetActivity<SelectBankPresenter> implements SelectBankContract.View {

    private int[] mTabImgs = new int[]{R.mipmap.icon_bank_zx, R.mipmap.icon_bank_gd, R.mipmap.icon_bank_pf,
            R.mipmap.icon_bank_ms, R.mipmap.icon_bank_xye, R.mipmap.icon_bank_pa,
            R.mipmap.icon_bank_zx, R.mipmap.icon_bank_zx, R.mipmap.icon_bank_zx,
            R.mipmap.icon_bank_js, R.mipmap.icon_bank_ny, R.mipmap.icon_bank_zs,
            R.mipmap.icon_bank_zg, R.mipmap.icon_bank_jt, R.mipmap.icon_bank_gdfz};
    private List<SelectBank.DataBean> mSelectBankList = new ArrayList<>();
    @BindView(R.id.history_recycler)
    RecyclerView mHistoryRecycler;
    @BindView(R.id.all_recycler)
    RecyclerView mAllRecycler;
    BaseQuickAdapter mAdapter;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_selectbank;
    }

    @Override
    protected void initInjector() {
getActivityComponent().inject(this);
    }

    @Override
    protected void initView() {
//        mSelectBankList.add(new SelectBank("中信银行",mTabImgs[0]));
//        mSelectBankList.add(new SelectBank("光大银行",mTabImgs[1]));
//        mSelectBankList.add(new SelectBank("浦发银行",mTabImgs[2]));
//        mSelectBankList.add(new SelectBank("中国民生银行",mTabImgs[3]));
//        mSelectBankList.add(new SelectBank("兴业银行",mTabImgs[4]));
//        mSelectBankList.add(new SelectBank("平安银行",mTabImgs[5]));
//        mSelectBankList.add(new SelectBank("华夏银行",mTabImgs[6]));
//        mSelectBankList.add(new SelectBank("中国工商银行",mTabImgs[7]));
//        mSelectBankList.add(new SelectBank("中国邮政银行",mTabImgs[8]));
//        mSelectBankList.add(new SelectBank("中国建设银行",mTabImgs[9]));
//        mSelectBankList.add(new SelectBank("中国农业银行",mTabImgs[10]));
//        mSelectBankList.add(new SelectBank("中国招商银行",mTabImgs[11]));
//        mSelectBankList.add(new SelectBank("中国银行",mTabImgs[12]));
//        mSelectBankList.add(new SelectBank("交通银行",mTabImgs[13]));
//        mSelectBankList.add(new SelectBank("广东发展银行",mTabImgs[14]));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mAllRecycler.setLayoutManager(linearLayoutManager);

        mAdapter = new BaseQuickAdapter<SelectBank.DataBean, BaseViewHolder>(R.layout.item_selectbank, mSelectBankList) {
            @Override
            protected void convert(BaseViewHolder helper, SelectBank.DataBean item) {
                helper.setText(R.id.tv_bankname, item.getBank_name());
            }
        };
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                SelectBank.DataBean resultBean  = (SelectBank.DataBean) adapter.getItem(position);
                Intent data = new Intent();
                data.putExtra("data",resultBean);
                setResult(RESULT_OK, data);
                finish();
            }
        });
        mAllRecycler.addItemDecoration(new RecycleViewDivider(
                mContext, LinearLayoutManager.HORIZONTAL, 2, getResources().getColor(R.color.backGroundColor)));
        mAllRecycler.setAdapter(mAdapter);
    }

    @Override
    protected void initDatas() {
        TreeMap params = new TreeMap<>();
        params.put("act", ConstantUrl.FINANCE_BANK_LIST);
        mPresenter.getData(DataUtil.sign(params));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
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
    public void requestSuccess(List<SelectBank.DataBean> bean) {
        mAdapter.setNewData(bean);
    }

    @Override
    public void requestError(String msg) {

    }

    @Override
    public void nodata(String msg) {
        ToastUtils.showToast(getString(R.string.loginexpired));
    }
}
