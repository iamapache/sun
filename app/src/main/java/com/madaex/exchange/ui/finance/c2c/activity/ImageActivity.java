package com.madaex.exchange.ui.finance.c2c.activity;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseActivity;
import com.madaex.exchange.common.util.ToastUtils;
import com.madaex.exchange.ui.finance.c2c.bean.EntrusTwo;
import com.madaex.exchange.view.GlideImgManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 项目：  sun
 * 类名：  ImageActivity.java
 * 时间：  2019/5/15 16:36
 * 描述：  ${TODO}
 */
public class ImageActivity extends BaseActivity {
    @BindView(R.id.image)
    ImageView mImage;
    @BindView(R.id.edit)
    Button mEdit;
    @BindView(R.id.toolbar_title_tv)
    TextView mToolbarTitleTv;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_image;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initDatas() {
        EntrusTwo.DataBean.GoodsListBean.PaymentListBean paymentListBean = getIntent().getParcelableExtra("bean");
        if(paymentListBean.getType().equals("1")){
            mToolbarTitleTv.setText(R.string.WeChatPay);
        }else  if(paymentListBean.getType().equals("2")){
            mToolbarTitleTv.setText(R.string.Alipay);
        }
        GlideImgManager.loadImage(mContext, paymentListBean.getImg(), mImage);
        mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Glide.with(mContext).load( paymentListBean.getImg()).asBitmap().toBytes().into(new SimpleTarget<byte[]>() {
                    @Override
                    public void onResourceReady(byte[] bytes, GlideAnimation<? super byte[]> glideAnimation) {
                        try {
                            savaBitmap(System.currentTimeMillis()+"", bytes);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    public void savaBitmap(String imgName, byte[] bytes) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String filePath = null;
            FileOutputStream fos = null;
            try {
                filePath = Environment.getExternalStorageDirectory().getCanonicalPath() + "/MyImg";
                File imgDir = new File(filePath);
                if (!imgDir.exists()) {
                    imgDir.mkdirs();
                }
                imgName = filePath + "/" + imgName;
                fos = new FileOutputStream(imgName);
                fos.write(bytes);
                ToastUtils.showToast("图片已保存到" + filePath);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fos != null) {
                        fos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            ToastUtils.showToast("请检查SD卡是否可用");
        }
    }

    @OnClick({R.id.toolbar_left_btn_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_btn_ll:
                finish();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
