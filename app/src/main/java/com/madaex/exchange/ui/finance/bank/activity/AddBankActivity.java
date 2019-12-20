package com.madaex.exchange.ui.finance.bank.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;
import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseNetActivity;
import com.madaex.exchange.common.util.DataUtil;
import com.madaex.exchange.common.util.SPUtils;
import com.madaex.exchange.common.util.ToastUtils;
import com.madaex.exchange.ui.common.CommonContract;
import com.madaex.exchange.ui.common.CommonDataBean;
import com.madaex.exchange.ui.common.CommonPresenter;
import com.madaex.exchange.ui.constant.ConstantUrl;
import com.madaex.exchange.ui.constant.Constants;
import com.madaex.exchange.ui.finance.address.bean.JsonBean;
import com.madaex.exchange.ui.finance.address.fragment.GetJsonDataUtil;
import com.madaex.exchange.ui.finance.bank.contract.SelectBank;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 项目：  madaexchange
 * 类名：  AddBankActivity.java
 * 时间：  2018/8/28 19:42
 * 描述：  ${TODO}
 */

public class AddBankActivity extends BaseNetActivity<CommonPresenter> implements CommonContract.View {
    @BindView(R.id.et_username)
    EditText mEtUsername;
    @BindView(R.id.et_bankname)
    EditText mEtBankname;
    @BindView(R.id.et_bankaddress)
    EditText mEtBankaddress;
    @BindView(R.id.et_bankbrash)
    EditText mEtBankbrash;
    @BindView(R.id.et_bankcardid)
    EditText mEtBankcardid;
    @BindView(R.id.et_rebankcardid)
    EditText mEtRebankcardid;
    @BindView(R.id.submit)
    Button mSubmit;
    private int CODE_REQUEST = 0x002;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_addbank;
    }

    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initDatas() {
        mEtUsername.setText(SPUtils.getString(Constants.USERNAME));
        mEtBankaddress.setCursorVisible(false);
        mEtBankaddress.setFocusable(false);
        mEtBankaddress.setFocusableInTouchMode(false);
        mEtBankname.setCursorVisible(false);
        mEtBankname.setFocusable(false);
        mEtBankname.setFocusableInTouchMode(false);
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.et_bankname, R.id.submit, R.id.toolbar_left_btn_ll, R.id.et_bankaddress})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.et_bankname:
                Intent intent = new Intent(AddBankActivity.this, SelectBankActivity.class);
                startActivityForResult(intent, CODE_REQUEST);
                break;
            case R.id.submit:
                validate();
                break;
            case R.id.toolbar_left_btn_ll:
                finish();
                break;
            case R.id.et_bankaddress:
                if (isLoaded) {
                    showPickerView();
                } else {
                    Toast.makeText(AddBankActivity.this, "no data", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK
                && requestCode == CODE_REQUEST) {
            SelectBank.DataBean resultBean = data.getParcelableExtra("data");
            mEtBankname.setText(resultBean.getBank_name());
        }

    }

    private void validate() {
        if (TextUtils.isEmpty(mEtUsername.getText().toString())) {
            ToastUtils.showToast(getString(R.string.bankname));
            return;
        }
        if (TextUtils.isEmpty(mEtBankname.getText().toString())) {
            ToastUtils.showToast(getString(R.string.selectbankkai));
            return;
        }
        if (TextUtils.isEmpty(mEtBankaddress.getText().toString())) {
            ToastUtils.showToast(getString(R.string.bankaddress));
            return;
        }
        if (TextUtils.isEmpty(mEtBankbrash.getText().toString())) {
            ToastUtils.showToast(getString(R.string.truebrashbank));
            return;
        }
        if (TextUtils.isEmpty(mEtBankcardid.getText().toString())) {
            ToastUtils.showToast(getString(R.string.bankid));
            return;
        }
        if (TextUtils.isEmpty(mEtRebankcardid.getText().toString())) {
            ToastUtils.showToast(getString(R.string.aiganbankid));
            return;
        }
        if (!mEtRebankcardid.getText().toString().endsWith(mEtBankcardid.getText().toString())) {
            ToastUtils.showToast(getString(R.string.beankcardisnosame));
            return;
        }
        TreeMap params = new TreeMap<>();
        params.put("act", ConstantUrl.FINANCE_INSERT_BANK);
        params.put("truename", mEtUsername.getText().toString().trim());
        params.put("bankname", mEtBankname.getText().toString().trim());
        params.put("bankprov", privoce);
        params.put("bankcity", city);
        params.put("bankaddr", mEtBankbrash.getText().toString().trim());
        params.put("bankcard", mEtBankcardid.getText().toString().trim());
        mPresenter.getData(DataUtil.sign2(params));
    }

    @Override
    public void requestSuccess(String msg) {
        SPUtils.putBoolean(Constants.has_bank, true);
        ToastUtils.showToast(msg);
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void requestSuccess2(CommonDataBean.DataBean data) {

    }

    @Override
    public void requestError(String msg) {
        ToastUtils.showToast(msg);
    }

    @Override
    public void nodata(String msg) {
        ToastUtils.showToast(getString(R.string.loginexpired));
    }

    @Override
    public void sendMsgSuccess(String msg) {

    }


    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private Thread thread;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;

    private boolean isLoaded = false;
    private String privoce ,city;

    private void showPickerView() {// 弹出选择器

        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getPickerViewText() +
                        options2Items.get(options1).get(options2) ;
                privoce = options1Items.get(options1).getPickerViewText();
                city =  options2Items.get(options1).get(options2);
                mEtBankaddress.setText(tx);

            }
        })
                .setSubmitText(getResources().getString(R.string.sure))//确定按钮文字
                .setCancelText(getResources().getString(R.string.cacel))//取消按钮文字
                .setTitleText(getString(R.string.cityselect))
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();


       pvOptions.setPicker(options1Items, options2Items);
//        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }

    private void initJsonData() {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(this, "province.json");//获取assets目录下的json文件数据

        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市
                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {
                    City_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }

        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);

    }


    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (thread == null) {//如果已创建就不再重新创建子线程了
                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 子线程中解析省市区数据
                                initJsonData();
                            }
                        });
                        thread.start();
                    }
                    break;

                case MSG_LOAD_SUCCESS:
                    isLoaded = true;
                    break;

                case MSG_LOAD_FAILED:
                    break;
            }
        }
    };
}
