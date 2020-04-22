package com.madaex.exchange.ui.buy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.madaex.exchange.R;
import com.madaex.exchange.common.util.SPUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 项目：  madaexchange
 * 类名：  BuyAdapter.java
 * 时间：  2018/11/22 11:41
 * 描述：  ${TODO}
 */
public class BuyAdapter extends  RecyclerView.Adapter<BuyAdapter.MyViewHolder> {

    private List<List<BigDecimal>> mDatas=new ArrayList<>();
    private Context mContext;
    private LayoutInflater inflater;
    private String market_type = "0";
    public BuyAdapter(Context context){
        this. mContext=context;
        market_type = SPUtils.getString("market_type", "0");
        inflater=LayoutInflater. from(mContext);
    }

    @Override
    public int getItemCount() {

        return mDatas.size();
    }

    //填充onCreateViewHolder方法返回的holder中的控件
    @Override
    public void onBindViewHolder(MyViewHolder holder,  int position) {
        holder.name.setText(mContext.getResources().getString(R.string.buy) + (position+ 1)  );
        if(mDatas.get(position).get(0).floatValue()==0){
            holder.price.setText( "--");
        }else {
            holder.price.setText(mDatas.get(position).get(0)+"");
        }
        if(mDatas.get(position).get(1).floatValue()==0){
            holder.number.setText( "--");
        }else {
            holder.number.setText( mDatas.get(position).get(1)+"");
        }
        holder.name.setTextColor(mContext.getResources().getColor(R.color.common_red) );


        int vote = (int) (mDatas.get(position).get(1).floatValue()/(2000)*100);
        holder.mProgressBar.setProgress(vote);
        Log.d("onBindViewHolder", vote+"重连");
        holder.mProgressBar.setProgressDrawable(mContext.getResources().getDrawable(R.drawable.progressbar_bg));
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
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_deal_trans,parent, false);
        final  MyViewHolder holder= new MyViewHolder(view);
        return holder;
    }

    public void setNewData(List<List<Float>> datas) {
        mDatas.clear();

        List<List<BigDecimal>> listList =new ArrayList<>();
        for(List<Float> floats:datas){
            List<BigDecimal> bigDecimals = new ArrayList<>();
            for(Float aFloat:floats){
                bigDecimals.add(new BigDecimal(Float.toString(aFloat)));
            }
            listList.add(bigDecimals);
        }
        this. mDatas=listList;
        notifyDataSetChanged();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        View classView;
        TextView price;
        TextView name;
        TextView number;
        ProgressBar mProgressBar;
        public MyViewHolder(View view) {
            super(view);
            classView = itemView;
            price=(TextView) view.findViewById(R.id.price);
            name=(TextView) view.findViewById(R.id.name);
            number=(TextView) view.findViewById(R.id.number);
            mProgressBar=(ProgressBar) view.findViewById(R.id.progress_bar_h);
        }

    }
    public static interface OnItemClickListener {
        void onItemClick(List<BigDecimal> list);
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
