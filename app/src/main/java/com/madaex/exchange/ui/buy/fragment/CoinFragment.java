package com.madaex.exchange.ui.buy.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseNetLazyFragment;
import com.madaex.exchange.common.util.SPUtils;
import com.madaex.exchange.ui.buy.bean.CoinList;
import com.madaex.exchange.ui.buy.bean.Event;
import com.madaex.exchange.ui.buy.contract.CoinLister;
import com.madaex.exchange.ui.constant.Constants;
import com.madaex.exchange.ui.market.bean.Position;
import com.madaex.exchange.view.GlideImgManager;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 项目：  madaexchange
 * 类名：  CoinFragment.java
 * 时间：  2018/9/5 14:52
 * 描述：  ${TODO}
 */

public class CoinFragment extends BaseNetLazyFragment {
    BaseQuickAdapter mAdapter;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    private CoinLister mCoinLister;

    public CoinLister getCoinLister() {
        return mCoinLister;
    }

    public void setCoinLister(CoinLister coinLister) {
        mCoinLister = coinLister;
    }

    public static CoinFragment newInstance(int parentposition,int type,ArrayList<CoinList.DataBean.ListBean> string) {
        CoinFragment fragment = null;
        if (fragment == null) {
            fragment = new CoinFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putInt("parentposition", parentposition);
        bundle.putInt("type", type);
        bundle.putParcelableArrayList(Constants.ARGS, string);
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

        ArrayList<CoinList.DataBean.ListBean> list = getArguments().getParcelableArrayList(Constants.ARGS);
         int type = getArguments().getInt("type",0);
        int parentposition = getArguments().getInt("parentposition",0);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(mContext,3);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerview.setLayoutManager(linearLayoutManager);

        mAdapter = new BaseQuickAdapter<CoinList.DataBean.ListBean, BaseViewHolder>(R.layout.item_coin, list) {
            @Override
            protected void convert(final BaseViewHolder helper, CoinList.DataBean.ListBean item) {
                helper.setText(R.id.coinname, item.getName());
                GlideImgManager.loadImage(mContext,item.getImg(), (ImageView) helper.getView(R.id.coinimg));
            }
        };
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                CoinList.DataBean.ListBean listBean = (CoinList.DataBean.ListBean) adapter.getItem(position);
                Event event = new Event();
                event.setMsg(listBean.getName());
                event.setCode(type);

                Position bean  = new Position();
                bean.setParent(parentposition);
                bean.setChild(position);
                event.setData(bean);
                EventBus.getDefault().post(event);
                mCoinLister.inputInforCompleted("");
                SPUtils.putString(Constants.ONE_XNB,listBean.getName().split("/")[0]);
                SPUtils.putString(Constants.TWO_XNB,listBean.getName().split("/")[1]);
            }
        });
        mRecyclerview.setAdapter(mAdapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_coin;
    }

    @Override
    public void initInjector() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initDatas() {

    }
}
