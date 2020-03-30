package com.madaex.exchange.ui.finance.contracts.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.madaex.exchange.R;
import com.madaex.exchange.ui.finance.contracts.activity.OtherTransferActivity;
import com.madaex.exchange.ui.finance.contracts.activity.TransferActivity;
import com.madaex.exchange.ui.finance.contracts.bean.ContractAsset;
import com.madaex.exchange.view.GlideImgManager;
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

public class ContractRecyclerviewAdapter extends RecyclerView.Adapter<ContractRecyclerviewAdapter.ViewHolder> implements Filterable {


    private Context mContext;
    private List<List<ContractAsset.DataBean.XnbListBean>> data;
    private List<List<ContractAsset.DataBean.XnbListBean>> mFilterList = new ArrayList<List<ContractAsset.DataBean.XnbListBean>>();
    private boolean isshow;
    String wallet_type ="";
    public ContractRecyclerviewAdapter(Context context, List<List<ContractAsset.DataBean.XnbListBean>> data, String wallet_type ) {
        this.mContext = context;
        this.data = data;

        this.mFilterList = data;
        this.wallet_type  =wallet_type;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_contract, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final List<ContractAsset.DataBean.XnbListBean> xnbListBeans = mFilterList.get(position);
        ContractAsset.DataBean.XnbListBean item=xnbListBeans.get(0);
        ContractAsset.DataBean.XnbListBean item2=xnbListBeans.get(1);
        holder.number.setText(item.getXnb_name());
        holder.type.setText(item.getAssets());
        GlideImgManager.loadImage(mContext, item.getIcon(), holder.imageView);

        holder.number2.setText(item2.getXnb_name());
        holder.type2.setText(item2.getAssets());
        GlideImgManager.loadImage(mContext, item2.getIcon(), holder.imageView2);

        holder.item_contract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (item.getXnb_name() .equals("USDT")) {
                    Intent intent = new Intent();
                    intent.setClass(mContext, TransferActivity.class);
                    intent.putExtra("bean", item);
                    mContext.startActivity(intent);
                } else {
                    Intent intent = new Intent();
                    intent.setClass(mContext, OtherTransferActivity.class);
                    intent.putExtra("bean", item);
                    mContext.startActivity(intent);
                }
            }
        });

        holder.item_contract2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (item2.getXnb_name() .equals("USDT")) {
                    Intent intent = new Intent();
                    intent.setClass(mContext, TransferActivity.class);
                    intent.putExtra("bean", item2);
                    mContext.startActivity(intent);
                } else {
                    Intent intent = new Intent();
                    intent.setClass(mContext, OtherTransferActivity.class);
                    intent.putExtra("bean", item2);
                    mContext.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if(mFilterList==null||mFilterList.size()==0){
            return 0;
        }
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
                    List<List<ContractAsset.DataBean.XnbListBean>> filteredList = new ArrayList<>();
                    for (List<ContractAsset.DataBean.XnbListBean> str : data) {
                        //这里根据需求，添加匹配规则
                        if (isshow) {
                            if (Double.valueOf(str.get(0).getAssets()) > 1||Double.valueOf(str.get(1).getAssets()) > 1) {
                                filteredList.add(str);
                            }
                        } else {
                            filteredList.add(str);
                        }
                    }

                    mFilterList = filteredList;
                } else {
                    List<List<ContractAsset.DataBean.XnbListBean>> filteredList = new ArrayList<>();
                    for (List<ContractAsset.DataBean.XnbListBean> str : data) {
                        //这里根据需求，添加匹配规则
                        if (isshow) {
                            if (str.get(0).getXnb_name().toLowerCase().contains(charString.toLowerCase()) && Double.valueOf(str.get(0).getXnb_name()) > 1) {
                                filteredList.add(str);
                            }else {
                                if (str.get(1).getXnb_name().toLowerCase().contains(charString.toLowerCase()) && Double.valueOf(str.get(1).getXnb_name()) > 1) {
                                    filteredList.add(str);
                                }
                            }
                        } else {
                            if (str.get(0).getXnb_name().toLowerCase().contains(charString.toLowerCase())) {
                                filteredList.add(str);
                            }else {
                                if (str.get(1).getXnb_name().toLowerCase().contains(charString.toLowerCase())) {
                                    filteredList.add(str);
                                }
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
                mFilterList = (List<List<ContractAsset.DataBean.XnbListBean>>) filterResults.values;
//                Logger.i(mFilterList.size() + "");
                notifyDataSetChanged();
            }
        };
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.number)
        TextView number;
        @BindView(R.id.type)
        TextView type;
        @BindView(R.id.item_contract)
        LinearLayout item_contract;
        @BindView(R.id.imageView)
        ImageView imageView;

        @BindView(R.id.number2)
        TextView number2;
        @BindView(R.id.type2)
        TextView type2;
        @BindView(R.id.item_contract2)
        LinearLayout item_contract2;
        @BindView(R.id.imageView2)
        ImageView imageView2;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
