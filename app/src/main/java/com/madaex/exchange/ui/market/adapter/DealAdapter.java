package com.madaex.exchange.ui.market.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.madaex.exchange.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目：  madaexchange
 * 类名：  BuyAdapter.java
 * 时间：  2018/11/22 11:41
 * 描述：  ${TODO}
 */
public class DealAdapter extends  RecyclerView.Adapter<DealAdapter.MyViewHolder> {

    private List<List<String>> mDatas=new ArrayList<>();
    private Context mContext;
    private LayoutInflater inflater;
    public DealAdapter(Context context){
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
        holder.name.setText(mContext.getResources().getString(R.string.buy) + (getItemCount()-position + 1) );
        holder.name.setTextColor(mContext.getResources().getColor(R.color.green) );
        holder.price.setText( mDatas.get(position).get(0));
        holder.number.setText( mDatas.get(position).get(1));
        holder.itemView.setTag(mDatas.get(position).get(0));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemClickListener.onItemClick( mDatas.get(position));
            }
        });
    }

    //重写onCreateViewHolder方法，返回一个自定义的ViewHolder
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_deal_trans2,parent, false);
        final  MyViewHolder holder= new MyViewHolder(view);
        return holder;
    }

    public void setNewData(List<List<String>> datas) {
        mDatas.clear();
        this. mDatas=datas;
        notifyDataSetChanged();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        View classView;
        TextView price;
        TextView name;
        TextView number;
        public MyViewHolder(View view) {
            super(view);
            classView = itemView;
            price=(TextView) view.findViewById(R.id.price);
            name=(TextView) view.findViewById(R.id.name);
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
