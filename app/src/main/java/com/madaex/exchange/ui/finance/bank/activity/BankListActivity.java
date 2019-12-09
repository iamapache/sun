package com.madaex.exchange.ui.finance.bank.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.madaex.dialog.AlertDialog;
import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseNetActivity;
import com.madaex.exchange.common.util.DataUtil;
import com.madaex.exchange.common.util.SPUtils;
import com.madaex.exchange.common.util.ToastUtils;
import com.madaex.exchange.common.util.ViewUtil;
import com.madaex.exchange.ui.constant.ConstantUrl;
import com.madaex.exchange.ui.constant.Constants;
import com.madaex.exchange.ui.finance.bank.contract.Bank;
import com.madaex.exchange.ui.finance.bank.contract.BankContract;
import com.madaex.exchange.ui.finance.bank.presenter.BankPresenter;
import com.madaex.exchange.ui.mine.activity.AuthenticationActivity;

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

public class BankListActivity extends BaseNetActivity<BankPresenter> implements BankContract.View {
    ArrayList<Bank.DataBean> mDataBeans = new ArrayList<>();
    BaseQuickAdapter mAdapter;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;

    private int CODE_REQUEST = 0x002;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_banklist;
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

        mAdapter = new BaseQuickAdapter<Bank.DataBean, BaseViewHolder>(R.layout.item_banklist, mDataBeans) {
            @Override
            protected void convert(BaseViewHolder helper, final Bank.DataBean item) {
                helper.setText(R.id.name, item.getName()).setText(R.id.bankname, item.getBank()).setText(R.id.banktype, R.string.depositcard)
                        .setText(R.id.number, item.getBankcard()).setImageResource(R.id.bankimg, ViewUtil.setBankImageView(item.getName()));
                helper.getView(R.id.ll_delete).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new AlertDialog(BankListActivity.this).builder().setTitle(getString(R.string.warmprompt))
                                .setMsg(getString(R.string.suredeletecard))
                                .setPositiveButton(getString(R.string.sure), new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        TreeMap params = new TreeMap<>();
                                        params.put("act", ConstantUrl.FINANCE_DEAL_USER_BANK_CARD);
                                        params.put("bank_id", item.getId());
                                        mPresenter.delete(DataUtil.sign(params));

                                    }
                                }).setNegativeButton(getString(R.string.cancel), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        }).show();
                    }
                });
            }
        };
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            }
        });

        mAdapter.addFooterView(getFooterView(1, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( SPUtils.getBoolean(Constants.has_bank, true)){
                    ToastUtils.showToast(getString(R.string.alardyaddbank));
                }else {
                    if(!TextUtils.isEmpty(SPUtils.getString(Constants.USERNAME))){
                        Intent intent = new Intent(mContext, AddBankActivity.class);
                        startActivityForResult(intent, CODE_REQUEST);
                    }else {
                        ToastUtils.showToast(getString(R.string.Notauthentic));
                        startActivity(new Intent(mContext, AuthenticationActivity.class));
                    }
                }
            }
        }));
        mRecyclerview.setAdapter(mAdapter);
    }

    private View getFooterView(int type, View.OnClickListener listener) {
        View view = getLayoutInflater().inflate(R.layout.bank_footer_view, (ViewGroup) mRecyclerview.getParent(), false);
        view.setOnClickListener(listener);
        return view;
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
        params.put("act", ConstantUrl.FINANCE_USER_BANK_LIST);
        mPresenter.getData(DataUtil.sign(params));
    }

    @OnClick({R.id.toolbar_left_btn_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_btn_ll:
                finish();
                break;
        }
    }

    @Override
    public void requestSuccess(List<Bank.DataBean> bean) {
        mAdapter.setNewData(bean);
    }

    @Override
    public void nodata(String msg) {
        ToastUtils.showToast(getString(R.string.loginexpired));
    }

    @Override
    public void requestError(String msg) {
        ToastUtils.showToast(msg);
    }

    @Override
    public void deleteSuccess(String msg) {
        ToastUtils.showToast(msg);
        SPUtils.putBoolean(Constants.has_bank, false);
        getBankList();
    }

    @Override
    public void deleteError(String msg) {
        ToastUtils.showToast(msg);
    }
}
