package com.madaex.exchange.ui.market.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseNetActivity;
import com.madaex.exchange.common.net.Constant;
import com.madaex.exchange.common.util.DataUtil;
import com.madaex.exchange.common.util.ToastUtils;
import com.madaex.exchange.ui.constant.ConstantUrl;
import com.madaex.exchange.ui.market.bean.Message;
import com.madaex.exchange.ui.market.contract.MessageContract;
import com.madaex.exchange.ui.market.presenter.MessagePresenter;
import com.madaex.exchange.ui.mine.activity.LinkWebViewActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 项目：  madaexchange
 * 类名：  BankListActivity.java
 * 时间：  2018/8/28 18:47
 * 描述：  ${TODO}
 */

public class MessageListActivity extends BaseNetActivity<MessagePresenter> implements MessageContract.View {
    ArrayList<Message.DataBean> mDataBeans = new ArrayList<>();
    BaseQuickAdapter mAdapter;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;

    private int CODE_REQUEST = 0x002;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message;
    }

    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerview.setLayoutManager(linearLayoutManager);

        mAdapter = new BaseQuickAdapter<Message.DataBean, BaseViewHolder>(R.layout.item_message, mDataBeans) {
            @Override
            protected void convert(BaseViewHolder helper, final Message.DataBean item) {
                helper.setText(R.id.number, item.getTitle()).setText(R.id.type, item.getAdd_time());
                TextView textView = helper.getView(R.id.time1);
                if (item.getIs_read() == 0) {
                    textView.setBackgroundResource(R.drawable.rect_rounded_red);
                } else {
                    textView.setBackgroundResource(R.drawable.rect_rounded_white);
                }
            }
        };
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Message.DataBean dataBean = (Message.DataBean) adapter.getItem(position);
                TreeMap params = new TreeMap<>();
                params.put("act", ConstantUrl.USER_ADD_READ_NEWS);
                params.put("news_id", dataBean.getId());
                params.put("type", "one");
                mPresenter.read(DataUtil.sign(params));

                Intent intent0 = new Intent(mContext, LinkWebViewActivity.class);
                intent0.putExtra(LinkWebViewActivity.WEB_TITLE, dataBean.getTitle());
                intent0.putExtra("type", 3);
                intent0.putExtra(LinkWebViewActivity.WEB_URL, Constant.HTTP + ConstantUrl.particulars + "?type=nwes&id=" + dataBean.getId());
                startActivity(intent0);
            }
        });

        mRecyclerview.setAdapter(mAdapter);
        View top = getLayoutInflater().inflate(R.layout.view_empty_data, (ViewGroup) mRecyclerview.getParent(), false);
        TextView textView = top.findViewById(R.id.nodata);
        textView.setText(R.string.nomessage);
        mAdapter.setEmptyView(top);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK
                && requestCode == CODE_REQUEST) {
            getBankList();
        }

    }


    @Override
    protected void initDatas() {
        getBankList();
    }

    private void getBankList() {
        TreeMap params = new TreeMap<>();
        params.put("act", ConstantUrl.USER_NEWS_LIST);
        mPresenter.getData(DataUtil.sign(params));
    }

    @OnClick({R.id.toolbar_left_btn_ll, R.id.toolbar_right_tv_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_btn_ll:
                finish();
                break;
            case R.id.toolbar_right_tv_ll:

                TreeMap params = new TreeMap<>();
                params.put("act", ConstantUrl.USER_ADD_READ_NEWS);
                params.put("news_id", "");
                params.put("type", "all");
                mPresenter.read(DataUtil.sign(params));
                break;
        }
    }

    @Override
    public void requestSuccess(List<Message.DataBean> bean) {
        mAdapter.setNewData(bean);
    }

    @Override
    public void requestError(String msg) {
        ToastUtils.showToast(msg);
    }

    @Override
    public void requestSuccess(String bean) {
        ToastUtils.showToast(bean);
        TreeMap params = new TreeMap<>();
        params.put("act", ConstantUrl.USER_NEWS_LIST);
        mPresenter.getData(DataUtil.sign(params));
    }

}
