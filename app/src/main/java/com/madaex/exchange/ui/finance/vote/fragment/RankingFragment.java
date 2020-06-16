package com.madaex.exchange.ui.finance.vote.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseNetLazyFragment;
import com.madaex.exchange.common.net.Constant;
import com.madaex.exchange.common.util.DataUtil;
import com.madaex.exchange.common.util.ToastUtils;
import com.madaex.exchange.common.view.RecycleViewDivider;
import com.madaex.exchange.ui.constant.ConstantUrl;
import com.madaex.exchange.ui.constant.Constants;
import com.madaex.exchange.ui.finance.bean.Asset;
import com.madaex.exchange.ui.finance.vote.activity.RankingDetailActivity;
import com.madaex.exchange.ui.finance.vote.bean.NOWVOTE;
import com.madaex.exchange.ui.finance.vote.bean.VoteCoin;
import com.madaex.exchange.ui.finance.vote.bean.issue;
import com.madaex.exchange.ui.finance.vote.contract.VoteCoinContract;
import com.madaex.exchange.ui.finance.vote.presenter.VoteCoinPresenter;
import com.madaex.exchange.ui.mine.activity.LinkWebViewActivity;
import com.madaex.exchange.view.GlideImgManager;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;

/**
 * 项目：  madaexchange
 * 类名：  RankingFragment.java
 * 时间：  2018/9/3 9:45
 * 描述：  ${TODO}
 */

