package com.madaex.exchange.ui.finance.c2c.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseNetActivity;
import com.madaex.exchange.common.util.DataUtil;
import com.madaex.exchange.common.util.ToastUtils;
import com.madaex.exchange.ui.constant.ConstantUrl;
import com.madaex.exchange.ui.finance.c2c.bean.EntractDetail;
import com.madaex.exchange.ui.finance.c2c.contract.C2CEntrustContract;
import com.madaex.exchange.ui.finance.c2c.presenter.C2CEntrustPresenter;

import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 项目：  madaexchange
 * 类名：  EntrustDetailActivity.java
 * 时间：  2018/10/18 10:44
 * 描述：  ${TODO}
 */

public class C2CEntrustDetailActivity extends BaseNetActivity<C2CEntrustPresenter> implements C2CEntrustContract.View {
    @BindView(R.id.quantitynumber)
    TextView mQuantitynumber;
    @BindView(R.id.entrastPrice)
    TextView mEntrastPrice;
    @BindView(R.id.transnumber)
    TextView mTransnumber;
    BaseQuickAdapter mAdapter;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_c2centrustdetail;
    }

    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initView() {

    }

    @OnClick({R.id.toolbar_left_btn_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_btn_ll:
                finish();
                break;

        }
    }

    int type = 1;

    @Override
    protected void initDatas() {

        getDataDetail();



        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerview.setLayoutManager(linearLayoutManager);

        mAdapter = new BaseQuickAdapter<EntractDetail.DataBean.OrderBean, BaseViewHolder>(R.layout.item_entrustdetail2) {
            @Override
            protected void convert(BaseViewHolder helper, final EntractDetail.DataBean.OrderBean item) {
                helper.setText(R.id.coinname, item.getCoin_ename()+ item.getNum()).setText(R.id.price, getString(R.string.transaction)+item.getPrice() )
                        .setText(R.id.coinprice, item.getTotal()) .setText(R.id.mum, item.getAddtime());
            }
        };
        mRecyclerview.setAdapter(mAdapter);

    }

    private void getDataDetail() {
        String bean = getIntent().getStringExtra("id");
        TreeMap params = new TreeMap<>();
        params.put("act", ConstantUrl.FIAT_MY_ENTRUST_DETAIL);
        params.put("goods_id", bean);
        mPresenter.getDataDetail(DataUtil.sign(params));
    }


    @Override
    public void nodata(String msg) {

    }

    @Override
    public void requestError(String msg) {

    }

    @Override
    public void deleteSuccess(String msg) {
        ToastUtils.showToast(msg);
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void deleteError(String msg) {

    }

    @Override
    public void requestEntrustDetailSuccess(EntractDetail bean) {
        mEntrastPrice .setText(bean.getData().getGoods().getCoin_ename() + bean.getData().getGoods().getPrice());
        mQuantitynumber.setText(bean.getData().getGoods().getNum());
        mTransnumber.setText(bean.getData().getGoods().getDeal_num());
        mAdapter.setNewData(bean.getData().getOrder());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


}
