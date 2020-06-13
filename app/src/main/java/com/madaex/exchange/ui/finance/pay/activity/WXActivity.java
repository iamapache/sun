package com.madaex.exchange.ui.finance.pay.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseNetActivity;
import com.madaex.exchange.common.net.Constant;
import com.madaex.exchange.common.util.DataUtil;
import com.madaex.exchange.common.util.EmptyUtils;
import com.madaex.exchange.common.util.SPUtils;
import com.madaex.exchange.common.util.ToastUtils;
import com.madaex.exchange.ui.constant.ConstantUrl;
import com.madaex.exchange.ui.constant.Constants;
import com.madaex.exchange.ui.finance.pay.bean.ImgCheck;
import com.madaex.exchange.ui.finance.pay.bean.Payway;
import com.madaex.exchange.ui.finance.pay.bean.UploadIdcard;
import com.madaex.exchange.ui.finance.pay.contract.WayContract;
import com.madaex.exchange.ui.finance.pay.presenter.WayPresenter;
import com.madaex.exchange.ui.mine.activity.LinkWebViewActivity;
import com.madaex.exchange.view.GlideImgManager;
import com.wc.widget.dialog.IosDialog;
import com.zzti.fengyongge.imagepicker.PhotoSelectorActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 项目：  sun
 * 类名：  ZFBActivity.java
 * 时间：  2019/5/13 18:13
 * 描述：  ${TODO}
 */
public class WXActivity extends BaseNetActivity<WayPresenter> implements WayContract.View {
    @BindView(R.id.img)
    ImageView mImg;
    @BindView(R.id.zfbinfo)
    TextView mZfbinfo;
    @BindView(R.id.submit)
    Button mSubmit;
    private List<String> mDatalist = new ArrayList<>();
    @BindView(R.id.ll_del)
    LinearLayout mLlDel;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wxway;
    }

    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initView() {
        SpannableString spanStrStart = new SpannableString(getString(R.string.wxpayway));
        SpannableString spanStrClick = new SpannableString(getString(R.string.generationexample));
        spanStrClick.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent2 = new Intent(mContext, LinkWebViewActivity.class);
                intent2.putExtra(LinkWebViewActivity.WEB_TITLE, getString(R.string.generationexample));
                intent2.putExtra("type", 1);
                intent2.putExtra(LinkWebViewActivity.WEB_URL, Constant.HTTP + "appapi/fiat/payment_method_sample");
                startActivity(intent2);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(ContextCompat.getColor(mContext, R.color.common_bule)); //设置颜色
                ds.setUnderlineText(false);
                //设置字体背景
//				ds.bgColor = Color.parseColor("#FF0000");
            }
        }, 0, spanStrClick.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mZfbinfo.append(spanStrStart);
        mZfbinfo.append(spanStrClick);
        mZfbinfo.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    protected void initDatas() {
        if (getIntent().hasExtra("bean")) {
            Payway.DataBean dataBean = getIntent().getParcelableExtra("bean");
            GlideImgManager.loadImage(mContext, dataBean.getImg(), mImg);
            mLlDel.setVisibility(View.VISIBLE);
            mSubmit.setVisibility(View.GONE);

        }else {
            mImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(WXActivity.this, PhotoSelectorActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intent.putExtra("limit", 1);//number是选择图片的数量
                    startActivityForResult(intent, 20);
                }
            });
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({ R.id.submit, R.id.toolbar_left_btn_ll, R.id.delete, R.id.edit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.submit:
                validate(false);

                break;
            case R.id.toolbar_left_btn_ll:
                finish();
                break;
            case R.id.delete:
                Dialog dialog = new IosDialog.Builder(this).setTitleColor(ContextCompat.getColor(mContext, R.color.common_text_1)).setTitleSize(20)
                        .setMessage(R.string.Confirmthedeletion).setMessageSize(14)
                        .setNegativeButtonColor(Color.GRAY)
                        .setNegativeButtonSize(18)
                        .setNegativeButton(getString(R.string.cancel), new IosDialog.OnClickListener() {
                            @Override
                            public void onClick(IosDialog dialog, View v) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButtonColor(ContextCompat.getColor(mContext, R.color.common_bule))
                        .setPositiveButtonSize(18)
                        .setPositiveButton(R.string.delete, new IosDialog.OnClickListener() {
                            @Override
                            public void onClick(IosDialog dialog, View v) {
                                Payway.DataBean dataBean = getIntent().getParcelableExtra("bean");
                                TreeMap params = new TreeMap<Object, Object>();
                                params.put("act", ConstantUrl.USER_PAYMENTDEL);
                                params.put("user_id", SPUtils.getString(Constants.TOKEN, ""));
                                params.put("id", dataBean.getId());
                                mPresenter.delete(DataUtil.sign(params));
                            }
                        }).build();
                dialog.show();
                break;
            case R.id.edit:
                validate(true);
                break;
        }
    }

    private void validate(boolean b) {
        if (EmptyUtils.isEmpty(mDatalist)) {
            ToastUtils.showToast(R.string.selectpicture);
            return;
        }
        if (b) {
            Payway.DataBean dataBean = getIntent().getParcelableExtra("bean");
            TreeMap params = new TreeMap<Object, Object>();
            params.put("act", ConstantUrl.USER_PAYMENTSAVE);
            params.put("id", dataBean.getId());
            params.put("user_id", SPUtils.getString(Constants.TOKEN, ""));
            params.put("account", "");
            params.put("type", 1 + "");
            mPresenter.saveUserHeadImage(DataUtil.sign(params), (ArrayList<String>) mDatalist);
        } else {

            TreeMap params = new TreeMap<Object, Object>();
            params.put("act", ConstantUrl.USER_PAYMENTADD);
            params.put("user_id", SPUtils.getString(Constants.TOKEN, ""));
            params.put("account", "");
            params.put("type", 1 + "");
            mPresenter.saveUserHeadImage(DataUtil.sign(params), (ArrayList<String>) mDatalist);
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 20:
                if (data != null) {
                    List<String> photos = (List<String>) data.getExtras().getSerializable("photos");
                    //path是选择拍照或者图片的地址数组
                    //处理代码
                    mDatalist.clear();
                    mDatalist.addAll(photos);
                    GlideImgManager.loadImage(mContext, new File(mDatalist.get(0)), mImg);
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void requestError(String msg) {
        ToastUtils.showToast(msg);
    }

    @Override
    public void requestSuccess(String commonBean) {
        ToastUtils.showToast(commonBean);
        finish();
    }

    @Override
    public void requestSuccess(Payway commonBean) {

    }

    @Override
    public void requestPayWaySuccess(String commonBean) {

    }

    @Override
    public void uploadIdcardSuccess(UploadIdcard commonBean) {

    }

    @Override
    public void uploadIdcardSuccess2(UploadIdcard commonBean) {

    }

    @Override
    public void requestidcardImgCheckError(String msg) {

    }

    @Override
    public void idcardImgCheck(ImgCheck commonBean) {

    }
}
