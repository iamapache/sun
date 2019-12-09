package com.madaex.exchange.ui.market.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.flyco.tablayout.SlidingTabLayout;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseNetLazyFragment;
import com.madaex.exchange.common.util.DataUtil;
import com.madaex.exchange.common.util.ToastUtils;
import com.madaex.exchange.common.view.CustomPopWindow;
import com.madaex.exchange.common.view.NoScrollViewPager;
import com.madaex.exchange.ui.adapter.TitleStatePagerAdapter;
import com.madaex.exchange.ui.constant.ConstantUrl;
import com.madaex.exchange.ui.constant.Constants;
import com.madaex.exchange.ui.finance.address.activity.ScanActivity;
import com.madaex.exchange.ui.market.activity.MessageListActivity;
import com.madaex.exchange.ui.market.bean.Home;
import com.madaex.exchange.ui.market.bean.TitleBean;
import com.madaex.exchange.ui.market.contract.HomeContract;
import com.madaex.exchange.ui.market.presenter.HomePresenter;
import com.madaex.exchange.ui.mine.activity.AccountManagerActivity;

import java.util.ArrayList;
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

public class MarketFragment extends BaseNetLazyFragment<HomePresenter> implements HomeContract.View {

    @BindView(R.id.stl)
    SlidingTabLayout mStl;
    @BindView(R.id.vp)
    NoScrollViewPager mVp;
    Unbinder unbinder;
    @BindView(R.id.img_popview)
    ImageView mImgPopview;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private List<String> mTitleList = new ArrayList<>();
    private CustomPopWindow mCustomPopWindow;


    public static MarketFragment newInstance(String string) {
        MarketFragment fragment = null;
        if (fragment == null) {
            fragment = new MarketFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putString(Constants.ARGS, string);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_market;
    }

    @Override
    public void initInjector() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initDatas() {
        TreeMap params = new TreeMap<>();
        params.put("act", ConstantUrl.TRADE_HOME_INDEX_TOP);
        mPresenter.getTitleData(DataUtil.sign(params));
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

    @OnClick({R.id.img_msg, R.id.img_popview})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_msg:
                Intent intent = new Intent(mContext, MessageListActivity.class);
                intent.putExtra("market", "ada_GRC");
                intent.putExtra("one_xnb", "ada");
                intent.putExtra("two_xnb", "GRC");
                startActivity(intent);
                break;
            case R.id.img_popview:
                showPopMenu();
                break;
        }
    }

    private void openScanCode() {
        IntentIntegrator intentIntegrator = new IntentIntegrator(getActivity());
        intentIntegrator.setCaptureActivity(ScanActivity.class);
        intentIntegrator.initiateScan();
    }
    private void showPopMenu() {
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.pop_menumark, null);
        //处理popWindow 显示内容
        handleLogic(contentView);
        //创建并显示popWindow
        mCustomPopWindow = new CustomPopWindow.PopupWindowBuilder(mContext)
                .setView(contentView)
                .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                .setBgDarkAlpha(0.7f) // 控制亮度
                .setOnDissmissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                    }
                })
                .create()
                .showAsDropDown(mImgPopview, -2, 10);

    }

    private void handleLogic(View contentView) {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCustomPopWindow != null) {
                    mCustomPopWindow.dissmiss();
                }
                switch (v.getId()) {
                    case R.id.ll_item1:
                        goAccount();
                        break;
                    case R.id.ll_item2:
                        openScanCode();
                        break;
                }
            }
        };
        contentView.findViewById(R.id.ll_item1).setOnClickListener(listener);
        contentView.findViewById(R.id.ll_item2).setOnClickListener(listener);
    }

    private void goAccount() {
        startActivityAfterLogin(new Intent(mContext, AccountManagerActivity.class));
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            try {
                if (resultCode == Activity.RESULT_OK) {
                    if (requestCode == REQUEST_CODE) {
                        String code = data.getStringExtra("data");
                        try {
                            ToastUtils.showToast(code);
                            if (TextUtils.isEmpty(code)) {
                                IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
                                if (result != null && !TextUtils.isEmpty(result.getContents())) {
                                    code = result.getContents();
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            if (!TextUtils.isEmpty(code)) {
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
    public void requestSuccess(List<Home> msg) {

    }

    @Override
    public void requestError(String msg) {

    }

    @Override
    public void requestSuccess(String msg) {

    }

    @Override
    public void SuccessTitle(TitleBean msg) {
        mTitleList.addAll(msg.getData());
        for (int j = 0; j < mTitleList.size(); j++) {
            mFragments.add(TransactionListFragment.newInstance(j,mTitleList.get(j)));
        }
        TitleStatePagerAdapter mAdapter = new TitleStatePagerAdapter(getChildFragmentManager(), mFragments, mTitleList);
        mVp.setAdapter(mAdapter);
        mVp.setCurrentItem(1);
        mStl.setViewPager(mVp);
    }

}
