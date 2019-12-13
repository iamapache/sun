package com.madaex.exchange.ui.market.adapter;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
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

public class KineQuickAdapter extends BaseQuickAdapter<Home, BaseViewHolder> {
    public int mCurrentPosition = -1;
    List<Home> mCategoryDatas = new ArrayList<>();
    List<Home> mCategoryDatas2 = new ArrayList<>();
    private int issort = 0;
    private int status = 3;
    private boolean isload = true;
    private int parentposition = 0;

    public KineQuickAdapter(int parentposition) {
        super(R.layout.item_mark);
        this.parentposition = parentposition;
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
            setNewData(mCategoryDatas2);
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
            setNewData(mCategoryDatas2);
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
            setNewData(mCategoryDatas2);
        }
    }

    @Override
    protected void convert(BaseViewHolder helper, final Home item) {
        helper.setText(R.id.endname, "/" + item.getExchangeType().toUpperCase());
        helper.setVisible(R.id.endname, true);
        helper.setText(R.id.coinname, item.getCurrentype().toUpperCase());
        helper.setText(R.id.coinnumber, mContext.getString(R.string.vol) + " " + DataUtil.thousand(item.getVolumn(), mContext))
                .setText(R.id.coinprice, "" + item.getCurrentPrice()).
                setText(R.id.coinrmb, "￥" + item.getSellRmb()).setText(R.id.bili, item.getRiseRate())
                .setText(R.id.maxprice, item.getHigh() + "").
                setText(R.id.minprice, item.getLow() + "");

        GlideImgManager.loadImage(mContext, item.getCoinImageUrl(), (ImageView) helper.getView(R.id.img_bank));
        TextView textView = helper.getView(R.id.tv_coll);
        if (item.getCollection() == 0) {
            Drawable drawableLeft = mContext.getResources().getDrawable(
                    R.mipmap.optional_unselect);
            textView.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,
                    null, null, null);
            textView.setCompoundDrawablePadding(10);
            helper.setText(R.id.tv_coll, mContext.getString(R.string.addfav));
        } else if (item.getCollection() == 1) {
            Drawable drawableLeft = mContext.getResources().getDrawable(
                    R.mipmap.optional_select);
            textView.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,
                    null, null, null);
            textView.setCompoundDrawablePadding(10);
            helper.setText(R.id.tv_coll, mContext.getString(R.string.cacelfav));
        }
        helper.setBackgroundRes(R.id.tv_time1, R.drawable.chart_tran_shape).setBackgroundRes(R.id.tv_time2, R.drawable.chart_tran_shape)
                .setBackgroundRes(R.id.tv_time3, R.drawable.chart_shape).setBackgroundRes(R.id.tv_time4, R.drawable.chart_tran_shape)
                .setBackgroundRes(R.id.tv_time5, R.drawable.chart_tran_shape);
        helper.setTextColor(R.id.tv_time1, mContext.getResources().getColor(R.color.common_text_6)).setTextColor(R.id.tv_time2, mContext.getResources().getColor(R.color.common_text_6))
                .setTextColor(R.id.tv_time3, mContext.getResources().getColor(R.color.common_text_1)).setTextColor(R.id.tv_time4, mContext.getResources().getColor(R.color.common_text_6))
                .setTextColor(R.id.tv_time5, mContext.getResources().getColor(R.color.common_text_6));

        if (item.getRiseRate().contains("-")) {
            helper.setText(R.id.bili, item.getRiseRate());
            helper.setBackgroundRes(R.id.bili, R.drawable.common_button_buleshape);
            helper.setTextColor(R.id.coinprice, mContext.getResources().getColor(R.color.common_green));
        } else {
            helper.setText(R.id.bili, "+" + item.getRiseRate());
            helper.setBackgroundRes(R.id.bili, R.drawable.common_button_redshape);
            helper.setTextColor(R.id.coinprice, mContext.getResources().getColor(R.color.common_red));
        }

        if (item.isShow) {
            helper.setGone(R.id.ll_line, true);
            setBg(status, helper, item);
        } else {
            helper.setGone(R.id.ll_line, false);
        }


        helper.setOnClickListener(R.id.tv_time1, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBg(status, helper, item);
                status = 1;
            }
        });
        helper.setOnClickListener(R.id.tv_time2, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBg(status, helper, item);
                status = 2;
            }
        });
        helper.setOnClickListener(R.id.tv_time3, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBg(status, helper, item);
                status = 3;
            }
        });
        helper.getView(R.id.tv_time4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBg(status, helper, item);
                status = 4;
            }
        });
        helper.getView(R.id.tv_time5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBg(status, helper, item);
                status = 5;
            }
        });

        helper.getView(R.id.ll_collection).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoCollection(item);

            }
        });
        helper.getView(R.id.ll_transation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(mContext, DealActivity.class);
                intent.putExtra("one_xnb", item.getCurrentype().toUpperCase());
                intent.putExtra("collect", item.getCollection());
                intent.putExtra("two_xnb", item.getExchangeType().toUpperCase());
                intent.putExtra(Constants.INFO, parentposition);
                intent.putExtra(Constants.INFO_CHILD, helper.getAdapterPosition());
                mContext.startActivity(intent);
            }
        });


        helper.getView(R.id.ll_content).setOnClickListener(new View.OnClickListener() {
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


    private void setBg(int status, BaseViewHolder helper, Home item) {
        if (status == 1) {
            helper.setBackgroundRes(R.id.tv_time1, R.drawable.chart_shape).setBackgroundRes(R.id.tv_time2, R.drawable.chart_tran_shape)
                    .setBackgroundRes(R.id.tv_time3, R.drawable.chart_tran_shape).setBackgroundRes(R.id.tv_time4, R.drawable.chart_tran_shape)
                    .setBackgroundRes(R.id.tv_time5, R.drawable.chart_tran_shape);
            helper.setTextColor(R.id.tv_time1, mContext.getResources().getColor(R.color.common_text_1)).setTextColor(R.id.tv_time2, mContext.getResources().getColor(R.color.common_text_6))
                    .setTextColor(R.id.tv_time3, mContext.getResources().getColor(R.color.common_text_6)).setTextColor(R.id.tv_time4, mContext.getResources().getColor(R.color.common_text_6))
                    .setTextColor(R.id.tv_time5, mContext.getResources().getColor(R.color.common_text_6));
            getKLine(helper, item, "1min");

        } else if (status == 2) {
            helper.setBackgroundRes(R.id.tv_time1, R.drawable.chart_tran_shape).setBackgroundRes(R.id.tv_time2, R.drawable.chart_shape)
                    .setBackgroundRes(R.id.tv_time3, R.drawable.chart_tran_shape).setBackgroundRes(R.id.tv_time4, R.drawable.chart_tran_shape)
                    .setBackgroundRes(R.id.tv_time5, R.drawable.chart_tran_shape);
            helper.setTextColor(R.id.tv_time1, mContext.getResources().getColor(R.color.common_text_6)).setTextColor(R.id.tv_time2, mContext.getResources().getColor(R.color.common_text_1))
                    .setTextColor(R.id.tv_time3, mContext.getResources().getColor(R.color.common_text_6)).setTextColor(R.id.tv_time4, mContext.getResources().getColor(R.color.common_text_6))
                    .setTextColor(R.id.tv_time5, mContext.getResources().getColor(R.color.common_text_6));
            getKLine(helper, item, "15min");
        } else if (status == 3) {
            helper.setBackgroundRes(R.id.tv_time1, R.drawable.chart_tran_shape).setBackgroundRes(R.id.tv_time2, R.drawable.chart_tran_shape)
                    .setBackgroundRes(R.id.tv_time3, R.drawable.chart_shape).setBackgroundRes(R.id.tv_time4, R.drawable.chart_tran_shape)
                    .setBackgroundRes(R.id.tv_time5, R.drawable.chart_tran_shape);
            helper.setTextColor(R.id.tv_time1, mContext.getResources().getColor(R.color.common_text_6)).setTextColor(R.id.tv_time2, mContext.getResources().getColor(R.color.common_text_6))
                    .setTextColor(R.id.tv_time3, mContext.getResources().getColor(R.color.common_text_1)).setTextColor(R.id.tv_time4, mContext.getResources().getColor(R.color.common_text_6))
                    .setTextColor(R.id.tv_time5, mContext.getResources().getColor(R.color.common_text_6));
            getKLine(helper, item, "1hour");
        } else if (status == 4) {
            helper.setBackgroundRes(R.id.tv_time1, R.drawable.chart_tran_shape).setBackgroundRes(R.id.tv_time2, R.drawable.chart_tran_shape)
                    .setBackgroundRes(R.id.tv_time3, R.drawable.chart_tran_shape).setBackgroundRes(R.id.tv_time4, R.drawable.chart_shape)
                    .setBackgroundRes(R.id.tv_time5, R.drawable.chart_tran_shape);
            helper.setTextColor(R.id.tv_time1, mContext.getResources().getColor(R.color.common_text_6)).setTextColor(R.id.tv_time2, mContext.getResources().getColor(R.color.common_text_6))
                    .setTextColor(R.id.tv_time3, mContext.getResources().getColor(R.color.common_text_6)).setTextColor(R.id.tv_time4, mContext.getResources().getColor(R.color.common_text_1))
                    .setTextColor(R.id.tv_time5, mContext.getResources().getColor(R.color.common_text_6));
            getKLine(helper, item, "1day");
        } else if (status == 5) {
            helper.setBackgroundRes(R.id.tv_time1, R.drawable.chart_tran_shape).setBackgroundRes(R.id.tv_time2, R.drawable.chart_tran_shape)
                    .setBackgroundRes(R.id.tv_time3, R.drawable.chart_tran_shape).setBackgroundRes(R.id.tv_time4, R.drawable.chart_tran_shape)
                    .setBackgroundRes(R.id.tv_time5, R.drawable.chart_shape);
            helper.setTextColor(R.id.tv_time1, mContext.getResources().getColor(R.color.common_text_6)).setTextColor(R.id.tv_time2, mContext.getResources().getColor(R.color.common_text_6))
                    .setTextColor(R.id.tv_time3, mContext.getResources().getColor(R.color.common_text_6)).setTextColor(R.id.tv_time4, mContext.getResources().getColor(R.color.common_text_6))
                    .setTextColor(R.id.tv_time5, mContext.getResources().getColor(R.color.common_text_1));
            getKLine(helper, item, "1week");
        }
    }

    private void setBg2(int status, BaseViewHolder helper, Home item) {
        if (status == 1) {
            helper.setBackgroundRes(R.id.tv_time1, R.drawable.chart_shape).setBackgroundRes(R.id.tv_time2, R.drawable.chart_tran_shape)
                    .setBackgroundRes(R.id.tv_time3, R.drawable.chart_tran_shape).setBackgroundRes(R.id.tv_time4, R.drawable.chart_tran_shape)
                    .setBackgroundRes(R.id.tv_time5, R.drawable.chart_tran_shape);
            helper.setTextColor(R.id.tv_time1, mContext.getResources().getColor(R.color.common_text_1)).setTextColor(R.id.tv_time2, mContext.getResources().getColor(R.color.common_text_6))
                    .setTextColor(R.id.tv_time3, mContext.getResources().getColor(R.color.common_text_6)).setTextColor(R.id.tv_time4, mContext.getResources().getColor(R.color.common_text_6))
                    .setTextColor(R.id.tv_time5, mContext.getResources().getColor(R.color.common_text_6));
        } else if (status == 2) {
            helper.setBackgroundRes(R.id.tv_time1, R.drawable.chart_tran_shape).setBackgroundRes(R.id.tv_time2, R.drawable.chart_shape)
                    .setBackgroundRes(R.id.tv_time3, R.drawable.chart_tran_shape).setBackgroundRes(R.id.tv_time4, R.drawable.chart_tran_shape)
                    .setBackgroundRes(R.id.tv_time5, R.drawable.chart_tran_shape);
            helper.setTextColor(R.id.tv_time1, mContext.getResources().getColor(R.color.common_text_6)).setTextColor(R.id.tv_time2, mContext.getResources().getColor(R.color.common_text_1))
                    .setTextColor(R.id.tv_time3, mContext.getResources().getColor(R.color.common_text_6)).setTextColor(R.id.tv_time4, mContext.getResources().getColor(R.color.common_text_6))
                    .setTextColor(R.id.tv_time5, mContext.getResources().getColor(R.color.common_text_6));
        } else if (status == 3) {
            helper.setBackgroundRes(R.id.tv_time1, R.drawable.chart_tran_shape).setBackgroundRes(R.id.tv_time2, R.drawable.chart_tran_shape)
                    .setBackgroundRes(R.id.tv_time3, R.drawable.chart_shape).setBackgroundRes(R.id.tv_time4, R.drawable.chart_tran_shape)
                    .setBackgroundRes(R.id.tv_time5, R.drawable.chart_tran_shape);
            helper.setTextColor(R.id.tv_time1, mContext.getResources().getColor(R.color.common_text_6)).setTextColor(R.id.tv_time2, mContext.getResources().getColor(R.color.common_text_6))
                    .setTextColor(R.id.tv_time3, mContext.getResources().getColor(R.color.common_text_1)).setTextColor(R.id.tv_time4, mContext.getResources().getColor(R.color.common_text_6))
                    .setTextColor(R.id.tv_time5, mContext.getResources().getColor(R.color.common_text_6));
        } else if (status == 4) {
            helper.setBackgroundRes(R.id.tv_time1, R.drawable.chart_tran_shape).setBackgroundRes(R.id.tv_time2, R.drawable.chart_tran_shape)
                    .setBackgroundRes(R.id.tv_time3, R.drawable.chart_tran_shape).setBackgroundRes(R.id.tv_time4, R.drawable.chart_shape)
                    .setBackgroundRes(R.id.tv_time5, R.drawable.chart_tran_shape);
            helper.setTextColor(R.id.tv_time1, mContext.getResources().getColor(R.color.common_text_6)).setTextColor(R.id.tv_time2, mContext.getResources().getColor(R.color.common_text_6))
                    .setTextColor(R.id.tv_time3, mContext.getResources().getColor(R.color.common_text_6)).setTextColor(R.id.tv_time4, mContext.getResources().getColor(R.color.common_text_1))
                    .setTextColor(R.id.tv_time5, mContext.getResources().getColor(R.color.common_text_6));
        } else if (status == 5) {
            helper.setBackgroundRes(R.id.tv_time1, R.drawable.chart_tran_shape).setBackgroundRes(R.id.tv_time2, R.drawable.chart_tran_shape)
                    .setBackgroundRes(R.id.tv_time3, R.drawable.chart_tran_shape).setBackgroundRes(R.id.tv_time4, R.drawable.chart_tran_shape)
                    .setBackgroundRes(R.id.tv_time5, R.drawable.chart_shape);
            helper.setTextColor(R.id.tv_time1, mContext.getResources().getColor(R.color.common_text_6)).setTextColor(R.id.tv_time2, mContext.getResources().getColor(R.color.common_text_6))
                    .setTextColor(R.id.tv_time3, mContext.getResources().getColor(R.color.common_text_6)).setTextColor(R.id.tv_time4, mContext.getResources().getColor(R.color.common_text_6))
                    .setTextColor(R.id.tv_time5, mContext.getResources().getColor(R.color.common_text_1));
        }
    }

    private void gotoCollection(Home item) {
        if (!TextUtils.isEmpty(SPUtils.getString(Constants.TOKEN, ""))) {
            mCallBack.doSomeThing(item);
        } else {
            mContext.startActivity(new Intent(mContext, LoginActivity.class));
        }
    }

    private void getKLine(BaseViewHolder helper, Home item, String type) {
        if (item.isShow) {
            KLineView combinedchart = helper.getView(R.id.lineChart);
            KLineDataManage kLineData = new KLineDataManage(mContext);
            combinedchart.initChart(true, false);
            HashMap params3 = new HashMap<>();
            params3.put("act", ConstantUrl.TRADE_HOME_INDEX_DETAIL_KLINE);
            params3.put("market", item.getCurrentype().toUpperCase() + "_" + "qc");
            params3.put("type", type);
            combinedchart.getGestureListenerCandle().setCoupleClick(new CoupleChartGestureListener.CoupleClick() {
                @Override
                public void singleClickListener() {
                    Intent intent = new Intent();
                    intent.setClass(mContext, DealActivity.class);
                    intent.putExtra("one_xnb", item.getCurrentype().toUpperCase());
                    intent.putExtra("collect", item.getCollection());
                    intent.putExtra("two_xnb", item.getExchangeType().toUpperCase());
                    intent.putExtra(Constants.INFO, parentposition);
                    intent.putExtra(Constants.INFO_CHILD, helper.getAdapterPosition());
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

//    @Override
//    public Filter getFilter() {
//        return new Filter() {
//            //执行过滤操作
//            @Override
//            protected FilterResults performFiltering(CharSequence charSequence) {
//                String charString = charSequence.toString();
//                mFilterList.clear();
//                mFilterList2.clear();
//                isfilter = true;
//                Logger.i(charString + "");
//                if (charString.isEmpty()) {
//                    //没有过滤的内容，则使用源数据
//                    mFilterList.addAll(mCategoryDatas);
//                    mFilterList2.addAll(mCategoryDatas2);
//                } else {
//                    List<Home> filteredList = new ArrayList<>();
//                    List<Home> filteredList2 = new ArrayList<>();
//                    for (Home str : mCategoryDatas) {
//                        //这里根据需求，添加匹配规则
//                        if (str.getCurrentype().contains(charString)) {
//                            filteredList.add(str);
//                        }
//                    }
//                    for (Home str : mCategoryDatas2) {
//                        //这里根据需求，添加匹配规则
//                        if (str.getCurrentype().contains(charString)) {
//                            filteredList2.add(str);
//                        }
//                    }
//                    mFilterList.addAll(filteredList);
//                    mFilterList2.addAll(filteredList2);
//                    Logger.i(mFilterList.size() + "");
//                }
//
//
//                FilterResults filterResults = new FilterResults();
//                filterResults.values = mFilterList;
//
//                return filterResults;
//            }
//
//            //把过滤后的值返回出来
//            @Override
//            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//                mFilterList = (List<Home>) filterResults.values;
//
//                for (Home home : mCategoryDatas) {
//                }
//
//                if (issort == 0) {
//                    setNewData(mFilterList2);
//                } else if (issort == 1) {
//                    Collections.sort(mFilterList, new Comparator<Home>() {
//                        @Override
//                        public int compare(Home o1, Home o2) {
//                            String gotime = o1.getRiseRate().replace("%", "");
//                            String returntime = o2.getRiseRate().replace("%", "");
//                            double hits1 = Double.valueOf(gotime);
//                            double hits0 = Double.valueOf(returntime);
//                            if (hits1 > hits0) {
//                                return 1;
//                            } else if (hits1 == hits0) {
//                                return 0;
//                            } else {
//                                return -1;
//                            }
//                        }
//                    });
//                    setNewData(mFilterList);
//                } else if (issort == 2) {
//                    Collections.sort(mFilterList, new Comparator<Home>() {
//                        @Override
//                        public int compare(Home o1, Home o2) {
//                            String gotime = o1.getRiseRate().replace("%", "");
//                            String returntime = o2.getRiseRate().replace("%", "");
//                            double hits1 = Double.valueOf(gotime);
//                            double hits0 = Double.valueOf(returntime);
//                            if (hits1 > hits0) {
//                                return -1;
//                            } else if (hits1 == hits0) {
//                                return 0;
//                            } else {
//                                return 1;
//                            }
//                        }
//                    });
//                    setNewData(mFilterList);
//                }
//
//            }
//        };
//    }

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
}
