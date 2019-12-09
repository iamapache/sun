package com.madaex.exchange.ui.market.fragment;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseNetLazyFragment;
import com.madaex.exchange.common.util.DataUtil;
import com.madaex.exchange.ui.buy.bean.Event;
import com.madaex.exchange.ui.constant.ConstantUrl;
import com.madaex.exchange.ui.constant.Constants;
import com.madaex.exchange.ui.market.bean.CoinDetail;
import com.madaex.exchange.ui.market.contract.CoinDetailContract;
import com.madaex.exchange.ui.market.presenter.CoinDetailPresenter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 项目：  madaexchange
 * 类名：  CoinDetailFragment.java
 * 时间：  2018/9/14 17:11
 * 描述：  ${TODO}
 */

public class CoinDetailFragment extends BaseNetLazyFragment<CoinDetailPresenter> implements CoinDetailContract.View {

    @BindView(R.id.webview)
    WebView mWebview;
    Unbinder unbinder;
    @BindView(R.id.info)
    TextView mInfo;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_coindetail;
    }

    public static CoinDetailFragment newInstance(String string) {
        CoinDetailFragment fragment = null;
        if (fragment == null) {
            fragment = new CoinDetailFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putString(Constants.ARGS, string);
        fragment.setArguments(bundle);
        return fragment;
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
        params.put("act", ConstantUrl.TRADE_INTRODUCE);
        params.put("one_xnb", getArguments().getString(Constants.ARGS));
        mPresenter.getData(DataUtil.sign(params));
    }

    @Override
    public void requestSuccess(CoinDetail bean) {
        mInfo.setText(Html.fromHtml(bean.getData()));
//        mWebview.loadData( "" , "text/html", "UTF-8" ) ;
//        String html = "<html>"  + bean.getData() + "</body></html>";
//        mWebview.loadData(html, "text/html", "uft-8");
//        Logger.i("<==>html:" + bean.getData());

    }


    @Override
    public void requestError(String msg) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        if (event != null && event.getCode() == Constants.MARK) {
            String coin = event.getMsg();
            TreeMap params = new TreeMap<>();
            params.put("act", ConstantUrl.TRADE_INTRODUCE);
            params.put("one_xnb", coin.split("/")[0]);
            mPresenter.getData(DataUtil.sign(params));
        }else if (event != null && event.getCode() == Constants.change) {

            if (isVisible == true ) {
                String coin = event.getMsg();
                TreeMap params = new TreeMap<>();
                params.put("act", ConstantUrl.TRADE_INTRODUCE);
                params.put("one_xnb", coin.split("/")[0]);
                mPresenter.getData(DataUtil.sign(params));
            }
        }
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

    }
}