public class RankingFragment extends BaseNetLazyFragment<VoteCoinPresenter> implements VoteCoinContract.View {
    ArrayList<VoteCoin.DataBean> mCategoryDatas = new ArrayList<>();
    BaseQuickAdapter mAdapter;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    private int mType;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_ranking;
    }

    public static RankingFragment newInstance(int string, int type) {
        RankingFragment fragment = null;
        if (fragment == null) {
            fragment = new RankingFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.ARGS, string);
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void initInjector() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        isVisible = false;
        isPrepared = false;
        mType = getArguments().getInt("type", 1);
        int mEntrusttype = getArguments().getInt(Constants.ARGS, 0);
        if (mType == 1) {
            if (mEntrusttype == 0) {
                TreeMap params = new TreeMap<>();
                params.put("act", ConstantUrl.MARKET_VOTE_LIST);
                mPresenter.getData(DataUtil.sign(params));
            } else {
                TreeMap params = new TreeMap<>();
                params.put("act", ConstantUrl.MARKET_MY_VOTE_LIST);
                mPresenter.getData(DataUtil.sign(params));
            }
        } else {
            if (mEntrusttype == 0) {
                TreeMap params = new TreeMap<>();
                params.put("act", ConstantUrl.MARKET_PUBLIC_OFFER_LIST);
                mPresenter.getData(DataUtil.sign(params));
            } else {
                TreeMap params = new TreeMap<>();
                params.put("act", ConstantUrl.MARKET_MY_PARTAKE_PUBLIC);
                mPresenter.getData(DataUtil.sign(params));
            }
        }

    }

    @Override
    protected void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerview.setLayoutManager(linearLayoutManager);


        int mEntrusttype = getArguments().getInt(Constants.ARGS, 0);
        if (mEntrusttype == 0) {
            mAdapter = new BaseQuickAdapter<VoteCoin.DataBean, BaseViewHolder>(R.layout.item_vote, mCategoryDatas) {
                @Override
                protected void convert(final BaseViewHolder helper, final VoteCoin.DataBean item) {
                    helper.setText(R.id.i, item.getI() + "").setText(R.id.coinname, item.getCoinname());
                    if (mType == 1) {
                        helper.setText(R.id.total, getString(R.string.totaldrop) + item.getTotal() );
                        helper.setText(R.id.number, item.getNumber() + getString(R.string.ticket));
                    } else {
                        helper.setText(R.id.total, getString(R.string.Has) + item.getPercent());
                        helper.setText(R.id.number, getString(R.string.hasraise) + item.getDeal() );
                    }
                    GlideImgManager.loadImage(mContext, item.getImg(), (ImageView) helper.getView(R.id.img_bank));
                    helper.setText(R.id.votegoto, item.getStatus());
                    if (item.getStatus().equals(getString(R.string.Tovote)) || item.getStatus().equals(getString(R.string.goconfirm)) || item.getStatus().equals(getString(R.string.Immediateapplication))) {
                        helper.setBackgroundRes(R.id.votegoto, R.mipmap.bg_login);
                    } else {
                        helper.setBackgroundRes(R.id.votegoto, R.mipmap.bg_login);
                    }
                    helper.getView(R.id.votedetail).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent2 = new Intent(mContext, LinkWebViewActivity.class);
                            intent2.putExtra(LinkWebViewActivity.WEB_TITLE, getString(R.string.projectdetails));
                            intent2.putExtra(LinkWebViewActivity.WEB_status, 1);
                            if (mType == 1) {
                                intent2.putExtra(LinkWebViewActivity.WEB_URL, Constant.HTTP + ConstantUrl.Prodetails + "?id=" + item.getId());
                            } else {
                                intent2.putExtra(LinkWebViewActivity.WEB_URL, Constant.HTTP + ConstantUrl.Issuedetails + "?id=" + item.getId());
                            }
                            startActivity(intent2);
                        }
                    });
                    helper.getView(R.id.votegoto).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (item.getStatus().equals(getString(R.string.Tovote)) || item.getStatus().equals(getString(R.string.goconfirm)) || item.getStatus().equals(getString(R.string.Immediateapplication))) {
                                Intent intent = getActivity().getIntent();
                                intent.setClass(mContext, RankingDetailActivity.class);
                                intent.putExtra("bean", item);
//                                intent.putExtra("type", mType);
                                startActivity(intent);
                            }

                        }
                    });
                }
            };
        } else {
            mAdapter = new BaseQuickAdapter<VoteCoin.DataBean, BaseViewHolder>(R.layout.item_vote2, mCategoryDatas) {
                @Override
                protected void convert(final BaseViewHolder helper, VoteCoin.DataBean item) {
                    helper.setText(R.id.i, item.getI() + "").setText(R.id.coinname, item.getCoinname())
                    ;
                    GlideImgManager.loadImage(mContext, item.getImg(), (ImageView) helper.getView(R.id.img_bank));
                    if (mType == 1) {
                        helper.setVisible(R.id.status, false).setText(R.id.total, getString(R.string.totalvote) + item.getMy_num()).setText(R.id.number, getString(R.string.cast) + item.getCoinname() + getString(R.string.numbershu) + item.getNumber())
                                .setText(R.id.button, getString(R.string.totaldrop) + item.getTotal() );
                    } else {
                        helper.setVisible(R.id.status, true);
                        helper.setText(R.id.status, item.getStatus());
                        helper.setText(R.id.button, getString(R.string.raisetotal) + item.getTotal() );
                        helper.setText(R.id.total, getString(R.string.Has) + item.getPercent());
                        helper.setText(R.id.number, getString(R.string.cast) + item.getCoinname() + getString(R.string.numbershu) + item.getNumber());
                    }
                }
            };
        }
        mRecyclerview.addItemDecoration(new RecycleViewDivider(
                mContext, LinearLayoutManager.HORIZONTAL, 15, getResources().getColor(R.color.backGroundColor)));

        mRecyclerview.setAdapter(mAdapter);
    }

    @Override
    protected void initDatas() {

    }

    @Override
    public void requestSuccess(List<VoteCoin.DataBean> commonBean) {
        mCategoryDatas.addAll(commonBean);
        mAdapter.setNewData(commonBean);
    }

    @Override
    public void nodata(String msg) {
        ToastUtils.showToast(getString(R.string.loginexpired));
    }

    @Override
    public void requestError(String msg) {

    }

    @Override
    public void requestSuccess(String commonBean) {

    }

    @Override
    public void sendViewSuccess(Asset msg) {

    }

    @Override
    public void sendViewSuccess(issue commonBean) {

    }

    @Override
    public void sendViewSuccess(NOWVOTE commonBean) {

    }

}
