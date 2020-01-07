package com.madaex.exchange.ui.market.activity;

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
import com.madaex.exchange.ui.market.bean.EntrustDetail;
import com.madaex.exchange.ui.market.bean.EntrustList;
import com.madaex.exchange.ui.market.contract.EntrustContract;
import com.madaex.exchange.ui.market.presenter.EntrustPresenter;

import java.math.BigDecimal;
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

public class EntrustDetailActivity extends BaseNetActivity<EntrustPresenter> implements EntrustContract.View {
    @BindView(R.id.quantitynumber)
    TextView mQuantitynumber;
    @BindView(R.id.entrastPrice)
    TextView mEntrastPrice;
    @BindView(R.id.transnumber)
    TextView mTransnumber;
    BaseQuickAdapter mAdapter;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.deal)
    TextView mDeal;
    @BindView(R.id.deal_type)
    TextView mDealType;
    @BindView(R.id.coinname)
    TextView mCoinname;
    @BindView(R.id.price)
    TextView mPrice;
    @BindView(R.id.coinprice)
    TextView mCoinprice;
    @BindView(R.id.mum)
    TextView mMum;
    @BindView(R.id.time)
    TextView mTime;
    @BindView(R.id.img_delete)
    TextView mImgDelete;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_entrustdetail;
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
    private String market_type = "0";

    @Override
    protected void initDatas() {
        market_type = getIntent().getStringExtra("market_type");
        EntrustList.DataBean bean = getIntent().getParcelableExtra("bean");
        type = getIntent().getIntExtra("type", 1);
        mQuantitynumber.setText(bean.getOne_xnb() + bean.getNum());
        mEntrastPrice.setText(bean.getTwo_xnb() + bean.getPrice());
        mTransnumber.setText(bean.getOne_xnb() + bean.getDeal());
        getDataDetail();


        mImgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (market_type.equals("0")) {
                    TreeMap params = new TreeMap<>();
                    params.put("act", ConstantUrl.TRADE_REVOKE);
                    params.put("one_xnb", bean.getOne_xnb());
                    params.put("two_xnb", bean.getTwo_xnb());
                    params.put("id", bean.getId());
                    mPresenter.delete(DataUtil.sign(params));
                } else {
                    TreeMap params = new TreeMap<>();
                    params.put("act", ConstantUrl.Contract_REVOKE);
                    params.put("one_xnb", bean.getOne_xnb());
                    params.put("two_xnb", bean.getTwo_xnb());
                    params.put("id", bean.getId());
                    mPresenter.delete(DataUtil.sign(params));
                }
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerview.setLayoutManager(linearLayoutManager);

        mAdapter = new BaseQuickAdapter<EntrustDetail.DataBean.DealBean, BaseViewHolder>(R.layout.item_entrustdetail) {
            @Override
            protected void convert(BaseViewHolder helper, final EntrustDetail.DataBean.DealBean item) {
                helper.setText(R.id.price, bean.getTwo_xnb() + new BigDecimal(String.valueOf(item.getPrice())).stripTrailingZeros().toPlainString()).setText(R.id.num, bean.getOne_xnb() + new BigDecimal(String.valueOf(item.getNum())).stripTrailingZeros().toPlainString())
                        .setText(R.id.mum, bean.getTwo_xnb() + new BigDecimal(String.valueOf(item.getPrice())).stripTrailingZeros().toPlainString());
                if (type == 1) {
                    helper.setText(R.id.type, R.string.buy).setText(R.id.deal_type, R.string.buy);
                    helper.setTextColor(R.id.price, getResources().getColor(R.color.common_red)).setTextColor(R.id.num, getResources().getColor(R.color.common_red))
                            .setTextColor(R.id.deal, getResources().getColor(R.color.common_red)).setBackgroundRes(R.id.deal_type, R.drawable.rect_rounded_red);
                } else if (type == 2) {
                    helper.setText(R.id.type, R.string.seller).setText(R.id.deal_type, R.string.seller);
                    helper.setTextColor(R.id.price, getResources().getColor(R.color.common_green)).setTextColor(R.id.num, getResources().getColor(R.color.common_green))
                            .setTextColor(R.id.deal, getResources().getColor(R.color.common_green)).setBackgroundRes(R.id.deal_type, R.drawable.rect_rounded_arc);
                }
                helper.setVisible(R.id.ll_history, false).setVisible(R.id.img_delete, true).setText(R.id.deal, new BigDecimal(String.valueOf(item.getMum())).stripTrailingZeros().toPlainString());
            }
        };
        mRecyclerview.setAdapter(mAdapter);

        if (type == 1) {
            mDealType.setText(R.string.buy);
            mPrice.setTextColor(getResources().getColor(R.color.common_red));
            mDeal.setTextColor(getResources().getColor(R.color.common_red));
            mDealType.setBackgroundResource(R.drawable.rect_rounded_red);
        } else if (type == 2) {
            mDealType.setText(R.string.seller);
            mPrice.setTextColor(getResources().getColor(R.color.common_green));
            mDeal.setTextColor(getResources().getColor(R.color.common_green));
            mDealType.setBackgroundResource(R.drawable.rect_rounded_arc);
        }

        mPrice.setText(bean.getTwo_xnb() + new BigDecimal(String.valueOf(bean.getPrice())).stripTrailingZeros().toPlainString());
        mMum.setText(bean.getTwo_xnb() + "--");
    }

    private void getDataDetail() {
        EntrustList.DataBean bean = getIntent().getParcelableExtra("bean");
        if (market_type.equals("0")) {
            TreeMap params = new TreeMap<>();
            params.put("act", ConstantUrl.TRADE_CURRENT_ENTRUST_DETAIL);
            params.put("id", bean.getId());
            mPresenter.getDataDetail(DataUtil.sign(params));

        } else {
            TreeMap params = new TreeMap<>();
            params.put("act", ConstantUrl.Contract_CURRENT_ENTRUST_DETAIL);
            params.put("id", bean.getId());
            mPresenter.getDataDetail(DataUtil.sign(params));
        }
    }

    @Override
    public void requestSuccess(EntrustList bean) {

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
    public void requestEntrustDetailSuccess(EntrustDetail bean) {
        mAdapter.setNewData(bean.getData().getDeal());
        mDeal.setText(new BigDecimal(String.valueOf(bean.getData().getNo_deal())).stripTrailingZeros().toPlainString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


}
