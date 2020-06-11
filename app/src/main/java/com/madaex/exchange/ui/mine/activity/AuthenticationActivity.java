package com.madaex.exchange.ui.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseNetActivity;
import com.madaex.exchange.common.util.DataUtil;
import com.madaex.exchange.common.util.EmptyUtils;
import com.madaex.exchange.common.util.SPUtils;
import com.madaex.exchange.common.util.ToastUtils;
import com.madaex.exchange.ui.buy.bean.Event;
import com.madaex.exchange.ui.constant.ConstantUrl;
import com.madaex.exchange.ui.constant.Constants;
import com.madaex.exchange.ui.finance.pay.bean.Payway;
import com.madaex.exchange.ui.finance.pay.contract.WayContract;
import com.madaex.exchange.ui.finance.pay.presenter.WayPresenter;
import com.madaex.exchange.view.GlideImgManager;
import com.zzti.fengyongge.imagepicker.PhotoSelectorActivity;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 项目：  madaexchange
 * 类名：  AuthenticationActivity.java
 * 时间：  2018/8/24 14:39
 * 描述：  ${TODO}
 */

public class AuthenticationActivity extends BaseNetActivity<WayPresenter> implements WayContract.View {
    @BindView(R.id.tv_username)
    EditText mTvUsername;
    @BindView(R.id.name)
    EditText mName;
    @BindView(R.id.user)
    EditText mUser;
    @BindView(R.id.tv_cardnumber)
    EditText mTvCardnumber;
    @BindView(R.id.address)
    EditText mAddress;
    @BindView(R.id.city)
    EditText mCity;
    @BindView(R.id.postcode)
    EditText mPostcode;
    @BindView(R.id.tv_location)
    TextView mTvLocation;
    @BindView(R.id.ll_1)
    LinearLayout mLl1;
    @BindView(R.id.ll_2)
    LinearLayout mLl2;
    @BindView(R.id.img_cardtop)
    ImageView mImgCardtop;
    @BindView(R.id.img_cardbottom)
    ImageView mImgCardbottom;
    @BindView(R.id.ll_3)
    LinearLayout mLl3;
    private int CODE_REQUEST = 0x002;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_authentication;
    }

    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
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
        Event event = new Event();
        event.setCode(Constants.MINE);
        EventBus.getDefault().post(event);
        finish();
    }

    @Override
    public void requestSuccess(Payway commonBean) {

    }

    @Override
    public void requestPayWaySuccess(String commonBean) {

    }


    @Override
    public void requestError(String msg) {
        ToastUtils.showToast(msg);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_location, R.id.submit, R.id.toolbar_left_btn_ll,R.id.img_cardtop, R.id.img_cardbottom})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_location:

                Intent intent = new Intent(AuthenticationActivity.this, RegionActivity.class);
                startActivityForResult(intent, CODE_REQUEST);

                break;
            case R.id.submit:
                validate();
                break;
            case R.id.toolbar_left_btn_ll:
                finish();
                break;
            case R.id.img_cardtop:
                Intent intenttop = new Intent(AuthenticationActivity.this, PhotoSelectorActivity.class);
                intenttop.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intenttop.putExtra("limit", 1);//number是选择图片的数量
                startActivityForResult(intenttop, 22);
                break;
            case R.id.img_cardbottom:
                Intent intent2 = new Intent(AuthenticationActivity.this, PhotoSelectorActivity.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent2.putExtra("limit", 1);//number是选择图片的数量
                startActivityForResult(intent2, 23);
                break;
        }
    }

    private List<String> mDatalist = new ArrayList<>();
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK
                && requestCode == CODE_REQUEST) {
            String bean = data.getStringExtra("data");
            mTvLocation.setText(bean);
            if (bean.equals(getString(R.string.china))) {
                mLl1.setVisibility(View.GONE);
                mLl2.setVisibility(View.GONE);
                mLl3.setVisibility(View.VISIBLE);
            } else {
                mLl1.setVisibility(View.VISIBLE);
                mLl2.setVisibility(View.VISIBLE);
                mLl3.setVisibility(View.GONE);
            }
        }
        switch (requestCode) {
            case 22:
                if (data != null) {
                    List<String> photos = (List<String>) data.getExtras().getSerializable("photos");
                    //path是选择拍照或者图片的地址数组
                    //处理代码
                    mDatalist.clear();
                    mDatalist.addAll(photos);
//                    mImgCardtop.setBackground(null);
                    GlideImgManager.loadImage(mContext, new File(mDatalist.get(0)), mImgCardtop);
                }
                break;
            case 23:
                if (data != null) {
                    List<String> photos = (List<String>) data.getExtras().getSerializable("photos");
                    //path是选择拍照或者图片的地址数组
                    //处理代码
                    mDatalist.clear();
                    mDatalist.addAll(photos);
//                    mImgCardbottom.setBackground(null);
                    GlideImgManager.loadImage(mContext, new File(mDatalist.get(0)), mImgCardbottom);
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void validate() {
        if (mTvLocation.getText().toString().trim().equals(getString(R.string.china))) {
            if (EmptyUtils.isEmpty(mDatalist)) {
                ToastUtils.showToast(R.string.selectpicture);
                return;
            }
            TreeMap params = new TreeMap<Object, Object>();
            params.put("act", ConstantUrl.USER_PAYMENTADD);
            params.put("user_id", SPUtils.getString(Constants.TOKEN, ""));
            params.put("type", 2 + "");
            mPresenter.saveUserHeadImage(DataUtil.sign(params), (ArrayList<String>) mDatalist);
        } else {
            if (TextUtils.isEmpty(mTvUsername.getText().toString())) {
                ToastUtils.showToast(getString(R.string.entryidname));
                return;
            }
            if (TextUtils.isEmpty(mTvCardnumber.getText().toString())) {
                ToastUtils.showToast(getString(R.string.entryidnumber));
                return;
            }
            TreeMap params = new TreeMap<>();
            params.put("act", ConstantUrl.USER_REALNAME);
            params.put("country", mTvLocation.getText().toString().trim());
            params.put("truename", mTvUsername.getText().toString().trim());
            params.put("idcard", mTvCardnumber.getText().toString().trim());
//        params.put("firstname", mUser.getText().toString().trim());
//        params.put("lastname", mName.getText().toString().trim());
//        params.put("address", mAddress.getText().toString().trim());
//        params.put("city", mCity.getText().toString().trim());
//        params.put("postalcode", mPostcode.getText().toString().trim());
            mPresenter.getData(DataUtil.sign2(params));
        }

    }


}
