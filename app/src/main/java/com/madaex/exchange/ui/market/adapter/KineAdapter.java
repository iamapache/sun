package com.madaex.exchange.ui.market.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.stockChart.CoupleChartGestureListener;
import com.github.mikephil.charting.stockChart.data.KLineDataManage;
import com.github.mikephil.charting.stockChart.view.KLineView;
import com.madaex.exchange.R;
import com.madaex.exchange.common.net.RetrofitHelper;
import com.madaex.exchange.common.rx.DefaultTransformer2;
import com.madaex.exchange.common.util.Base64Utils;
import com.madaex.exchange.common.util.DataUtil;
import com.madaex.exchange.common.util.FileEncryptionManager;
import com.madaex.exchange.common.util.SPUtils;
import com.madaex.exchange.ui.constant.ConstantUrl;
import com.madaex.exchange.ui.constant.Constants;
import com.madaex.exchange.ui.login.activity.LoginActivity;
import com.madaex.exchange.ui.market.activity.DealActivity;
import com.madaex.exchange.ui.market.bean.Home;
import com.madaex.exchange.view.GlideImgManager;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import io.reactivex.observers.DefaultObserver;

/**
 * 项目：  madaexchange
 * 类名：  KineQuickAdapter.java
 * 时间：  2018/9/14 18:00
 * 描述：  ${TODO}
 */

public class KineAdapter extends RecyclerView.Adapter<KineAdapter.MyViewHolder> {
    public int mCurrentPosition = -1;
    List<Home> mCategoryDatas = new ArrayList<>();
    List<Home> mCategoryDatas2 = new ArrayList<>();
    private int issort = 0;
    private int status = 3;
    private boolean isload = true;
    private Context mContext;
    private LayoutInflater inflater;

