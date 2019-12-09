package com.madaex.exchange.ui.finance.c2c.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseNetFragment;
import com.madaex.exchange.common.util.DataUtil;
import com.madaex.exchange.common.util.ToastUtils;
import com.madaex.exchange.ui.buy.bean.Event;
import com.madaex.exchange.ui.constant.ConstantUrl;
import com.madaex.exchange.ui.constant.Constants;
import com.madaex.exchange.ui.finance.c2c.activity.C2CEntrustDetailActivity;
import com.madaex.exchange.ui.finance.c2c.bean.EntrusTwo;
import com.madaex.exchange.ui.finance.c2c.bean.MyEntrust;
import com.madaex.exchange.ui.finance.c2c.contract.MyEntrustContract;
import com.madaex.exchange.ui.finance.c2c.presenter.MyEntrustPresenter;
import com.madaex.exchange.view.GlideImgManager;
import com.wc.widget.dialog.IosDialog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Method;
import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 项目：  sun
 * 类名：  EntrustOneFragment.java
 * 时间：  2019/5/13 19:30
 * 描述：  ${TODO}
 */
public class EntrustOneFragment extends BaseNetFragment<MyEntrustPresenter> implements MyEntrustContract.View {
    int pageNum = 1;
    boolean isRefresh = true;
    private BaseQuickAdapter mAdapter;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.swiperefreshlayout)
    SwipeRefreshLayout mSwiperefreshlayout;
    Unbinder unbinder;

    public static EntrustOneFragment newInstance(String string) {
        EntrustOneFragment fragment = null;
        if (fragment == null) {
            fragment = new EntrustOneFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putString(Constants.ENTRUSTCURRENT, string);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_entrustone;
    }

    @Override
    public void initInjector() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerview.setLayoutManager(linearLayoutManager);
        mAdapter = new BaseQuickAdapter<MyEntrust.DataBean.GoodsListBean, BaseViewHolder>(R.layout.item_platformentrust) {
            @Override
            protected void convert(BaseViewHolder helper, final MyEntrust.DataBean.GoodsListBean item) {
                ProgressBar progressBar = helper.getView(R.id.preview_progressBar);
                GlideImgManager.loadImage(mContext, item.getCoinImageUrl(), (ImageView) helper.getView(R.id.img_bank));
                helper.setText(R.id.type, item.getType_name()+ "")
                        .setText(R.id.minnumber, item.getNum_min() )
                .setText(R.id.num, item.getCoin_ename() + item.getNum())
                        .setText(R.id.price, "RMB" +item.getPrice())
                        .setText(R.id.mum, item.getCoin_ename() + item.getDeal_num())
                        .setText(R.id.status, R.string.Status) ;

                if(item.getStatus().equals("0")){
                    helper.setText(R.id.id, R.string.unpaids);
                }else if(item.getStatus().equals("1")){
                    helper.setText(R.id.id, R.string.paid);
                }else if(item.getStatus().equals("2")){
                    helper.setText(R.id.id, R.string.yok);
                }else if(item.getStatus().equals("3")){
                    helper.setText(R.id.id, R.string.cancel);
                }else if(item.getStatus().equals("4")){
                    helper.setText(R.id.id, R.string.close);
                }
                if(Double.valueOf( item.getDeal_num())==0){
                    helper.setGone(R.id.img_delete, false);
                }else {
                    helper.setGone(R.id.img_delete, true);
                }
                if (!TextUtils.isEmpty(item.getDeal_num()) || !TextUtils.isEmpty(item.getNum())) {
                    int pb = (int) (Double.valueOf(item.getDeal_num())*100 / Double.valueOf(item.getNum()));
                    progressBar.setProgress(pb);
                }
                helper.setText(R.id.img_delete, R.string.cacels);
                if (item.getType().equals("1")) {
                    helper.setText(R.id.coinname, R.string.sellnumber);
                    helper.setText(R.id.textminnumber, R.string.seee);
                    helper.setTextColor(R.id.mum, getResources().getColor(R.color.common_red)).setTextColor(R.id.num, getResources().getColor(R.color.common_red));
                    setProgressDrawable(progressBar, R.drawable.progressbar_red);
                } else if (item.getType().equals("2")) {
                    helper.setText(R.id.coinname, R.string.buynumber);
                    helper.setText(R.id.textminnumber, R.string.buuuu);
                    helper.setTextColor(R.id.mum, getResources().getColor(R.color.common_green)).setTextColor(R.id.num, getResources().getColor(R.color.common_green));
                    setProgressDrawable(progressBar, R.drawable.progressbar_green);
                }
                helper.getView(R.id.img_delete).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Dialog dialog = new IosDialog.Builder(mContext).setTitle(R.string.submitdelete).setTitleColor(ContextCompat.getColor(mContext, R.color.common_text_1)).setTitleSize(20)
                                .setMessage("").setMessageSize(14)
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
                                .setPositiveButton(R.string.submit, new IosDialog.OnClickListener() {
                                    @Override
                                    public void onClick(IosDialog dialog, View v) {
                                        dialog.dismiss();
                                        TreeMap params = new TreeMap<Object, Object>();
                                        params.put("act", ConstantUrl.FIAT_GOODS_CANCEL);
                                        params.put("goods_id",item.getId());
                                        mPresenter.cancel(DataUtil.sign(params));
                                    }
                                }).build();
                        dialog.show();

                    }
                });
            }
        };
        mRecyclerview.setAdapter(mAdapter);
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                isRefresh =false;
                getData();
            }
        }, mRecyclerview);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MyEntrust.DataBean.GoodsListBean item = (MyEntrust.DataBean.GoodsListBean) adapter.getItem(position);
                Intent intent =  new Intent(mContext, C2CEntrustDetailActivity.class);
                intent.putExtra("id",item.getId());
                startActivity(intent);
            }
        });

        mSwiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNum = 1;
                isRefresh =true;
                getData();
            }
        });
    }


    private static Drawable getMethod(String methodName, Object o, Object[] paras) {
        Drawable newDrawable = null;
        try {
            Class<?> c[] = new Class[2];
            c[0] = Drawable.class;
            c[1] = boolean.class;
            Method method = ProgressBar.class.getDeclaredMethod(methodName, c);
            method.setAccessible(true);
            newDrawable = (Drawable) method.invoke(o, paras);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return newDrawable;
    }

    @SuppressLint("NewApi")
    public static void setProgressDrawable(@NonNull ProgressBar bar, @DrawableRes int resId) {
        Drawable layerDrawable = bar.getResources().getDrawable(resId);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Drawable d = getMethod("tileify", bar, new Object[]{layerDrawable, false});
            bar.setProgressDrawable(d);
        } else {
            bar.setProgressDrawableTiled(layerDrawable);
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        if (event != null && event.getCode() == Constants.C2C) {
            String coin = event.getMsg();
            coin_ename = coin;
            pageNum = 1;
            isRefresh =true;
            getData();
        }
    }

    private String coin_ename="";

    @Override
    protected void initDatas() {
        coin_ename =   getArguments().getString(Constants.ENTRUSTCURRENT,"GRC");
        getData();
    }

    private void getData() {
        TreeMap params = new TreeMap<Object, Object>();
        params.put("act", ConstantUrl.FIAT_MY_ENTRUST_IN);
        params.put("coin_ename",coin_ename );
        params.put("curPage", pageNum+"");
        mPresenter.getData(DataUtil.sign(params));
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
    public void requestSuccess(String msg) {
        ToastUtils.showToast(msg);
        pageNum = 1;
        isRefresh =true;
        getData();
    }

    @Override
    public void requestSuccess(MyEntrust msg) {
        mSwiperefreshlayout.setRefreshing(false);
        if (msg != null) {
            setData(isRefresh, msg.getData().getGoods_list());

        } else {
            mAdapter.setEnableLoadMore(true);
        }
    }

    @Override
    public void requestSuccess(EntrusTwo msg) {

    }

    private void setData(boolean isRefresh, List data) {
        pageNum++;
        final int size = data == null ? 0 : data.size();
        if (isRefresh) {
            mAdapter.setEnableLoadMore(true);
            mAdapter.setNewData(data);

        } else {
            if (size > 0) {
                mAdapter.addData(data);

            }
        }
        if (size < 6) {
            //第一页如果不够一页就不显示没有更多数据布局
            mAdapter.loadMoreEnd(isRefresh);
        } else {
            mAdapter.loadMoreComplete();
        }
    }

    @Override
    public void requestError(String msg) {
        mSwiperefreshlayout.setRefreshing(false);
    }

    @Override
    public void nodata(String msg) {

    }
}
