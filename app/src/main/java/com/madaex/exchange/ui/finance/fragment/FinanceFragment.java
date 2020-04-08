package com.madaex.exchange.ui.finance.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseNetLazyFragment;
import com.madaex.exchange.common.util.DataUtil;
import com.madaex.exchange.common.util.EmptyUtils;
import com.madaex.exchange.common.util.ToastUtils;
import com.madaex.exchange.common.view.CustomPopWindow;
import com.madaex.exchange.ui.buy.bean.Event;
import com.madaex.exchange.ui.constant.ConstantUrl;
import com.madaex.exchange.ui.constant.Constants;
import com.madaex.exchange.ui.finance.activity.AssetActivity;
import com.madaex.exchange.ui.finance.address.activity.ScanActivity;
import com.madaex.exchange.ui.finance.bank.activity.BankListActivity;
import com.madaex.exchange.ui.finance.bean.Asset;
import com.madaex.exchange.ui.finance.bean.Recharge;
import com.madaex.exchange.ui.finance.contract.AssetContract;
import com.madaex.exchange.ui.finance.contracts.activity.ContractActivity;
import com.madaex.exchange.ui.finance.presenter.AssetPresenter;
import com.madaex.exchange.ui.finance.transfer.activity.NcActivity;
import com.madaex.exchange.ui.finance.transfer.activity.TransferActivity;
import com.madaex.exchange.ui.finance.vote.activity.VoteCoinActivity;
import com.madaex.exchange.ui.mine.activity.AccountManagerActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
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

public class FinanceFragment extends BaseNetLazyFragment<AssetPresenter> implements AssetContract.View, OnChartValueSelectedListener {

    Unbinder unbinder;
    @BindView(R.id.cny)
    TextView mCny;
    @BindView(R.id.name1)
    TextView mName1;
    @BindView(R.id.money1)
    TextView mMoney1;
    @BindView(R.id.name2)
    TextView mName2;
    @BindView(R.id.money2)
    TextView mMoney2;
    @BindView(R.id.name3)
    TextView mName3;
    @BindView(R.id.money3)
    TextView mMoney3;
    @BindView(R.id.name4)
    TextView mName4;
    @BindView(R.id.money4)
    TextView mMoney4;
    @BindView(R.id.textView)
    TextView mTextView;
    @BindView(R.id.ll_votecoin)
    LinearLayout mLlVotecoin;
    @BindView(R.id.ll_gongmu)
    LinearLayout mLlGongmu;
    @BindView(R.id.img_popview)
    ImageView mImgPopview;
    //    @BindView(R.id.mPieChart)
//    PieChart mPieChart;
    @BindView(R.id.asset)
    TextView mAsset;
    @BindView(R.id.bili1)
    TextView mBili1;
    @BindView(R.id.bili2)
    TextView mBili2;
    @BindView(R.id.bili3)
    TextView mBili3;
    @BindView(R.id.bili4)
    TextView mBili4;
    private CustomPopWindow mCustomPopWindow;

    public static FinanceFragment newInstance(String string) {
        FinanceFragment fragment = null;
        if (fragment == null) {
            fragment = new FinanceFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putString(Constants.ARGS, string);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_finance;
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
        mImgPopview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopMenu();
            }
        });
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

    @OnClick({R.id.ll_asset, R.id.ll_bank, R.id.ll_c2ctrans, R.id.ll_otc, R.id.ll_votecoin, R.id.ll_gongmu, R.id.ll_internaltransfer, R.id.ll_nc})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_asset:
                Intent intent11 = new Intent();
                intent11.setClass(mContext, AssetActivity.class);
                intent11.putExtra("wallet_type", "general");
                intent11.putExtra("title", getString(R.string.finance_spot_assets));
                startActivityAfterLogin(intent11);
                break;
            case R.id.ll_bank:
                startActivityAfterLogin(new Intent(mContext, BankListActivity.class));
//                startActivityAfterLogin(new Intent(mContext, WayActivity.class));
//                Intent intent2 = new Intent();
//                intent2.setClass(mContext, AssetActivity.class);
//                intent2.putExtra("wallet_type", "hedge");
//                intent2.putExtra("title", getString(R.string.SpotAssets));
//                startActivityAfterLogin(intent2);
                break;
            case R.id.ll_c2ctrans:
//                startActivityAfterLogin(new Intent(mContext, C2CTransationActivity.class));
//                startActivityAfterLogin(new Intent(mContext, C2CListActivity.class));
                Intent intent3 = new Intent();
                intent3.setClass(mContext, ContractActivity.class);
                intent3.putExtra("wallet_type", "contract");
                intent3.putExtra("title", getString(R.string.contract));
                startActivityAfterLogin(intent3);
                break;
            case R.id.ll_otc:
//                startActivityAfterLogin(new Intent(mContext, PlatActivity.class));
                ToastUtils.showToast(getString(R.string.comingsoon));
                break;
            case R.id.ll_votecoin:
                Intent intent = new Intent();
                intent.setClass(mContext, VoteCoinActivity.class);
                intent.putExtra("id", 1);
                startActivityAfterLogin(intent);
