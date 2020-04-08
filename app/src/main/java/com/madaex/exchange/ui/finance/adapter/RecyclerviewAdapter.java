package com.madaex.exchange.ui.finance.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.madaex.exchange.R;
import com.madaex.exchange.common.util.ToastUtils;
import com.madaex.exchange.ui.finance.activity.BillActivity;
import com.madaex.exchange.ui.finance.activity.TabBuyCoinActivity;
import com.madaex.exchange.ui.finance.activity.TabSellerActivity;
import com.madaex.exchange.ui.finance.bean.Asset;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 项目：  madaexchange
 * 类名：  RecyclerviewAdapter.java
 * 时间：  2018/9/7 10:22
 * 描述：  ${TODO}
 */

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.ViewHolder> implements Filterable {


    private Context mContext;
    private List<Asset.DataBean.XnbListBean> data;
    private List<Asset.DataBean.XnbListBean> mFilterList = new ArrayList<>();
    private boolean isshow;
    String wallet_type ="";
    public RecyclerviewAdapter(Context context, List<Asset.DataBean.XnbListBean> data, String wallet_type ) {
        this.mContext = context;
        this.data = data;
        this.mFilterList = data;
        this.wallet_type  =wallet_type;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_asset, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Asset.DataBean.XnbListBean item = mFilterList.get(position);
        holder.mAssetName.setText(item.getXnb_name());
        holder.mAvail.setText(mContext.getString(R.string.alltotal) + "$" + item.getAssets());
        holder.mFrozen.setText(mContext.getString(R.string.Frozen) + item.getFrozen());
        holder.mAssets.setText(item.getAvail() + "");

        holder.mLlBuycoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (item.getIs_recharge() == 1) {
                    Intent intent = new Intent();
                    intent.setClass(mContext, TabBuyCoinActivity.class);
                    intent.putExtra("address", item.getAddress());
                    intent.putExtra("xnb", item.getCoin_id()+"");
                    intent.putExtra("xnb_name", item.getXnb_name());
                    intent.putExtra("wallet_type", wallet_type);
                    mContext.startActivity(intent);
                } else if (item.getIs_recharge() == 0) {
                    ToastUtils.showToast("该币暂时不能充值");
//                    Intent intent = new Intent();
//                    intent.setClass(mContext, BuyBillActivity.class);
//                    intent.putExtra("address", item.getAddress());
//                    intent.putExtra("xnb", item.getXnb());
//                    intent.putExtra("xnb_name", item.getXnb_name());
//                    mContext.startActivity(intent);
                }
            }
        });
        holder.mLlSellercoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (item.getIs_support_cash()==1) {
                    Intent intent = new Intent();
                    intent.setClass(mContext, TabSellerActivity.class);
                    intent.putExtra("xnb", item.getCoin_id()+"");
                    intent.putExtra("xnb_name", item.getXnb_name());
                    intent.putExtra("wallet_type", wallet_type);
                    intent.putExtra("coin_id", item.getCoin_id()+"");
                    mContext.startActivity(intent);
                } else if (item.getIs_support_cash()==2) {
                    ToastUtils.showToast(R.string.comingsoon);

                }

            }
        });
        holder.mLlBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(mContext, BillActivity.class);
                intent.putExtra("xnb", item.getCoin_id()+"");
                intent.putExtra("xnb_name", item.getXnb_name());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFilterList.size();
    }


    public void filter(String string, boolean isshow) {
        getFilter().filter(string);
        this.isshow = isshow;
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            //执行过滤操作
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                Logger.i(charString + "");
                if (charString.isEmpty()) {
                    //没有过滤的内容，则使用源数据
                    List<Asset.DataBean.XnbListBean> filteredList = new ArrayList<>();
                    for (Asset.DataBean.XnbListBean str : data) {
                        //这里根据需求，添加匹配规则
                        if (isshow) {
                            if (Double.valueOf(str.getAssets()) > 1) {
                                filteredList.add(str);
                            }
                        } else {
                            filteredList.add(str);
                        }
                    }

                    mFilterList = filteredList;
                } else {
                    List<Asset.DataBean.XnbListBean> filteredList = new ArrayList<>();
                    for (Asset.DataBean.XnbListBean str : data) {
                        //这里根据需求，添加匹配规则
                        if (isshow) {
                            if (str.getXnb().toLowerCase().contains(charString.toLowerCase()) && Double.valueOf(str.getAssets()) > 1) {
                                filteredList.add(str);
                            }
                        } else {
                            if (str.getXnb().toLowerCase().contains(charString.toLowerCase())) {
                                filteredList.add(str);
                            }
                        }

                    }

                    mFilterList = filteredList;
                    Logger.i(mFilterList.size() + "");
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilterList;
                return filterResults;
            }

            //把过滤后的值返回出来
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilterList = (List<Asset.DataBean.XnbListBean>) filterResults.values;
                Logger.i(mFilterList.size() + "");
                notifyDataSetChanged();
            }
        };
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.asset_name)
        TextView mAssetName;
        @BindView(R.id.assets)
        TextView mAssets;
        @BindView(R.id.frozen)
        TextView mFrozen;
        @BindView(R.id.avail)
        TextView mAvail;
        @BindView(R.id.ll_buycoin)
        LinearLayout mLlBuycoin;
        @BindView(R.id.ll_sellercoin)
        LinearLayout mLlSellercoin;
        @BindView(R.id.ll_bill)
        LinearLayout mLlBill;
        @BindView(R.id.cardView)
        CardView mCardView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
