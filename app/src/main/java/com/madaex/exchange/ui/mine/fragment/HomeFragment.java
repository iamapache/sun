package com.madaex.exchange.ui.mine.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseNetLazyFragment;
import com.madaex.exchange.common.util.DataUtil;
import com.madaex.exchange.common.util.EmptyUtils;
import com.madaex.exchange.common.util.ToastUtils;
import com.madaex.exchange.ui.constant.ConstantUrl;
import com.madaex.exchange.ui.constant.Constants;
import com.madaex.exchange.ui.finance.activity.AssetActivity;
import com.madaex.exchange.ui.finance.address.activity.ScanActivity;
import com.madaex.exchange.ui.finance.contracts.activity.ContractActivity;
import com.madaex.exchange.ui.finance.vote.activity.VoteCoinActivity;
import com.madaex.exchange.ui.login.activity.RegisterActivity;
import com.madaex.exchange.ui.market.adapter.KineQuickAdapter;
import com.madaex.exchange.ui.market.bean.Home;
import com.madaex.exchange.ui.market.bean.HomeData;
import com.madaex.exchange.ui.mine.activity.AccountManagerActivity;
import com.madaex.exchange.ui.mine.activity.LinkWebViewActivity;
import com.madaex.exchange.ui.mine.adapter.GlideImageLoader;
import com.madaex.exchange.ui.mine.bean.BannerData;
import com.madaex.exchange.ui.mine.bean.HotCoin;
import com.madaex.exchange.ui.mine.bean.NoticeData;
import com.madaex.exchange.ui.mine.contract.PageHomeContract;
import com.madaex.exchange.ui.mine.presenter.PageHomePresenter;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.google.zxing.integration.android.IntentIntegrator.REQUEST_CODE;

/**
 * 项目：  exchange
 * 类名：  MineFragment.java
 * 时间：  2018/8/22 10:24
 * 描述：  ${TODO}
 */

public class HomeFragment extends BaseNetLazyFragment<PageHomePresenter> implements PageHomeContract.View, KineQuickAdapter.OnclikCallBack  {
    ArrayList<HotCoin.DataBean> testBeans = new ArrayList<>();
    BaseQuickAdapter mAdapter;
    Unbinder unbinder;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.notice)
    TextView mNotice;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.hotcoinview)
    RecyclerView mHotcoinview;


    ArrayList<Home> mHomeArrayList = new ArrayList<>();
//    BaseQuickAdapter mAdapter2;
    KineQuickAdapter mKineQuickAdapter;
    @BindView(R.id.img_scan)
    LinearLayout mImgScan;
    @BindView(R.id.img_popview)
    ImageView mImgPopview;

    public static HomeFragment newInstance(String string) {
        HomeFragment fragment = null;
        if (fragment == null) {
            fragment = new HomeFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putString(Constants.ARGS, string);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initInjector() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mHotcoinview.setLayoutManager(linearLayoutManager);

        mAdapter = new BaseQuickAdapter<HotCoin.DataBean, BaseViewHolder>(R.layout.item_hotcoin, testBeans) {
            @Override
            protected void convert(BaseViewHolder helper, final HotCoin.DataBean item) {
                helper.setText(R.id.name, item.getName()).setText(R.id.time, item.getNew_price1() + "")
                        .setText(R.id.address, item.getChange());
                if (item.getChange().contains("-")) {
                    helper.setTextColor(R.id.time, mContext.getResources().getColor(R.color.common_green));
                    helper.setTextColor(R.id.address, mContext.getResources().getColor(R.color.common_green));
                } else {
                    helper.setTextColor(R.id.time, mContext.getResources().getColor(R.color.common_red));
                    helper.setTextColor(R.id.address, mContext.getResources().getColor(R.color.common_red));
                }
            }
        };
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            }
        });
        mHotcoinview.setAdapter(mAdapter);


        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(mContext);
        linearLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerview.setLayoutManager(linearLayoutManager2);
        mKineQuickAdapter = new KineQuickAdapter(0);
        mRecyclerview.setAdapter(mKineQuickAdapter);

        mKineQuickAdapter.setCallBack(this);
