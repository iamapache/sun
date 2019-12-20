package com.madaex.exchange.ui.finance.c2c.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseNetLazyFragment;
import com.madaex.exchange.common.util.DataUtil;
import com.madaex.exchange.common.util.EmptyUtils;
import com.madaex.exchange.common.view.CustomPopWindow;
import com.madaex.exchange.common.view.SegmentView;
import com.madaex.exchange.ui.constant.ConstantUrl;
import com.madaex.exchange.ui.constant.Constants;
import com.madaex.exchange.ui.finance.c2c.activity.C2CTransationActivity;
import com.madaex.exchange.ui.finance.c2c.bean.PlatformEntrust;
import com.madaex.exchange.ui.finance.c2c.contract.PlatformEntrustContract;
import com.madaex.exchange.ui.finance.c2c.presenter.PlatformEntrustPresenter;
import com.madaex.exchange.ui.mine.bean.User;
import com.madaex.exchange.view.GlideImgManager;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 项目：  sun
 * 类名：  PlatformEntrustFragment.java
 * 时间：  2019/5/9 14:34
 * 描述：  ${TODO}
 */
public class PlatformEntrustFragment extends BaseNetLazyFragment<PlatformEntrustPresenter> implements PlatformEntrustContract.View {
    Unbinder unbinder;
    @BindView(R.id.segmentview)
    SegmentView mSegmentview;
    @BindView(R.id.delete)
    Button mDelete;
    private BaseQuickAdapter mAdapter;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.ll_pop)
    LinearLayout mLlPop;
    @BindView(R.id.tv_all)
    TextView mTvAll;
    @BindView(R.id.tv_price)
    TextView mTvPrice;
    @BindView(R.id.swiperefreshlayout)
    SwipeRefreshLayout mSwiperefreshlayout;
    private CustomPopWindow mCustomPopWindow;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_platformentrust;
    }

    public static PlatformEntrustFragment newInstance(String string) {
        PlatformEntrustFragment fragment = null;
        if (fragment == null) {
            fragment = new PlatformEntrustFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putString(Constants.ENTRUSTCURRENT, string);
        fragment.setArguments(bundle);
        return fragment;
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
        mAdapter = new BaseQuickAdapter<PlatformEntrust.DataBean.GoodsListBean, BaseViewHolder>(R.layout.item_platformentrust) {
            @Override
            protected void convert(BaseViewHolder helper, final PlatformEntrust.DataBean.GoodsListBean item) {
                ProgressBar progressBar = helper.getView(R.id.preview_progressBar);
                GlideImgManager.loadImage(mContext, item.getCoinImageUrl(), (ImageView) helper.getView(R.id.img_bank));
                helper.setText(R.id.type, item.getType_name() + "")
                        .setText(R.id.num, item.getCoin_ename() +item.getNum())
                        .setText(R.id.minnumber, item.getNum_min() )
                        .setText(R.id.price, "RMB" + item.getPrice())
                        .setText(R.id.mum, item.getCoin_ename() + (Double.valueOf(item.getNum()) - Double.valueOf(item.getDeal_num())))
                        .setText(R.id.id, item.getUser_id() + "");

                if(Double.valueOf(item.getNum()) - Double.valueOf(item.getDeal_num())==0){
                    helper.setGone(R.id.img_delete, false);
                }else {
                    helper.setGone(R.id.img_delete, true);
                }
                if (!TextUtils.isEmpty(item.getDeal_num()) || !TextUtils.isEmpty(item.getNum())) {
                    int pb = (int) (Double.valueOf(item.getDeal_num()) * 100 / Double.valueOf(item.getNum()));
                    progressBar.setProgress(pb);
                }
                if (item.getType().equals("1")) {
                    helper.setText(R.id.img_delete, getString(R.string.item_seller));
                    helper.setText(R.id.coinname, R.string.sellnumber);
                    helper.setText(R.id.textminnumber, R.string.seee);
                    helper.setTextColor(R.id.mum, getResources().getColor(R.color.common_red)).setTextColor(R.id.num, getResources().getColor(R.color.common_red));
                    setProgressDrawable(progressBar, R.drawable.progressbar_red);
                } else if (item.getType().equals("2")) {
                    helper.setText(R.id.img_delete, getString(R.string.item_buy));
                    helper.setText(R.id.coinname, R.string.buynumber);
                    helper.setText(R.id.textminnumber, R.string.buuuu);
                    helper.setTextColor(R.id.mum, getResources().getColor(R.color.common_green)).setTextColor(R.id.num, getResources().getColor(R.color.common_green));
                    setProgressDrawable(progressBar, R.drawable.progressbar_green);
                }
                helper.getView(R.id.img_delete).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, C2CTransationActivity.class);
                        intent.putExtra("type", item.getType());
                        intent.putExtra("price", item.getPrice());
                        intent.putExtra("goods_id", item.getId());
                        intent.putExtra("num", Double.valueOf(item.getNum()) - Double.valueOf(item.getDeal_num()));
                        startActivity(intent);
                    }
                });


            }
        };
        mRecyclerview.setAdapter(mAdapter);
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                isRefresh = false;
                getData(time_type, price_type);
            }
        }, mRecyclerview);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });

        mSwiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNum = 1;
                isRefresh = true;
                getData(time_type, price_type);
            }
        });

        mSegmentview.setOnSegmentViewClickListener(new SegmentView.onSegmentViewClickListener() {
            @Override
            public void onSegmentViewClick(View view, int postion) {
                switch (postion) {
                    case 0:
                        if (EmptyUtils.isNotEmpty(mAdapter)) {
                            pageNum = 1;
                            isRefresh = true;
                            getData(time_type, price_type);
                            sort = 1;
                        }
                        break;
                    case 1:
                        if (EmptyUtils.isNotEmpty(mAdapter)) {
                            pageNum = 1;
                            isRefresh = true;
                            getData(time_type, price_type);
                            sort = 2;

                        }
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private int sort = 0;


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


    @Override
    protected void initDatas() {
        getData(time_type, price_type);
        TreeMap params = new TreeMap<>();
        params.put("act", ConstantUrl.USER_USERINFO_STATUS);
        mPresenter.load(DataUtil.sign(params));
    }

    private String time_type = "0";
    private String price_type = "0";
    int pageNum = 1;
    boolean isRefresh = true;


    private void getData(String time_type, String price_type) {
        TreeMap params = new TreeMap<Object, Object>();
        params.put("act", ConstantUrl.FIAT_ENTRUST_PLATFORM);
        params.put("time_type", time_type);
        params.put("price_type", price_type);
        params.put("curPage", pageNum + "");
        mPresenter.getData(DataUtil.sign(params));
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void requestSuccess(String msg) {

    }

    @Override
    public void requestError(String msg) {
        mSwiperefreshlayout.setRefreshing(false);
    }

    @Override
    public void sendMsgSuccess(PlatformEntrust.DataBean msg) {
        mSwiperefreshlayout.setRefreshing(false);

        if (msg != null) {
            setData(isRefresh, msg.getGoods_list());

        } else {
            mAdapter.setEnableLoadMore(true);
        }
    }

    @Override
    public void requestSuccess(User user) {
        if(EmptyUtils.isNotEmpty(user.getData().getIs_merchants())){
            if (user.getData().getIs_merchants()==0) {
                mDelete.setVisibility(View.GONE);
            } else if (user.getData().getIs_merchants()==1) {
                mDelete.setVisibility(View.VISIBLE);
            }
        }

    }

    private void setData(boolean isRefresh, List<PlatformEntrust.DataBean.GoodsListBean> data) {
        pageNum++;
        final int size = data == null ? 0 : data.size();
        if (isRefresh) {
            mAdapter.setEnableLoadMore(true);
            List<PlatformEntrust.DataBean.GoodsListBean> goodsListBeanList = new ArrayList<>();
            for (PlatformEntrust.DataBean.GoodsListBean goodsListBean : data) {
                if (sort == 1) {
                    if (goodsListBean.getType().equals("1")) {
                        goodsListBeanList.add(goodsListBean);
                    }
                }
                if (sort == 2) {
                    if (goodsListBean.getType().equals("2")) {
                        goodsListBeanList.add(goodsListBean);
                    }
                }
                if (sort == 0) {
                    goodsListBeanList.add(goodsListBean);
                }
            }
            mAdapter.setNewData(goodsListBeanList);

        } else {
            if (size > 0) {
                List<PlatformEntrust.DataBean.GoodsListBean> goodsListBeanList = new ArrayList<>();
                for (PlatformEntrust.DataBean.GoodsListBean goodsListBean : data) {
                    if (sort == 1) {
                        if (goodsListBean.getType().equals("1")) {
                            goodsListBeanList.add(goodsListBean);
                        }
                    }
                    if (sort == 2) {
                        if (goodsListBean.getType().equals("2")) {
                            goodsListBeanList.add(goodsListBean);
                        }
                    }
                    if (sort == 0) {
                        goodsListBeanList.add(goodsListBean);
                    }
                }
                mAdapter.addData(goodsListBeanList);
            }
        }
        if (size < 6) {
            //第一页如果不够一页就不显示没有更多数据布局
            mAdapter.loadMoreEnd(isRefresh);
        } else {
            mAdapter.loadMoreComplete();
        }
    }

    @OnClick({R.id.tv_all, R.id.tv_price, R.id.delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_all:
                showAllPopMenu();
                break;
            case R.id.tv_price:
                showPricePopMenu();
                break;
            case R.id.delete:
                Intent intent = new Intent(mContext, C2CTransationActivity.class);

                startActivity(intent);
                break;
        }
    }

    private void showPricePopMenu() {
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.pop_entrust_pricedesc, null);
        //处理popWindow 显示内容
        handleLogicPrice(contentView);
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
                .showAsDropDown(mTvPrice, 0, 30);
    }

    private void handleLogicPrice(View contentView) {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCustomPopWindow != null) {
                    mCustomPopWindow.dissmiss();
                }
                switch (v.getId()) {
                    case R.id.ll_item1:
                        pageNum = 1;
                        isRefresh = true;
                        price_type = "0";
                        mTvPrice.setText(R.string.Descend);
                        getData(time_type, price_type);
                        break;

                    case R.id.ll_item2:
                        pageNum = 1;
                        isRefresh = true;
                        price_type = "1";
                        mTvPrice.setText(R.string.Ascending);
                        getData(time_type, price_type);


                }
            }
        };
        contentView.findViewById(R.id.ll_item1).setOnClickListener(listener);
        contentView.findViewById(R.id.ll_item2).setOnClickListener(listener);
    }

    private void showAllPopMenu() {
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.pop_entrust_time, null);
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
                .showAsDropDown(mLlPop, 0, 0);

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
                        mTvAll.setText(R.string.Unlimited);
                        pageNum = 1;
                        isRefresh = true;
                        time_type = "0";
                        getData(time_type, price_type);
                        break;
                    case R.id.ll_item2:
                        mTvAll.setText(R.string.Aday);
                        pageNum = 1;
                        isRefresh = true;
                        time_type = "1";
                        getData(time_type, price_type);
                        break;
                    case R.id.ll_item3:
                        mTvAll.setText(R.string.Aweek);
                        pageNum = 1;
                        isRefresh = true;
                        time_type = "2";
                        getData(time_type, price_type);
                        break;
                }
            }
        };
        contentView.findViewById(R.id.ll_item1).setOnClickListener(listener);
        contentView.findViewById(R.id.ll_item2).setOnClickListener(listener);
        contentView.findViewById(R.id.ll_item3).setOnClickListener(listener);
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


}