//                ToastUtils.showToast(getString(R.string.comingsoon));
                break;
            case R.id.ll_gongmu:
                Intent intent1 = new Intent();
                intent1.setClass(mContext, VoteCoinActivity.class);
                intent1.putExtra("id", 2);
                startActivityAfterLogin(intent1);
//                ToastUtils.showToast(getString(R.string.comingsoon));
                break;
            case R.id.ll_internaltransfer:
                startActivityAfterLogin(new Intent(mContext, TransferActivity.class));
                break;
            case R.id.ll_nc:
                startActivityAfterLogin(new Intent(mContext, NcActivity.class));
                break;
        }
    }

    @Override
    public void nodata(String msg) {
        mMoney1.setText("￥ 0.0");
        mMoney2.setText("￥ 0.0");
        mMoney3.setText("￥ 0.0");
        mMoney4.setText("￥ 0.0");
        mCny.setText("￥ 0.0");
//        initChart(1,
//                1,
//                1,
//                1, "",
//                "", "");
    }

    @Override
    public void requestError(String msg) {
        mMoney1.setText("￥ 0.0");
        mMoney2.setText("￥ 0.0");
        mMoney3.setText("￥ 0.0");
        mMoney4.setText("￥ 0.0");
        mCny.setText("￥ 0.0");
//        initChart(1,
//                1,
//                1,
//                1, "",
//                "", "");
    }

    @Override
    public void requestSuccess(Asset commonBean) {

        if (EmptyUtils.isNotEmpty(commonBean) && EmptyUtils.isNotEmpty(commonBean.getData())) {
            String sAgeFormat1 = getResources().getString(R.string.finance_assets);
            @SuppressLint({"StringFormatInvalid", "LocalSuppress"}) String sFinal1 = String.format(sAgeFormat1, "USDT");
            mAsset.setText(sFinal1);
            mCny.setText("￥" + new java.text.DecimalFormat("#.00").format(Double.valueOf(commonBean.getData().getAssets().getRmb())));
//        mName1.setText(commonBean.getData().getXnb_list().get(0).getXnb_name());
//        mName2.setText(commonBean.getData().getXnb_list().get(1).getXnb_name());
//        mName3.setText(commonBean.getData().getXnb_list().get(2).getXnb_name());
//        mName4.setText(commonBean.getData().getXnb_list().get(3).getXnb_name());
            mName1.setText(commonBean.getData().getCoin_hot().get(0).getName());




            mMoney1.setText("￥" + commonBean.getData().getCoin_hot().get(0).getValue());




            DecimalFormat df = new DecimalFormat("0%");

            df.setMaximumFractionDigits(2);
            df.setRoundingMode(RoundingMode.HALF_UP);
            mBili1.setText(new BigDecimal(commonBean.getData().getCoin_hot().get(0).getPrecent()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()+"%");

            if(commonBean.getData().getCoin_hot().size()>1){
                mName2.setText(commonBean.getData().getCoin_hot().get(1).getName());
                mMoney2.setText("￥" + commonBean.getData().getCoin_hot().get(1).getValue());
                mBili2.setText(new BigDecimal(commonBean.getData().getCoin_hot().get(1).getPrecent()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()+"%");
            }
            if(commonBean.getData().getCoin_hot().size()>2){
                mName3.setText(commonBean.getData().getCoin_hot().get(2).getName());
                mMoney3.setText("￥" + commonBean.getData().getCoin_hot().get(2).getValue());
                mBili3.setText(new BigDecimal(commonBean.getData().getCoin_hot().get(2).getPrecent()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()+"%");
            }
//            mBili2.setText(df.format((Double.valueOf(commonBean.getData().getCoin_hot().get(1).getValue())* 1.0)/(Double.valueOf(commonBean.getData().getAssets().getUsdt())* 1.0)));
//            mBili3.setText(df.format((Double.valueOf(commonBean.getData().getCoin_hot().get(2).getValue())* 1.0)/(Double.valueOf(commonBean.getData().getAssets().getUsdt())* 1.0)));
            if(commonBean.getData().getCoin_hot().size()>3){
                mName4.setText(commonBean.getData().getCoin_hot().get(3).getName());
                mMoney4.setText("￥" + commonBean.getData().getCoin_hot().get(3).getValue());
                mBili4.setText(new BigDecimal(commonBean.getData().getCoin_hot().get(3).getPrecent()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()+"%");}
//            initChart(Float.valueOf(commonBean.getData().getCoin_hot().get(0).getValue()),
//                    Float.valueOf(commonBean.getData().getCoin_hot().get(1).getValue()),
//                    Float.valueOf(commonBean.getData().getCoin_hot().get(2).getValue()),
//                    Float.valueOf(commonBean.getData().getCoin_hot().get(3).getValue()), commonBean.getData().getCoin_hot().get(0).getName(),
//                    commonBean.getData().getCoin_hot().get(1).getName(), commonBean.getData().getCoin_hot().get(2).getName());
        }

    }

    @Override
    public void requestRecharge(Recharge commonBean) {

    }

//    private void initChart(float f1, float f2, float f3, float f4, String str1, String str2, String str3) {
//        if (f1 == 0 && f2 == 0 && f3 == 0 && f4 == 0) {
//            f1 = 1;
//            f2 = 1;
//            f3 = 1;
//            f4 = 1;
//        }
//        mPieChart.setUsePercentValues(true);
//        mPieChart.getDescription().setEnabled(false);
//        mPieChart.setExtraOffsets(2, 2, 2, 2);
//        mPieChart.setBackgroundColor(Color.WHITE);
//        mPieChart.setDragDecelerationFrictionCoef(0.95f);
//        mPieChart.getLegend().setEnabled(false);
//        mPieChart.setDrawHoleEnabled(true);
//        mPieChart.setBackgroundColor(getResources().getColor(R.color.common_black));
//        mPieChart.setHoleColor(Color.WHITE);
////
//        mPieChart.setTransparentCircleColor(Color.WHITE);
//        mPieChart.setTransparentCircleAlpha(110);
////
////        mPieChart.setHoleRadius(58f);
////        mPieChart.setTransparentCircleRadius(61f);
//
//        mPieChart.setDrawCenterText(false);
//
//        mPieChart.setRotationAngle(0);
//        // 触摸旋转
//        mPieChart.setRotationEnabled(true);
//        mPieChart.setHighlightPerTapEnabled(true);
//
//        //变化监听
//        mPieChart.setOnChartValueSelectedListener(this);
//        mPieChart.setDrawHoleEnabled(false);
//        //模拟数据
//        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
//        entries.add(new PieEntry(f1, str1));
//        entries.add(new PieEntry(f2, str2));
//        entries.add(new PieEntry(f3, str3));
//        entries.add(new PieEntry(f4, getString(R.string.finance_other)));
//
//        //设置数据
//        setData(entries);
//
//        mPieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
//
//        Legend l = mPieChart.getLegend();
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
//        l.setOrientation(Legend.LegendOrientation.VERTICAL);
//        l.setDrawInside(false);
//        l.setXEntrySpace(7f);
//        l.setYEntrySpace(0f);
//        l.setYOffset(0f);
//        // 输入标签样式
//        mPieChart.setEntryLabelColor(Color.TRANSPARENT);
//        mPieChart.setEntryLabelTextSize(0f);
//        mPieChart.setEntryLabelColor(Color.TRANSPARENT);
//    }

    //设置数据
//    private void setData(ArrayList<PieEntry> entries) {
//        PieDataSet dataSet = new PieDataSet(entries, "");
//        dataSet.setSliceSpace(1f);
//        dataSet.setSelectionShift(1f);
//
//        //数据和颜色
//        ArrayList<Integer> colors = new ArrayList<Integer>();
////        colors.add(R.color.key_ok_color);
////        colors.add(R.color.light_pink);
////        colors.add(R.color.common_divier);
////        colors.add(R.color.common_text_5);
//        colors.add(Color.rgb(59, 177, 247));
//        colors.add(Color.rgb(225, 161, 87));
//        colors.add(Color.rgb(223, 227, 226));
//        colors.add(Color.rgb(111, 116, 110));
//        dataSet.setColors(colors);
//
////        dataSet.setValueLinePart1OffsetPercentage(80.f);
////        dataSet.setValueLinePart1Length(0.5f);
////        dataSet.setValueLinePart2Length(0.5f);
////        dataSet.setValueLineColor(Color.BLACK);
////        //当值显示在界面外面的时候是否允许改变量行长度
////        dataSet.setValueLineVariableLength(false);
////        //设置线的宽度
////        dataSet.setValueLineWidth(1);
////        //设置项X值拿出去
////        dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
////        //设置将Y轴的值拿出去
////        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
//
//        PieData data = new PieData(dataSet);
//        data.setValueFormatter(new PercentFormatter());
//        data.setValueTextSize(0f);
//        data.setValueTextColor(Color.TRANSPARENT);
//        mPieChart.setData(data);
//        mPieChart.highlightValues(null);
//        //刷新
//        mPieChart.invalidate();
//    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        getData();

    }

    private void getData() {
        TreeMap params = new TreeMap<>();
        params.put("act", ConstantUrl.TRADE_ASSETS_LIST);
        mPresenter.getData(DataUtil.sign(params));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        if (event != null && event.getCode() == Constants.LOGINSUCCESS) {
            getData();
        }
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

    private void openScanCode() {
        IntentIntegrator intentIntegrator = new IntentIntegrator(getActivity());
        intentIntegrator.setCaptureActivity(ScanActivity.class);
        intentIntegrator.initiateScan();
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
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
}