    @Override
    public KineAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_mark, parent, false);
        final KineAdapter.MyViewHolder holder = new KineAdapter.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(KineAdapter.MyViewHolder helper, int position) {
        Home item = mCategoryDatas2.get(position);
        if(item==null){
            return;
        }
        helper.endname.setText( "/" + item.getExchangeType().toUpperCase());
        helper.endname.setVisibility(View.VISIBLE);
        helper.coinname.setText(item.getCurrentype().toUpperCase());
        helper.coinnumber.setText(mContext.getString(R.string.vol) + " " + DataUtil.thousand(item.getVolumn(), mContext));
        helper.coinprice.setText("" + item.getCurrentPrice());
        helper.coinrmb.setText("￥" + item.getSellRmb());
        helper.bili.setText(item.getRiseRate());
        helper.maxprice.setText(item.getHigh() + "");
        helper.minprice.setText(item.getLow() + "");

        GlideImgManager.loadImage(mContext, item.getCoinImageUrl(),helper.img_bank);
        TextView textView = helper.tv_coll;
        if (item.getCollection() == 0) {
            Drawable drawableLeft = mContext.getResources().getDrawable(
                    R.mipmap.optional_unselect);
            textView.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,
                    null, null, null);
            textView.setCompoundDrawablePadding(10);
            helper.tv_coll.setText(mContext.getString(R.string.addfav));
        } else if (item.getCollection() == 1) {
            Drawable drawableLeft = mContext.getResources().getDrawable(
                    R.mipmap.optional_select);
            textView.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,
                    null, null, null);
            textView.setCompoundDrawablePadding(10);
            helper.tv_coll.setText(mContext.getString(R.string.cacelfav));
        }
        helper.tv_time1.setBackgroundResource(R.drawable.chart_tran_shape);
        helper.tv_time2.setBackgroundResource(R.drawable.chart_tran_shape);
        helper.tv_time3 .setBackgroundResource( R.drawable.chart_shape);
        helper.tv_time4 .setBackgroundResource( R.drawable.chart_tran_shape);
        helper.tv_time5 .setBackgroundResource(R.drawable.chart_tran_shape);
        helper.tv_time1.setTextColor( mContext.getResources().getColor(R.color.common_text_6));
        helper.tv_time2.setTextColor(mContext.getResources().getColor(R.color.common_text_6));
        helper.tv_time3.setTextColor( mContext.getResources().getColor(R.color.common_text_1));
        helper.tv_time4.setTextColor(mContext.getResources().getColor(R.color.common_text_6));
        helper.tv_time5.setTextColor( mContext.getResources().getColor(R.color.common_text_6));

        if (item.getRiseRate().contains("-")) {
            helper.bili.setText( item.getRiseRate());
            helper.bili.setBackgroundResource(R.drawable.common_button_buleshape);
            helper.coinprice.setTextColor(mContext.getResources().getColor(R.color.common_green));
        } else {
            helper.bili.setText( "+" + item.getRiseRate());
            helper.bili.setBackgroundResource( R.drawable.common_button_redshape);
            helper.coinprice.setTextColor( mContext.getResources().getColor(R.color.common_red));
        }

        if (item.isShow) {
            helper.ll_line.setVisibility(View.VISIBLE);
            setBg(status, helper, item);
        } else {
            helper.ll_line.setVisibility(View.GONE);
        }


        helper.tv_time1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBg(status, helper, item);
                status = 1;
            }
        });
        helper.tv_time2.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBg(status, helper, item);
                status = 2;
            }
        });
        helper.tv_time3.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBg(status, helper, item);
                status = 3;
            }
        });
        helper.tv_time4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBg(status, helper, item);
                status = 4;
            }
        });
        helper.tv_time5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBg(status, helper, item);
                status = 5;
            }
        });

        helper.ll_collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoCollection(item);

            }
        });
        helper.ll_transation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(mContext, DealActivity.class);
                intent.putExtra("one_xnb", item.getCurrentype().toUpperCase());
                intent.putExtra("collect", item.getCollection());
                intent.putExtra("two_xnb", item.getExchangeType().toUpperCase());
                mContext.startActivity(intent);
            }
        });


        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = helper.getAdapterPosition();
                if (mCategoryDatas == null || position < 0 || position >= mCategoryDatas.size()) {
                    return;
                }
                status = 3;
                isload = true;
                if (issort == 0) {
                    if (isfilter) {
                        Home data = mCategoryDatas2.get(position);
                        if (mCurrentPosition == -1) {
                            data.isShow = !data.isShow;
                            mCurrentPosition = position;
                        } else if (mCurrentPosition == position) {
                            data.isShow = !data.isShow;
                            mCurrentPosition = -1;
                        } else {
                            Home categoryData = mCategoryDatas2.get
                                    (mCurrentPosition);
                            categoryData.isShow = !categoryData.isShow;
                            data.isShow = !data.isShow;
                            mCurrentPosition = position;
                        }
                    } else {
                        Home data = mCategoryDatas.get(position);
                        if (mCurrentPosition == -1) {
                            data.isShow = !data.isShow;
                            mCurrentPosition = position;
                        } else if (mCurrentPosition == position) {
                            data.isShow = !data.isShow;
                            mCurrentPosition = -1;
                        } else {
                            Home categoryData = mCategoryDatas.get
                                    (mCurrentPosition);
                            categoryData.isShow = !categoryData.isShow;
                            data.isShow = !data.isShow;
                            mCurrentPosition = position;
                        }
                    }
                }
                if (issort == 1 || issort == 2) {
                    Home data = mCategoryDatas2.get(position);
                    if (mCurrentPosition == -1) {
                        data.isShow = !data.isShow;
                        mCurrentPosition = position;
                    } else if (mCurrentPosition == position) {
                        data.isShow = !data.isShow;
                        mCurrentPosition = -1;
                    } else {
                        Home categoryData = mCategoryDatas2.get
                                (mCurrentPosition);
                        categoryData.isShow = !categoryData.isShow;
                        data.isShow = !data.isShow;
                        mCurrentPosition = position;
                    }
                }
                mCallBack.doItem(mCurrentPosition);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCategoryDatas2.size();
    }

    public KineAdapter(Context context) {
        this.mContext = context;
        inflater = LayoutInflater.from(mContext);
    }

    public void setData(List<Home> mCategoryDatas) {
        this.mCategoryDatas = mCategoryDatas;
    }


    public void filter(String charString, int issort, int mCurrentPosition, boolean b) {
        this.issort = issort;
        this.mCurrentPosition = mCurrentPosition;
        mCategoryDatas2.clear();
        if (charString.isEmpty()) {
            //没有过滤的内容，则使用源数据
            this.mCategoryDatas2.addAll(mCategoryDatas);
        } else {
            for (Home str : mCategoryDatas) {
                //这里根据需求，添加匹配规则
                if (str.getCurrentype().contains(charString)) {
                    mCategoryDatas2.add(str);
                }
            }
        }
        Logger.i("<==>" + mCategoryDatas2.size());
        if (issort == 0) {
            notifyDataSetChanged();
        } else if (issort == 1) {
            Collections.sort(mCategoryDatas2, new Comparator<Home>() {
                @Override
                public int compare(Home o1, Home o2) {
                    String gotime = o1.getRiseRate().replace("%", "");
                    String returntime = o2.getRiseRate().replace("%", "");
                    double hits1 = Double.valueOf(gotime);
                    double hits0 = Double.valueOf(returntime);
                    if (hits1 > hits0) {
                        return 1;
                    } else if (hits1 == hits0) {
                        return 0;
                    } else {
                        return -1;
                    }
                }
            });
            notifyDataSetChanged();
        } else if (issort == 2) {
            Collections.sort(mCategoryDatas2, new Comparator<Home>() {
                @Override
                public int compare(Home o1, Home o2) {
                    String gotime = o1.getRiseRate().replace("%", "");
                    String returntime = o2.getRiseRate().replace("%", "");
                    double hits1 = Double.valueOf(gotime);
                    double hits0 = Double.valueOf(returntime);
                    if (hits1 > hits0) {
                        return -1;
                    } else if (hits1 == hits0) {
                        return 0;
                    } else {
                        return 1;
                    }
                }
            });
            notifyDataSetChanged();
        }
    }


    private void setBg(int status, MyViewHolder helper, Home item) {
        if (status == 1) {
            helper.tv_time1.setBackgroundResource( R.drawable.chart_shape);
            helper.tv_time2.setBackgroundResource( R.drawable.chart_tran_shape);
            helper.tv_time3.setBackgroundResource( R.drawable.chart_tran_shape);
            helper.tv_time4.setBackgroundResource( R.drawable.chart_tran_shape);
            helper.tv_time5 .setBackgroundResource(R.drawable.chart_tran_shape);
            helper.tv_time1.setTextColor( mContext.getResources().getColor(R.color.common_text_1));
            helper.tv_time2.setTextColor(mContext.getResources().getColor(R.color.common_text_6));
            helper.tv_time3 .setTextColor( mContext.getResources().getColor(R.color.common_text_6));
            helper.tv_time4 .setTextColor(mContext.getResources().getColor(R.color.common_text_6));
            helper.tv_time5 .setTextColor( mContext.getResources().getColor(R.color.common_text_6));
            getKLine(helper, item, "1min");

        } else if (status == 2) {
            helper.tv_time1.setBackgroundResource(R.drawable.chart_tran_shape);
            helper.tv_time2.setBackgroundResource( R.drawable.chart_shape);
            helper.tv_time3.setBackgroundResource( R.drawable.chart_tran_shape);
            helper.tv_time4 .setBackgroundResource( R.drawable.chart_tran_shape);
            helper.tv_time5  .setBackgroundResource(R.drawable.chart_tran_shape);
            helper.tv_time1.setTextColor( mContext.getResources().getColor(R.color.common_text_6));
            helper.tv_time2.setTextColor( mContext.getResources().getColor(R.color.common_text_1));
            helper.tv_time3.setTextColor( mContext.getResources().getColor(R.color.common_text_6));
            helper.tv_time4 .setTextColor( mContext.getResources().getColor(R.color.common_text_6));
            helper.tv_time5 .setTextColor( mContext.getResources().getColor(R.color.common_text_6));
            getKLine(helper, item, "15min");
        } else if (status == 3) {
            helper.tv_time1.setBackgroundResource(R.drawable.chart_tran_shape);
            helper.tv_time2 .setBackgroundResource( R.drawable.chart_tran_shape);
            helper.tv_time3   .setBackgroundResource( R.drawable.chart_shape);
            helper.tv_time4   .setBackgroundResource( R.drawable.chart_tran_shape);
            helper.tv_time5.setBackgroundResource( R.drawable.chart_tran_shape);
            helper.tv_time1.setTextColor(mContext.getResources().getColor(R.color.common_text_6));
            helper.tv_time2   .setTextColor( mContext.getResources().getColor(R.color.common_text_6));
            helper.tv_time3  .setTextColor(mContext.getResources().getColor(R.color.common_text_1));
            helper.tv_time4  .setTextColor( mContext.getResources().getColor(R.color.common_text_6));
            helper.tv_time5   .setTextColor( mContext.getResources().getColor(R.color.common_text_6));
            getKLine(helper, item, "1hour");
        } else if (status == 4) {
            helper.tv_time1.setBackgroundResource( R.drawable.chart_tran_shape);
            helper.tv_time1 .setBackgroundResource( R.drawable.chart_tran_shape);
            helper.tv_time1 .setBackgroundResource(R.drawable.chart_tran_shape);
            helper.tv_time1 .setBackgroundResource( R.drawable.chart_shape);
            helper.tv_time1 .setBackgroundResource( R.drawable.chart_tran_shape);
            helper.tv_time1.setTextColor( mContext.getResources().getColor(R.color.common_text_6));
            helper.tv_time1 .setTextColor(mContext.getResources().getColor(R.color.common_text_6));
            helper.tv_time1 .setTextColor( mContext.getResources().getColor(R.color.common_text_6));
            helper.tv_time1.setTextColor( mContext.getResources().getColor(R.color.common_text_1));
            helper.tv_time1.setTextColor( mContext.getResources().getColor(R.color.common_text_6));
            getKLine(helper, item, "1day");
        } else if (status == 5) {
            helper.tv_time1.setBackgroundResource( R.drawable.chart_tran_shape);
            helper.tv_time2 .setBackgroundResource( R.drawable.chart_tran_shape);
            helper.tv_time3 .setBackgroundResource( R.drawable.chart_tran_shape);
            helper.tv_time4 .setBackgroundResource( R.drawable.chart_tran_shape);
            helper.tv_time5 .setBackgroundResource(R.drawable.chart_shape);
            helper.tv_time1.setTextColor( mContext.getResources().getColor(R.color.common_text_6));
            helper.tv_time2 .setTextColor( mContext.getResources().getColor(R.color.common_text_6));
            helper.tv_time3 .setTextColor( mContext.getResources().getColor(R.color.common_text_6));
            helper.tv_time4 .setTextColor(mContext.getResources().getColor(R.color.common_text_6));
            helper.tv_time5 .setTextColor( mContext.getResources().getColor(R.color.common_text_1));
            getKLine(helper, item, "1week");
        }
    }


    private void gotoCollection(Home item) {
        if (!TextUtils.isEmpty(SPUtils.getString(Constants.TOKEN, ""))) {
            mCallBack.doSomeThing(item);
        } else {
            mContext.startActivity(new Intent(mContext, LoginActivity.class));
        }
    }

    private void getKLine(MyViewHolder helper, Home item, String type) {
        if (item.isShow) {
            KLineView combinedchart = helper.lineChart;
            KLineDataManage kLineData = new KLineDataManage(mContext);
            combinedchart.initChart(true, false);
            HashMap params3 = new HashMap<>();
            params3.put("act", ConstantUrl.TRADE_HOME_INDEX_DETAIL_KLINE);
            params3.put("market", item.getCurrentype().toUpperCase() + "_" + item.getExchangeType().toUpperCase());
            params3.put("type", type);
            combinedchart.getGestureListenerCandle().setCoupleClick(new CoupleChartGestureListener.CoupleClick() {
                @Override
                public void singleClickListener() {
                    Intent intent = new Intent();
                    intent.setClass(mContext, DealActivity.class);
                    intent.putExtra("collect", item.getCollection());
                    intent.putExtra("one_xnb", item.getCurrentype().toUpperCase());
                    intent.putExtra("two_xnb", item.getExchangeType().toUpperCase());
                    mContext.startActivity(intent);
                }
            });
            RetrofitHelper.getKLineAPI().getKLineResult(params3).compose(new DefaultTransformer2())
                    .subscribe(new DefaultObserver<String>() {
                        @Override
                        public void onNext(String jsonStr) {
                            //{"moneyType":"GRC","symbol":"eth","data":[[1535468400000,1948.21,1949.59,1938.95,1946.07,19893.112],
                            JSONObject object = null;
                            try {
                                FileEncryptionManager mFileEncryptionManager = FileEncryptionManager.getInstance();
                                String paramsStr = null;
                                try {
                                    paramsStr = new String(mFileEncryptionManager.decryptByPublicKey(Base64Utils.decode(jsonStr)));
                                    Logger.i("<==>data:kLineData" + paramsStr);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                object = new JSONObject(paramsStr);
                                kLineData.parseKlineData(object, "000001.IDX.SH", false);
                                combinedchart.setDataToChart(kLineData);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }

                    });
        }
    }

    boolean isfilter = false;


    public interface OnclikCallBack {
        void doSomeThing(Home item);

        void doItem(int item);
    }

    private OnclikCallBack mCallBack;

    public OnclikCallBack getCallBack() {
        return mCallBack;
    }

    public void setCallBack(OnclikCallBack callBack) {
        mCallBack = callBack;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        View classView;
        TextView endname;
        TextView coinname;
        TextView coinnumber;
        TextView minprice;
        TextView coinrmb;
        TextView coinprice;TextView maxprice;
        ImageView img_bank;
        TextView bili;
        TextView tv_coll;
        TextView tv_time1;
        TextView tv_time2;
        TextView tv_time3;
        TextView tv_time4;
        TextView tv_time5;
        LinearLayout ll_line;
        LinearLayout ll_collection;
        LinearLayout ll_transation;
        KLineView lineChart;
        public MyViewHolder(View view) {
            super(view);
            classView = itemView;
            endname = (TextView) view.findViewById(R.id.endname);
            coinname = (TextView) view.findViewById(R.id.coinname);
            coinnumber = (TextView) view.findViewById(R.id.coinnumber);
            maxprice = (TextView) view.findViewById(R.id.maxprice);
            minprice = (TextView) view.findViewById(R.id.minprice);
            coinrmb = (TextView) view.findViewById(R.id.coinrmb);
            coinprice = (TextView) view.findViewById(R.id.coinprice);
            img_bank = (ImageView) view.findViewById(R.id.img_bank);
            bili = (TextView) view.findViewById(R.id.bili);
            tv_coll = (TextView) view.findViewById(R.id.tv_coll);
            tv_time1 = (TextView) view.findViewById(R.id.tv_time1);
            tv_time2 = (TextView) view.findViewById(R.id.tv_time2);
            tv_time3 = (TextView) view.findViewById(R.id.tv_time3);
            tv_time4 = (TextView) view.findViewById(R.id.tv_time4);
            tv_time5 = (TextView) view.findViewById(R.id.tv_time5);
            ll_line = (LinearLayout) view.findViewById(R.id.ll_line);
            ll_collection = (LinearLayout) view.findViewById(R.id.ll_collection);
            ll_transation = (LinearLayout) view.findViewById(R.id.ll_transation);
            lineChart =view.findViewById(R.id.lineChart);
        }

    }

    public static interface OnItemClickListener {
        void onItemClick(List<String> list);

        void onItemLongClick(View view);
    }

    public OnItemClickListener mItemClickListener;

    public OnItemClickListener getItemClickListener() {
        return mItemClickListener;
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }
}
