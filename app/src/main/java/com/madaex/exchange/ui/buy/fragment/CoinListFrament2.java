package com.madaex.exchange.ui.buy.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.gson.Gson;
import com.madaex.exchange.R;
import com.madaex.exchange.common.CacheProviders;
import com.madaex.exchange.common.GitHubServiceManager;
import com.madaex.exchange.common.base.activity.dialog.BaseNetDialogFragment;
import com.madaex.exchange.common.util.DataUtil;
import com.madaex.exchange.ui.adapter.TitleStatePagerAdapter;
import com.madaex.exchange.ui.buy.bean.CoinList;
import com.madaex.exchange.ui.buy.bean.DealInfo;
import com.madaex.exchange.ui.buy.contract.CoinContract;
import com.madaex.exchange.ui.buy.contract.CoinLister;
import com.madaex.exchange.ui.buy.presenter.CoinPresenter;
import com.madaex.exchange.ui.constant.ConstantUrl;
import com.madaex.exchange.ui.constant.Constants;
import com.madaex.exchange.ui.market.bean.EntrustList;
import com.madaex.exchange.ui.market.bean.Home;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictDynamicKey;

/**
 * 项目：  madaexchange
 * 类名：  CoinFrament.java
 * 时间：  2018/9/5 12:17
 * 描述：  ${TODO}
 */

public class CoinListFrament2 extends BaseNetDialogFragment<CoinPresenter> implements CoinContract.View, CoinLister {
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.pager)
    ViewPager mPager;
    Unbinder unbinder;
    @BindView(R.id.toolbar_title_tv)
    TextView mToolbarTitleTv;
    private List<String> mTitleList = new ArrayList<>();
    private List<Fragment> mViewList = new ArrayList<>();

    public static CoinListFrament2 newInstance(int type, String one_xnb, String two_xnb) {
        CoinListFrament2 fragment = null;
        if (fragment == null) {
            fragment = new CoinListFrament2();
        }
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
//        bundle.putParcelable(Constants.ARGS, str);
        bundle.putString(Constants.ONE_XNB, one_xnb);
        bundle.putString(Constants.TWO_XNB, two_xnb);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_coin_list2;
    }

    @Override
    public void initInjector() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initView() {
        String one_xnb = getArguments().getString(Constants.ONE_XNB);
        String two_xnb = getArguments().getString(Constants.TWO_XNB);

        mToolbarTitleTv.setText(one_xnb + "/" + two_xnb);
//        CoinList data = getArguments().getParcelable(Constants.ARGS);

    }

    @Override
    protected void initDatas() {
//        TreeMap params = new TreeMap<>();
//        params.put("act", ConstantUrl.TRADE_COIN_LIST);
//        mPresenter.getData(DataUtil.sign(params));

        requestHttp("22");
    }

    private void requestHttp(String userName) {
        TreeMap params = new TreeMap<>();
        params.put("act", ConstantUrl.TRADE_COIN_LIST);
        //网络请求数据
        Observable<String> user = new GitHubServiceManager()
                .getTestResult2(DataUtil.sign(params));
        //缓存配置
        CacheProviders.getUserCache()
                .getTestResult2(user, new DynamicKey(userName), new EvictDynamicKey(false))//用户名作为动态key生成不同文件存储数据，默认不清除缓存数据
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String data2) {
                        Gson gson = new Gson();
                        CoinList data = gson.fromJson(data2, CoinList.class);
                        srtData(data);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


   void srtData(CoinList data){
       int type = getArguments().getInt("type",0);
       for (int j = 0; j < data.getData().size(); j++) {
           CoinList.DataBean dataBean= data.getData().get(j);
           mTitleList.add(dataBean.getTitle());
           CoinFragment fragment3 = CoinFragment.newInstance(dataBean.getTitle(),j,type,dataBean.getList());
           fragment3.setCoinLister(this);
           mViewList.add(fragment3);
       }
       for (CoinList.DataBean dataBean : data.getData()) {
           mTitleList.add(dataBean.getTitle());
       }

       TitleStatePagerAdapter adapter = new TitleStatePagerAdapter(getChildFragmentManager(), mViewList
               , mTitleList);
       mPager.setAdapter(adapter);
       mPager.setCurrentItem(0);
       mPager.setOffscreenPageLimit(3);
       mTabLayout.setupWithViewPager(mPager);
       mTabLayout.setTabsFromPagerAdapter(adapter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDatePickerDialog);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Window dialogWindow = getDialog().getWindow();

//        dialogWindow.setBackgroundDrawableResource(R.color.transparent);
        dialogWindow.getDecorView().setPadding(0, 0, 0, 0);
        getDialog().setCanceledOnTouchOutside(true);
        getDialog().setCancelable(true);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.TOP;
        dialogWindow.setAttributes(lp);

    }

    @Override
    public void requestSuccess(String msg) {

    }


    @Override
    public void requestError(String msg) {

    }

    @Override
    public void sendMsgSuccess(CoinList data) {

    }

    @Override
    public void sendDealSuccess(String msg) {

    }

    @Override
    public void requestDetailSuccess(Home baseBean) {

    }

    @Override
    public void requestToken(String baseBean) {

    }


    @Override
    public void sendMsgSuccess(DealInfo msg) {

    }

    @Override
    public void requestSuccess(EntrustList bean) {

    }

    @Override
    public void nodata(String msg) {

    }

    @Override
    public void deleteSuccess(String msg) {

    }

    @Override
    public void deleteError(String msg) {

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

    @OnClick(R.id.rl)
    public void onViewClicked() {
        dismiss();
    }

    @Override
    public void inputInforCompleted(String string) {
        dismiss();
    }

}
