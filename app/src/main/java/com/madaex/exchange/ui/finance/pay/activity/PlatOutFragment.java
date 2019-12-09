package com.madaex.exchange.ui.finance.pay.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.adorkable.iosdialog.BottomSheetDialog;
import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseNetLazyFragment;
import com.madaex.exchange.common.util.DataUtil;
import com.madaex.exchange.common.util.SPUtils;
import com.madaex.exchange.common.util.ToastUtils;
import com.madaex.exchange.ui.constant.ConstantUrl;
import com.madaex.exchange.ui.constant.Constants;
import com.madaex.exchange.ui.finance.pay.bean.PlatRecord;
import com.madaex.exchange.ui.finance.pay.contract.PlatContrct;
import com.madaex.exchange.ui.finance.pay.presenter.PlatPresenter;

import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 项目：  madaexchange
 * 类名：  TransationBuyFragment.java
 * 时间：  2018/8/30 10:19
 * 描述：  ${TODO}
 */

public class PlatOutFragment extends BaseNetLazyFragment<PlatPresenter> implements PlatContrct.View {


    @BindView(R.id.coinname)
    TextView mCoinname;
    @BindView(R.id.name)
    EditText mName;
    @BindView(R.id.number)
    EditText mNumber;
    @BindView(R.id.submit)
    Button mSubmit;
    Unbinder unbinder;
    @BindView(R.id.password)
    EditText mPassword;

    public static PlatOutFragment newInstance(int string) {
        PlatOutFragment fragment = null;
        if (fragment == null) {
            fragment = new PlatOutFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.ARGS, string);
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
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_platout;
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
    }

    @Override
    public void requestSuccess(String msg) {
        ToastUtils.showToast(msg);
        mName.getText().clear();
        mNumber.getText().clear();
        mPassword.getText().clear();
//        startActivity(new Intent(mContext, C2CTransationListActivity.class));
    }

    @Override
    public void requestSuccess(PlatRecord commonBean) {

    }

    @Override
    public void requestError(String msg) {
        ToastUtils.showToast(msg);
    }


    private String one_xnb = "";

    @OnClick(R.id.submit)
    public void onViewClicked() {
        validate();
    }

    private void validate() {

        if (TextUtils.isEmpty(mCoinname.getText().toString())) {
            ToastUtils.showToast(R.string.selectcurrency);
            return;
        }
        if (TextUtils.isEmpty(mName.getText().toString())) {
            ToastUtils.showToast(R.string.userID);
            return;
        }
        if (TextUtils.isEmpty(mNumber.getText().toString())) {
            ToastUtils.showToast(getString(R.string.entrureturnnumber));
            return;
        }
        if (TextUtils.isEmpty(mPassword.getText().toString())) {
            ToastUtils.showToast(R.string.entrytranspwd);
            return;
        }
        TreeMap params = new TreeMap<>();
        params.put("act", ConstantUrl.TRADE_PLAT_OUT);
        params.put("user_id", SPUtils.getString(Constants.TOKEN));
        params.put("coinname", mCoinname.getText().toString().trim());
        params.put("coinnum", mNumber.getText().toString().trim());
        params.put("userid", mName.getText().toString().trim());
        params.put("paypassword", mPassword.getText().toString().trim());
        params.put("type", getArguments().getInt(Constants.ARGS) + "");
        mPresenter.submit(DataUtil.sign(params));
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

    @OnClick(R.id.coinname)
    public void onViewClicked2() {
        if (getArguments().getInt(Constants.ARGS) == 1) {
            showBottomSheetDialog();
        } else if (getArguments().getInt(Constants.ARGS) == 2) {
            showBottomSheetDialog2();
        }

    }

    public void showBottomSheetDialog() {
        new BottomSheetDialog(getActivity())
                .init()
                .setTitle(getString(R.string.selectcurrency))
                .setCancelable(true)    //设置手机返回按钮是否有效
                .setCanceledOnTouchOutside(true)  //设置 点击空白处是否取消 Dialog 显示
                //如果条目样式一样，可以直接设置默认样式
                .addSheetItem("GRC",
                        //可以对一个条目单独设置字体样式
                        new BottomSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                mCoinname.setText("GRC");
                            }
                        })
                .addSheetItem("SNRC",
                        new BottomSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                mCoinname.setText("SNRC");
                            }
                        })
                .show();
    }

    public void showBottomSheetDialog2() {
        new BottomSheetDialog(getActivity())
                .init()
                .setTitle(getString(R.string.selectcurrency))
                .setCancelable(true)    //设置手机返回按钮是否有效
                .setCanceledOnTouchOutside(true)  //设置 点击空白处是否取消 Dialog 显示
                //如果条目样式一样，可以直接设置默认样式
                .addSheetItem("GRC",
                        //可以对一个条目单独设置字体样式
                        new BottomSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                mCoinname.setText("GRC");
                            }
                        })
                .show();
    }
}
