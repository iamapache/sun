package com.madaex.exchange.ui.buy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.madaex.exchange.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目：  madaexchange
 * 类名：  SellerAdapter.java
 * 时间：  2018/11/22 11:44
 * 描述：  ${TODO}
 */
public class SellerAdapter extends  RecyclerView.Adapter<SellerAdapter.MyViewHolder> {

    private List<List<Float>> mDatas=new ArrayList<>();
    private Context mContext;
    private LayoutInflater inflater;

    public SellerAdapter(Context context){
        this. mContext=context;
        inflater=LayoutInflater. from(mContext);
    }

    @Override
    public int getItemCount() {

        return mDatas.size();
    }

    //填充onCreateViewHolder方法返回的holder中的控件
    @Override
    public void onBindViewHolder(SellerAdapter.MyViewHolder holder, final int position) {
        holder.name.setText(mContext.getResources().getString(R.string.seller) + (getItemCount()-position )+ "" );
        holder.name.setTextColor(mContext.getResources().getColor(R.color.green) );
        holder.price.setText( mDatas.get(position).get(0)+"");
        holder.number.setText( mDatas.get(position).get(1)+"");
        holder.itemView.setTag(mDatas.get(position).get(0)+"");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemClickListener.onItemClick( mDatas.get(position));
            }
        });
    }

    //重写onCreateViewHolder方法，返回一个自定义的ViewHolder
    @Override
    public SellerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.item_deal_trans,parent, false);
        final  SellerAdapter.MyViewHolder holder= new SellerAdapter.MyViewHolder(view);
        return holder;
    }
    public void setNewData(List<List<Float>> datas) {
        mDatas.clear();
        this. mDatas=datas;
        Log.d("MainActivity", "setNewData");
        notifyDataSetChanged();
    }
    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView price;
        TextView name;
        TextView number;

        public MyViewHolder(View view) {
            super(view);
            price=(TextView) view.findViewById(R.id.price);
            name=(TextView) view.findViewById(R.id.name);
            number=(TextView) view.findViewById(R.id.number);
        }

    }
    public static interface OnItemClickListener {
        void onItemClick(List<Float> list);
        void onItemLongClick(View view);
    }
    public SellerAdapter.OnItemClickListener mItemClickListener;

    public SellerAdapter.OnItemClickListener getItemClickListener() {
        return mItemClickListener;
    }

    public void setItemClickListener(SellerAdapter.OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }
}
