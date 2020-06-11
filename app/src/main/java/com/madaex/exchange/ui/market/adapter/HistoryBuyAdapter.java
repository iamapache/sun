package com.madaex.exchange.ui.market.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.utils.DataTimeUtil;
import com.madaex.exchange.R;
import com.madaex.exchange.ui.market.bean.HistoryDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目：  madaexchange
 * 类名：  BuyAdapter.java
 * 时间：  2018/11/22 11:41
 * 描述：  ${TODO}
 */
public class HistoryBuyAdapter extends  RecyclerView.Adapter<HistoryBuyAdapter.MyViewHolder> {

    private List<HistoryDetail.DataBean> mDatas=new ArrayList<>();
    private Context mContext;
    private LayoutInflater inflater;
    public HistoryBuyAdapter(Context context){
        this. mContext=context;
        inflater=LayoutInflater. from(mContext);
    }

    @Override
    public int getItemCount() {

        return mDatas.size();
    }

    //填充onCreateViewHolder方法返回的holder中的控件
    @Override
    public void onBindViewHolder(MyViewHolder holder,  int position) {
        holder.price.setText( mDatas.get(position).getPrice());
        holder.number.setText( mDatas.get(position).getAmount());
        holder.time.setText(DataTimeUtil.secToDate3(Long.valueOf(mDatas.get(position).getDate())));
        if(mDatas.get(position).getType().equals("ask")){
            holder.time.setTextColor(mContext.getResources().getColor(R.color.green));
            holder.price.setTextColor(mContext.getResources().getColor(R.color.green));
            holder.number.setTextColor(mContext.getResources().getColor(R.color.green));
        }else {
            holder.time.setTextColor(mContext.getResources().getColor(R.color.red));
            holder.price.setTextColor(mContext.getResources().getColor(R.color.red));
            holder.number.setTextColor(mContext.getResources().getColor(R.color.red));
        }
    }

    //重写onCreateViewHolder方法，返回一个自定义的ViewHolder
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_trans_history,parent, false);
        final  MyViewHolder holder= new MyViewHolder(view);
        return holder;
    }

    public void setNewData(List<HistoryDetail.DataBean> datas) {
        mDatas.clear();
        this. mDatas=datas;
        notifyDataSetChanged();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        View classView;
        TextView price;
        TextView time;
        TextView number;
        public MyViewHolder(View view) {
            super(view);
            classView = itemView;
            price=(TextView) view.findViewById(R.id.price);
            time=(TextView) view.findViewById(R.id.time);
            number=(TextView) view.findViewById(R.id.number);
        }

    }
    public static interface OnItemClickListener {
        void onItemClick(List<String> list);
        void onItemLongClick(View view);
    }
    public OnItemClickListener  mItemClickListener;

    public OnItemClickListener getItemClickListener() {
        return mItemClickListener;
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }
}
