package com.madaex.exchange.ui.finance.vote.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseNetActivity;
import com.madaex.exchange.common.util.DataUtil;
import com.madaex.exchange.common.util.EmptyUtils;
import com.madaex.exchange.common.util.ToastUtils;
import com.madaex.exchange.ui.constant.ConstantUrl;
import com.madaex.exchange.ui.finance.bean.Asset;
import com.madaex.exchange.ui.finance.vote.bean.NOWVOTE;
import com.madaex.exchange.ui.finance.vote.bean.VoteCoin;
import com.madaex.exchange.ui.finance.vote.bean.issue;
import com.madaex.exchange.ui.finance.vote.contract.VoteCoinContract;
import com.madaex.exchange.ui.finance.vote.presenter.VoteCoinPresenter;
import com.madaex.exchange.view.GlideImgManager;

import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 项目：  madaexchange
 * 类名：  RankingDetailActivity.java
 * 时间：  2018/9/3 9:57
 * 描述：  ${TODO}
 */

public class RankingDetailActivity extends BaseNetActivity<VoteCoinPresenter> implements VoteCoinContract.View {
    @BindView(R.id.img_bank)
    ImageView mImgBank;
    @BindView(R.id.coinname)
    TextView mCoinname;
    @BindView(R.id.number)
    TextView mNumber;
    @BindView(R.id.total)
    TextView mTotal;
    @BindView(R.id.price)
    TextView mPrice;
    @BindView(R.id.name)
    TextView mName;
    @BindView(R.id.my_num)
    EditText mMyNum;
    @BindView(R.id.submit)
    Button mSubmit;
    @BindView(R.id.toolbar_title_tv)
    TextView mToolbarTitleTv;
    @BindView(R.id.background)
    LinearLayout mBackground;
    @BindView(R.id.ll_content1)
    LinearLayout mLlContent1;
    @BindView(R.id.count)
    TextView mCount;
    @BindView(R.id.castcount)
    TextView mCastcount;
    @BindView(R.id.twoprice)
    TextView mTwoprice;
    @BindView(R.id.ll_content2)
    LinearLayout mLlContent2;
    @BindView(R.id.textcotent)
    TextView mTextcotent;
    @BindView(R.id.nameone)
    TextView mNameone;
    @BindView(R.id.nametwo)
    TextView mNametwo;
    @BindView(R.id.asset)
    TextView mAsset;
    private VoteCoin.DataBean mResultBean;
    int mType;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_rankingdetail;
    }

    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initView() {

    }

    private void getData() {
//        TreeMap params = new TreeMap<>();
//        params.put("act", ConstantUrl.FINANCE_C2C_VIEW);
//        mPresenter.getGRC(DataUtil.sign(params));
        TreeMap params2 = new TreeMap<>();

        if (mType == 1) {
            params2.put("act", ConstantUrl.VOTE_NOWVOTE);
            params2.put("coinname", mResultBean.getCoinname());
            mPresenter.NOWVOTE(DataUtil.sign(params2));
        } else {
            params2.put("act", ConstantUrl.VOTE_GO_ISSUE);
            params2.put("id", mResultBean.getId());
            mPresenter.go_issue(DataUtil.sign(params2));
        }
    }

    @Override
    protected void initDatas() {
        mType = getIntent().getIntExtra("id", 1);
        mResultBean = getIntent().getParcelableExtra("bean");
        getData();

        if (mType == 1) {
            mToolbarTitleTv.setText(getString(R.string.Confirmationvote));
            mSubmit.setText(getString(R.string.Confirmationvote));
            mBackground.setBackgroundResource(R.mipmap.bg_votecoin);
            mLlContent1.setVisibility(View.GONE);
            mLlContent2.setVisibility(View.GONE);
            mName.setText("USDT");
            mMyNum.setHint(getString(R.string.Numbervoting));
            mTextcotent.setText(R.string.castnumber);
        } else {
            mToolbarTitleTv.setText(getString(R.string.oksubmit));
            mSubmit.setText(getString(R.string.oksubmit));
            mBackground.setBackgroundResource(R.mipmap.bg_rank);
            mName.setText(mResultBean.getCoinname());
            mTextcotent.setText(R.string.casticcprice);
            mMyNum.setHint(R.string.numberapplication);
            mTwoprice.setText(mResultBean.getIssue_price());
        }

        mCoinname.setText(mResultBean.getCoinname());

        mPrice.setText(mResultBean.getAssumnum());
        GlideImgManager.loadImage(mContext, mResultBean.getImg(), mImgBank);

        if (mType == 1) {
            mTotal.setText(getString(R.string.totaldrop) + mResultBean.getTotal() + mResultBean.getCoinname());
            mNumber.setText(mResultBean.getNumber() + getString(R.string.ticket));
        } else {
            mTotal.setText(getString(R.string.Has) + mResultBean.getPercent());
            mNumber.setText(mResultBean.getDeal() + getString(R.string.ticket));
        }
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.submit)
    public void onViewClicked() {
        validate();
    }

    private void validate() {
        if (TextUtils.isEmpty(mMyNum.getText().toString())) {
            ToastUtils.showToast(getString(R.string.entrureturnnumber));
            return;
        }
        TreeMap params = new TreeMap<>();
        if (mType == 1) {
            params.put("act", ConstantUrl.MARKET_CONFIRM_VOTE);
            params.put("number", mMyNum.getText().toString().trim());
            params.put("coinname", mResultBean.getCoinname());
            mPresenter.getData2(DataUtil.sign(params));
        } else {
            params.put("act", ConstantUrl.MARKET_CONFIRM_PUBLIC);
            params.put("num", mMyNum.getText().toString().trim());
            params.put("id", mResultBean.getId());
            mPresenter.getData2(DataUtil.sign(params));
        }
    }

    @Override
    public void requestSuccess(List<VoteCoin.DataBean> commonBean) {

    }

    @Override
    public void nodata(String msg) {
        ToastUtils.showToast(getString(R.string.loginexpired));
    }

    @Override
    public void requestError(String msg) {
        ToastUtils.showToast(msg);
        Intent intent =getIntent();
        intent.setClass(mContext, SuccessActivity.class);
        intent.putExtra("status", 0);
        if (mType == 1) {
            intent.putExtra("msg", msg);
        }else {
            intent.putExtra("msg", msg);
        }
        startActivity(intent);
    }

    @Override
    public void requestSuccess(String msg) {
        ToastUtils.showToast(msg);
        Intent intent =getIntent();
        intent.setClass(mContext, SuccessActivity.class);
        intent.putExtra("status", 1);
        if (mType == 1) {
            intent.putExtra("msg", msg);
        }else {
            intent.putExtra("msg", msg);
        }
        startActivity(intent);
    }

    @Override
    public void sendViewSuccess(Asset commonBean) {
        if (EmptyUtils.isNotEmpty(commonBean) && EmptyUtils.isNotEmpty(commonBean.getData())) {
            mCount.setText(commonBean.getData().getAssets().getUsdt() + commonBean.getData().getExchange_coin());
        }
//        if (EmptyUtils.isNotEmpty(mResultBean)) {
//            if (mType == 1 && Double.valueOf(mResultBean.getAssumnum())!=0) {
//                mCastcount.setText(ArithUtil.round(ArithUtil.div(Double.valueOf(msg.getData().getAssets().getUsdt()), Double.valueOf(mResultBean.getAssumnum())), 2) + "");
//                mName.setText("USDT");
//            } else if (Double.valueOf(mResultBean.getIssue_price())!=0) {
//                mCastcount.setText(ArithUtil.round(ArithUtil.div(Double.valueOf(msg.getData().getAssets().getUsdt()), Double.valueOf(mResultBean.getIssue_price())), 2) + mResultBean.getCoinname());
//            }
//        }
    }

    @Override
    public void sendViewSuccess(issue commonBean) {
        mAsset.setText(getString(R.string.Available) + ":" );
        mCount.setText( commonBean.getData().getQc_number() + commonBean.getData().getBuycoin());
        mCastcount.setText(commonBean.getData().getQc_number() + commonBean.getData().getCoinname());
        mNameone.setText(commonBean.getData().getBuycoin());
        mNametwo.setText(commonBean.getData().getBuycoin());
    }

    @Override
    public void sendViewSuccess(NOWVOTE commonBean) {
        mAsset.setText(getString(R.string.Yourasset));
        mCount.setText( commonBean.getData().getVote_num() + commonBean.getData().getVote_unit());
        mCastcount.setText(commonBean.getData().getVote_num() + "");
        mNameone.setText(commonBean.getData().getVote_unit());
        mNametwo.setText(commonBean.getData().getVote_unit());
    }
}
