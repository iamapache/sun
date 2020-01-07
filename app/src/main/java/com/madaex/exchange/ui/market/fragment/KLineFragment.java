package com.madaex.exchange.ui.market.fragment;

import android.os.Bundle;
import android.text.TextUtils;

import com.github.mikephil.charting.stockChart.data.KLineDataManage;
import com.github.mikephil.charting.stockChart.view.KLineView;
import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseNetLazyFragment;
import com.madaex.exchange.common.util.DataUtil;
import com.madaex.exchange.ui.buy.bean.Event;
import com.madaex.exchange.ui.constant.ConstantUrl;
import com.madaex.exchange.ui.constant.Constants;
import com.madaex.exchange.ui.market.contract.KLineContract;
import com.madaex.exchange.ui.market.presenter.KLinePresenter;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;

/**
 * 项目：  madaexchange
 * 类名：  KLineFragment.java
 * 时间：  2018/9/13 16:58
 * 描述：  ${TODO}
 */

public class KLineFragment extends BaseNetLazyFragment<KLinePresenter> implements KLineContract.View, KLineView.CallBack {
    @BindView(R.id.combinedchart)
    KLineView combinedchart;
    private boolean land;//是否横屏
    private KLineDataManage kLineData;
    private JSONObject object;
    private int indexType = 1;
    private String mType;
    private String one_xnb = "";
    private String two_xnb = "";

    public static KLineFragment newInstance(String type, boolean land) {
        KLineFragment fragment = null;
        if (fragment == null) {
            fragment = new KLineFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        bundle.putBoolean("landscape", land);
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
        //?market=ada_GRC&type=1min&size=1000
        String one_xnb = getActivity().getIntent().getStringExtra("one_xnb");
        String two_xnb = getActivity().getIntent().getStringExtra("two_xnb");
        getData(one_xnb, two_xnb);
    }

    private void getData(String one_xnb, String two_xnb) {
        showProgressDialog();
        String market = one_xnb + "_" + two_xnb;
        HashMap params3 = new HashMap<>();
        params3.put("act", ConstantUrl.TRADE_HOME_INDEX_DETAIL_KLINE);
        params3.put("market", market);
        params3.put("type", mType);
        params3.put("market_type", getActivity().getIntent().getStringExtra("market_type"));
        mPresenter.getData(DataUtil.sign(params3));
        Logger.i("one_xnb" );
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_kline;
    }

    @Override
    public void initInjector() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initView() {
        mType = getArguments().getString("type");
        land = getArguments().getBoolean("landscape");

    }

    @Override
    protected void initDatas() {
        combinedchart.setCallBack(this);
        kLineData = new KLineDataManage(getActivity());
        combinedchart.initChart(false, land);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void doSomeThing(int type) {
        loadIndexData(type);
    }

    private void loadIndexData(int type) {
        indexType = type;
        switch (indexType) {
            case 1://成交量
                combinedchart.doBarChartSwitch(indexType);
                break;
            case 2://请求MACD
                kLineData.initMACD();
                combinedchart.doBarChartSwitch(indexType);
                break;
            case 3://请求KDJ
                kLineData.initKDJ();
                combinedchart.doBarChartSwitch(indexType);
                break;
            case 4://请求BOLL
                kLineData.initBOLL();
                combinedchart.doBarChartSwitch(indexType);
                break;
            case 5://请求RSI
                kLineData.initRSI();
                combinedchart.doBarChartSwitch(indexType);
                break;
            default:
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        if (event != null && event.getCode() == Constants.MARK) {
            if (isVisible == false && isPrepared == false) {
                String coin = event.getMsg();
                String one_xnb = coin.split("/")[0];
                String two_xnb = coin.split("/")[1];
                getData(one_xnb, two_xnb);
            }

        } else if (event != null && event.getCode() == Constants.change) {
                String coin = event.getMsg();
                String one_xnb = coin.split("/")[0];
                String two_xnb = coin.split("/")[1];
                getData(one_xnb, two_xnb);
        }
    }

    @Override
    public void requestSuccess(String jsonStr) {
        try {
            if (!TextUtils.isEmpty(jsonStr)) {
                object = new JSONObject(jsonStr);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (object != null) {
            kLineData.parseKlineData(object, "000001.IDX.SH", land);
            if(combinedchart!=null){
                combinedchart.setDataToChart(kLineData);
            }

        }
        dismissProgressDialog();
    }

    @Override
    public void requestError(String msg) {
        dismissProgressDialog();
    }


}