//        mAdapter2 = new BaseQuickAdapter<Home, BaseViewHolder>(R.layout.item_getmartketlist, mHomeArrayList) {
//            @Override
//            protected void convert(BaseViewHolder helper, final Home item) {
//                helper.setText(R.id.endname, "/" + item.getExchangeType().toUpperCase());
//                helper.setVisible(R.id.endname, true);
//                helper.setText(R.id.coinname, item.getCurrentype().toUpperCase());
//                helper.setText(R.id.coinnumber, mContext.getString(R.string.vol) + " " + DataUtil.thousand(item.getVolumn(), mContext))
//                        .setText(R.id.coinprice, "" + item.getCurrentPrice()).
//                        setText(R.id.coinrmb, "￥" + item.getSellRmb()).setText(R.id.bili, item.getRiseRate());
//                GlideImgManager.loadImage(mContext, item.getCoinImageUrl(), (ImageView) helper.getView(R.id.img_bank));
//
//
//                if (item.getRiseRate().contains("-")) {
//                    helper.setText(R.id.bili, item.getRiseRate());
//                    helper.setBackgroundRes(R.id.bili, R.drawable.common_button_buleshape);
//                    helper.setTextColor(R.id.coinprice, mContext.getResources().getColor(R.color.common_green));
//                } else {
//                    helper.setText(R.id.bili, "+" + item.getRiseRate());
//                    helper.setBackgroundRes(R.id.bili, R.drawable.common_button_redshape);
//                    helper.setTextColor(R.id.coinprice, mContext.getResources().getColor(R.color.common_red));
//                }
//            }
//        };
//        mAdapter2.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//            }
//        });
//        mRecyclerview.setAdapter(mAdapter2);

        mImgScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openScanCode();
            }
        });
        mImgPopview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityAfterLogin(new Intent(mContext, AccountManagerActivity.class));
            }
        });
    }

    private void openScanCode() {
        IntentIntegrator intentIntegrator = new IntentIntegrator(getActivity());
        intentIntegrator.setCaptureActivity(ScanActivity.class);
        intentIntegrator.initiateScan();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == Activity.RESULT_OK) {
                if (requestCode == REQUEST_CODE) {
                    String code = data.getStringExtra("data");
                    try {
                        if (TextUtils.isEmpty(code)) {
                            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
                            if (result != null && !TextUtils.isEmpty(result.getContents())) {
                                code = result.getContents();

                            }
                        }
                        String str = code.substring(code.length() - 6);
                        Intent intent = new Intent(mContext, RegisterActivity.class);
                        intent.putExtra("invit", str);
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                        if (!TextUtils.isEmpty(code)) {
                            String str = code.substring(code.length() - 6);
                            Intent intent = new Intent(mContext, RegisterActivity.class);
                            intent.putExtra("invit", str);
                            startActivity(intent);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.showToast(getString(R.string.qrcode_error));
        }
    }

    @Override
    protected void initDatas() {
        TreeMap params = new TreeMap<>();
        mPresenter.getData(DataUtil.sign(params));
        mPresenter.load(DataUtil.sign(params));
        TreeMap params2 = new TreeMap<>();
        params2.put("act", ConstantUrl.MARKET_GETHOTCOIN);
        mPresenter.update(DataUtil.sign(params2));

        TreeMap params3 = new TreeMap<>();
        params3.put("act", ConstantUrl.TRADE_HOME_INDEX);
        params3.put("name", "USDT");
        mPresenter.getMartketList(DataUtil.sign(params3));
    }


    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
    public void requestSuccess(BannerData user) {
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CENTER);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(user.getData());
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
        List<String> titles = new ArrayList<>();
        for (BannerData.DataBean dataBean : user.getData()) {
            titles.add(dataBean.getTitle());
        }
//        banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(1500);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }

    @Override
    public void nodata(String msg) {

    }


    @Override
    public void requestError(String msg) {
        ToastUtils.showToast(msg);
    }

    @Override
    public void requestupdate(NoticeData bean) {
        mNotice.setText(bean.getData().getTxt());
        mNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(mContext, LinkWebViewActivity.class);
                intent2.putExtra(LinkWebViewActivity.WEB_TITLE, R.string.notice);
                intent2.putExtra("type", 3);
                intent2.putExtra(LinkWebViewActivity.WEB_URL, bean.getData().getUrl());
                startActivity(intent2);
            }
        });
    }

    @Override
    public void requestHotcoin(HotCoin bean) {
        mAdapter.setNewData(bean.getData());

    }
    private int sort = 0;
    private int mCurrentPosition = -1;private String searchAsset = "";
    @Override
    public void requestHotcoin(HomeData commonBean) {
//        mKineQuickAdapter.setNewData(commonBean.getData());
        if(EmptyUtils.isNotEmpty(commonBean)){
            for (int j = 0; j < commonBean.getData().size(); j++) {
                if (mCurrentPosition == j) {
                    commonBean.getData().get(j).isShow = true;
                }
            }
            mKineQuickAdapter.setData(commonBean.getData());
//        mAdapter.setNewData(msg);
            mKineQuickAdapter.filter(searchAsset, sort, mCurrentPosition, false);
        }
    }

    @Override
    public void requestSuccess(String s) {

    }


    @OnClick({R.id.ll_chongtibi, R.id.ll_toupiao, R.id.ll_gongmu, R.id.ll_heyuejiaoyi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_chongtibi:
                Intent intent11 = new Intent();
                intent11.setClass(mContext, AssetActivity.class);
                intent11.putExtra("wallet_type", "general");
                intent11.putExtra("title", getString(R.string.finance_spot_assets));
                startActivityAfterLogin(intent11);
                break;
            case R.id.ll_toupiao:
                Intent intent = new Intent();
                intent.setClass(mContext, VoteCoinActivity.class);
                intent.putExtra("id", 1);
                startActivityAfterLogin(intent);
                break;
            case R.id.ll_gongmu:
                Intent intent1 = new Intent();
                intent1.setClass(mContext, VoteCoinActivity.class);
                intent1.putExtra("id", 2);
                startActivityAfterLogin(intent1);
                break;
            case R.id.ll_heyuejiaoyi:
                Intent intent3 = new Intent();
                intent3.setClass(mContext, ContractActivity.class);
                intent3.putExtra("wallet_type", "contract");
                intent3.putExtra("title", getString(R.string.contract));
                startActivityAfterLogin(intent3);
                break;
        }
    }

    @Override
    public void doSomeThing(Home item) {
        HashMap params = new HashMap<>();
        params.put("act", ConstantUrl.TRADE_IS_COLLECTION);
        params.put("one_xnb", item.getCurrentype());
        params.put("two_xnb", item.getExchangeType());
        params.put("status", item.getCollection() == 0 ? 1 + "" : 0 + "");
        mPresenter.collection(DataUtil.sign(params));

    }

    @Override
    public void doItem(int item) {mCurrentPosition = item;
    }

}
