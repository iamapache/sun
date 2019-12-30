package com.madaex.exchange.ui.mine.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allenliu.versionchecklib.v2.builder.NotificationBuilder;
import com.allenliu.versionchecklib.v2.builder.UIData;
import com.allenliu.versionchecklib.v2.callback.CustomDownloadingDialogListener;
import com.allenliu.versionchecklib.v2.callback.CustomVersionDialogListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseNetLazyFragment;
import com.madaex.exchange.common.net.Constant;
import com.madaex.exchange.common.util.DataUtil;
import com.madaex.exchange.common.util.RegexUtil;
import com.madaex.exchange.common.util.SPUtils;
import com.madaex.exchange.common.util.ToastUtils;
import com.madaex.exchange.common.view.CustomPopWindow;
import com.madaex.exchange.ui.buy.bean.Event;
import com.madaex.exchange.ui.constant.ConstantUrl;
import com.madaex.exchange.ui.constant.Constants;
import com.madaex.exchange.ui.finance.address.activity.ScanActivity;
import com.madaex.exchange.ui.login.activity.RegisterActivity;
import com.madaex.exchange.ui.market.activity.MessageListActivity;
import com.madaex.exchange.ui.mine.activity.AccountManagerActivity;
import com.madaex.exchange.ui.mine.activity.AuthenticationActivity;
import com.madaex.exchange.ui.mine.activity.LanguageActivity;
import com.madaex.exchange.ui.mine.activity.LinkWebViewActivity;
import com.madaex.exchange.ui.mine.activity.SafeActivity;
import com.madaex.exchange.ui.mine.bean.User;
import com.madaex.exchange.ui.mine.bean.update;
import com.madaex.exchange.ui.mine.contract.MineContract;
import com.madaex.exchange.ui.mine.presenter.MinePresenter;
import com.madaex.exchange.update.BaseDialog;
import com.madaex.exchange.update.utils.AppUpdateUtils;
import com.madaex.exchange.update.utils.ColorUtil;
import com.madaex.exchange.update.utils.DrawableUtil;
import com.madaex.exchange.update.view.NumberProgressBar;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import hlq.ImageViewBound;

import static com.google.zxing.integration.android.IntentIntegrator.REQUEST_CODE;

/**
 * 项目：  exchange
 * 类名：  MineFragment.java
 * 时间：  2018/8/22 10:24
 * 描述：  ${TODO}
 */

public class MineFragment extends BaseNetLazyFragment<MinePresenter> implements MineContract.View {

    Unbinder unbinder;
    @BindView(R.id.username)
    TextView mUsername;
    @BindView(R.id.img)
    ImageView mImageView;
    @BindView(R.id.rl_info)
    RelativeLayout mRlInfo;
    @BindView(R.id.mark)
    TextView mark;
    @BindView(R.id.img_msg)
    ImageViewBound mImgMsg;
    private CustomPopWindow mCustomPopWindow;

    public static MineFragment newInstance(String string) {
        MineFragment fragment = null;
        if (fragment == null) {
            fragment = new MineFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putString(Constants.ARGS, string);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initInjector() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initView() {
        mImgMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MessageListActivity.class);
                intent.putExtra("market", "ada_GRC");
                intent.putExtra("one_xnb", "ada");
                intent.putExtra("two_xnb", "GRC");
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initDatas() {
    }


    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    private void getData() {
        TreeMap params = new TreeMap<>();
        params.put("act", ConstantUrl.USER_USERINFO_STATUS);
        mPresenter.getData(DataUtil.sign(params));
        if (TextUtils.isEmpty(SPUtils.getString(Constants.TOKEN, ""))) {
            mUsername.setText(R.string.loginregister);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        isVisible = false;
        isPrepared = false;
        TreeMap params = new TreeMap<>();
        params.put("act", ConstantUrl.USER_NEWS_TOTAL);
        mPresenter.getMessageCount(DataUtil.sign(params));
    }


    @OnClick({R.id.ll_authentication, R.id.ll_service, R.id.ll_safe, R.id.rl_info,
            R.id.img, R.id.ll_lua, R.id.ll_agreet, R.id.ll_about, R.id.ll_update, R.id.ll_invitation, R.id.ll_huod})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_authentication:
                goAu();
                break;
            case R.id.ll_service:
                Intent intent0 = new Intent(mContext, LinkWebViewActivity.class);
                intent0.putExtra(LinkWebViewActivity.WEB_TITLE, getString(R.string.mine_ustomer));
                intent0.putExtra(LinkWebViewActivity.WEB_URL, Constant.HTTP + ConstantUrl.service);
                startActivity(intent0);
                break;
            case R.id.ll_safe:
                goSafe();
                break;
            case R.id.ll_huod:
                ToastUtils.showToast(getString(R.string.comingsoon));
                break;
            case R.id.rl_info:
                goAccount();
                break;
            case R.id.img:
                showPopMenu();
                break;
            case R.id.ll_lua:
                startActivity(new Intent(mContext, LanguageActivity.class));
                break;
            case R.id.ll_agreet:
                Intent intent = new Intent(mContext, LinkWebViewActivity.class);
                intent.putExtra(LinkWebViewActivity.WEB_TITLE, getString(R.string.mine_agreement));
                intent.putExtra(LinkWebViewActivity.WEB_URL, Constant.HTTP + ConstantUrl.protocol);
                startActivity(intent);
                break;
            case R.id.ll_about:
                Intent intent2 = new Intent(mContext, LinkWebViewActivity.class);
                intent2.putExtra(LinkWebViewActivity.WEB_TITLE, getString(R.string.mine_brief));
                intent2.putExtra(LinkWebViewActivity.WEB_URL, Constant.HTTP + ConstantUrl.about);
                startActivity(intent2);
                break;
            case R.id.ll_invitation:
                goInvitation();
                break;
            case R.id.ll_update:
//                TreeMap params = new TreeMap<>();
//                params.put("act", ConstantUrl.VERSION_CHECK);
//                mPresenter.update(DataUtil.sign(params));
//                sendRequest(true);
                break;
        }
    }

    private void goInvitation() {
        Intent intent3 = new Intent(mContext, LinkWebViewActivity.class);
        intent3.putExtra(LinkWebViewActivity.WEB_status, 1);
        intent3.putExtra("title", 1);
        intent3.putExtra(LinkWebViewActivity.WEB_TITLE, getString(R.string.mine_invitation));
        intent3.putExtra(LinkWebViewActivity.WEB_URL, Constant.HTTP + ConstantUrl.INVITE + "?token=" + SPUtils.getString(Constants.TOKEN, ""));
        startActivityAfterLogin(intent3);
    }


//    private void sendRequest(boolean showmsg) {
//
//        TreeMap params = new TreeMap<>();
//        params.put("act", ConstantUrl.VERSION_CHECK);
//        HttpParams httpParams = new HttpParams();
//        httpParams.put("data", DataUtil.sign(params));
//        DownloadBuilder builder = AllenVersionChecker
//                .getInstance()
//                .requestVersion()
//                .setRequestMethod(HttpRequestMethod.POST)
//                .setRequestParams(httpParams)
//                .setRequestUrl(Constant.HTTP + Constant.APP_APIS)
//                .request(new RequestVersionListener() {
//                    @Nullable
//                    @Override
//                    public UIData onRequestVersionSuccess(String result) {
//                        FileEncryptionManager mFileEncryptionManager = FileEncryptionManager.getInstance();
//                        String paramsStr = null;
//                        try {
//                            paramsStr = new String(mFileEncryptionManager.decryptByPublicKey(Base64Utils.decode(result)));
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                        Logger.i("<==>onRequestVersionSuccess:" + paramsStr);
//                        Gson gson = new Gson();
//                        update commonBean = gson.fromJson(paramsStr, update.class);
//                        UIData uiData;
//                        if (!commonBean.getData().getIs_update().equals("0")) {
//                            if (Double.valueOf(commonBean.getData().getTitle()) > AppUtils.getVerCode(mContext)) {
//                                uiData = UIData
//                                        .create()
//                                        .setDownloadUrl(commonBean.getData().getUrl())
//                                        .setTitle(commonBean.getData().getTitle())
//                                        .setUPDATE(commonBean.getData().getIs_update())
//                                        .setContent(commonBean.getData().getLog());
//                                return uiData;
//                            } else {
//
//                                return null;
//                            }
//                        } else {
//                            if (showmsg) {
//                                ToastUtils.showToast(getString(R.string.nonewversion));
//                            }
//                            return null;
//                        }
//
//                    }
//
//                    @Override
//                    public void onRequestVersionFailure(String message) {
//                        if (showmsg) {
//                            ToastUtils.showToast(getString(R.string.nonewversion));
//                        }
//                    }
//                });
//
//        builder.setForceRedownload(true);
//        builder.setNotificationBuilder(createCustomNotification());
//        //更新界面选择
//        builder.setCustomVersionDialogListener(createCustomDialogTwo());
//        //下载进度界面选择
//        builder.setCustomDownloadingDialogListener(createCustomDownloadingDialog());
//        builder.executeMission(getActivity());
//    }

    private NotificationBuilder createCustomNotification() {
        return NotificationBuilder.create()
                .setRingtone(true)
                .setIcon(R.mipmap.ic_launcher)
                .setTicker(mContext.getString(R.string.appupdate))
                .setContentTitle(mContext.getString(R.string.load))
                .setContentText(mContext.getString(R.string.appupdate));
    }

    private void forceUpdate() {
        getActivity().finish();
    }

    private CustomVersionDialogListener createCustomDialogTwo() {
        return new CustomVersionDialogListener() {
            @Override
            public Dialog getCustomVersionDialog(Context context, UIData versionBundle) {
                BaseDialog baseDialog = new BaseDialog(context, R.style.UpdateAppDialog, R.layout.lib_update_app_dialog);
                TextView textView = baseDialog.findViewById(R.id.tv_update_info);
                LinearLayout ll_close = baseDialog.findViewById(R.id.ll_close);
                Button button = baseDialog.findViewById(R.id.versionchecklib_version_dialog_commit);
                TextView tv_title = baseDialog.findViewById(R.id.tv_title);
                textView.setText(versionBundle.getContent());
                tv_title.setText(String.format(getString(R.string.upgradetoversion), versionBundle.getTitle()));
                if (versionBundle.getUPDATE().equals("1")) {
                    baseDialog.setCanceledOnTouchOutside(true);

                    ll_close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            baseDialog.cancel();
                        }
                    });
                } else if (versionBundle.getUPDATE().equals("2")) {
                    ll_close.setVisibility(View.GONE);
                    baseDialog.setCanceledOnTouchOutside(false);
                    baseDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                        @Override
                        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                            if (keyCode == KeyEvent.KEYCODE_BACK) {
                                //禁用
                                //返回桌面
                                startActivity(new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME));
                                return true;
                            }
                            return false;
                        }
                    });
                }
                int mDefaultColor = 0xffe94339;
                button.setBackgroundDrawable(DrawableUtil.getDrawable(AppUpdateUtils.dip2px(4, getActivity()), mDefaultColor));
                button.setTextColor(ColorUtil.isTextColorDark(mDefaultColor) ? Color.BLACK : Color.WHITE);
                return baseDialog;
            }
        };
    }

    private CustomDownloadingDialogListener createCustomDownloadingDialog() {
        return new CustomDownloadingDialogListener() {
            @Override
            public Dialog getCustomDownloadingDialog(Context context, int progress, UIData versionBundle) {
                BaseDialog baseDialog = new BaseDialog(context, R.style.UpdateAppDialog, R.layout.lib_update_app_dialog);
                TextView textView = baseDialog.findViewById(R.id.tv_update_info);
                LinearLayout ll_close = baseDialog.findViewById(R.id.ll_close);
                TextView tv_title = baseDialog.findViewById(R.id.tv_title);
                textView.setText(versionBundle.getContent());
                tv_title.setText(String.format(getString(R.string.upgradetoversion), versionBundle.getTitle()));
                if (versionBundle.getUPDATE().equals("1")) {
                    baseDialog.setCanceledOnTouchOutside(true);
                } else if (versionBundle.getUPDATE().equals("2")) {
                    ll_close.setVisibility(View.GONE);
                    baseDialog.setCanceledOnTouchOutside(false);
                }
                Button button = baseDialog.findViewById(R.id.versionchecklib_version_dialog_commit);
                button.setVisibility(View.GONE);
                return baseDialog;
            }

            @Override
            public void updateUI(Dialog dialog, int progress, UIData versionBundle) {
                NumberProgressBar progressBar = dialog.findViewById(R.id.npb);
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(progress);
                progressBar.setMax(100);
                //随背景颜色变化
            }
        };
    }

    private void goAccount() {
        startActivityAfterLogin(new Intent(mContext, AccountManagerActivity.class));
    }

    private void goSafe() {
        startActivityAfterLogin(new Intent(mContext, SafeActivity.class));
    }

    private void goAu() {
        if (TextUtils.isEmpty(SPUtils.getString(Constants.USERNAME))) {
            startActivityAfterLogin(new Intent(mContext, AuthenticationActivity.class));
        } else {
            ToastUtils.showToast(getString(R.string.alVerification));
        }

    }

    private void showPopMenu() {
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.pop_menumark, null);
        //处理popWindow 显示内容
        handleLogic(contentView);
        //创建并显示popWindow
        mCustomPopWindow = new CustomPopWindow.PopupWindowBuilder(mContext)
                .setView(contentView)
                .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                .setBgDarkAlpha(0.7f) // 控制亮度
                .setOnDissmissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                    }
                })
                .create()
                .showAsDropDown(mImageView, -2, 10);

    }

    private void handleLogic(View contentView) {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCustomPopWindow != null) {
                    mCustomPopWindow.dissmiss();
                }
                switch (v.getId()) {
                    case R.id.ll_item1:
                        goAccount();
                        break;
                    case R.id.ll_item2:
                        openScanCode();
                        break;
                }
            }
        };
        contentView.findViewById(R.id.ll_item1).setOnClickListener(listener);
        contentView.findViewById(R.id.ll_item2).setOnClickListener(listener);
    }

    private void openScanCode() {
        IntentIntegrator intentIntegrator = new IntentIntegrator(getActivity());
        intentIntegrator.setCaptureActivity(ScanActivity.class);
        intentIntegrator.initiateScan();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == Activity.RESULT_OK) {
                if (requestCode == REQUEST_CODE) {
                    String code = data.getStringExtra("data");
                    try {
                        if (TextUtils.isEmpty(code)) {
                            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
                            if (result != null && !TextUtils.isEmpty(result.getContents())) {
                                code = result.getContents();

                            }
                        }
                        String str = code.substring(code.length() - 6);
                        Intent intent = new Intent(mContext, RegisterActivity.class);
                        intent.putExtra("invit", str);
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                        if (!TextUtils.isEmpty(code)) {
                            String str = code.substring(code.length() - 6);
                            Intent intent = new Intent(mContext, RegisterActivity.class);
                            intent.putExtra("invit", str);
                            startActivity(intent);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.showToast(getString(R.string.qrcode_error));
        }
    }

    @Override
    public void requestSuccess(User user) {
        mUsername.setText(getString(R.string.userid) + user.getData().getUserid());
        SPUtils.putString(Constants.MOBILE, user.getData().getUsername());
        mark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard2 = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData2 = ClipData.newPlainText(null, user.getData().getUserid());
                clipboard2.setPrimaryClip(clipData2);
                ToastUtils.showToast(getString(R.string.copyaddress));
            }
        });
        SPUtils.putString(Constants.USERNAME, user.getData().getName());
        SPUtils.putString("userid", user.getData().getUserid());
        if (RegexUtil.isInteger(user.getData().getUsername())) {
            SPUtils.putBoolean(Constants.ISMOBILE, true);
        } else {
            SPUtils.putBoolean(Constants.ISMOBILE, false);
        }
        if (user.getData().getIdcardauth() == 1) {
            SPUtils.putBoolean(Constants.has_bank, true);
        } else {
            SPUtils.putBoolean(Constants.has_bank, false);
        }
        if (user.getData().getHas_paypassword().equals("1")) {
            SPUtils.putBoolean(Constants.has_paypassword, true);
        } else {
            SPUtils.putBoolean(Constants.has_paypassword, false);
        }
    }

    @Override
    public void nodata(String msg) {

        SPUtils.putString(Constants.TOKEN, "");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        if (event != null && event.getCode() == Constants.LOGINSUCCESS) {
            String coin = event.getMsg();
            getData();
        }
        if (event != null && event.getCode() == Constants.MINE) {
            String coin = event.getMsg();
            getData();
        }
    }


    @Override
    public void requestError(String msg) {

    }

    @Override
    public void requestMessageCountSuccess(String msg) {
        mImgMsg.setMessageNum(Integer.valueOf(msg));
    }

    @Override
    public void requestupdate(update bean) {
        if (bean.getData().getIs_update().equals("0")) {
            ToastUtils.showToast(getString(R.string.nonewversion));
        }
    }

    private boolean isForceUpdate = true;

    /**
     * 显示软件更新对话框
     */
    private void showNoticeDialog(String msg) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle(getString(R.string.appupdate))
                .setMessage(R.string.newversion)
                .setPositiveButton(R.string.update, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        mPresenter.load(msg);
                    }
                });
        if (!isForceUpdate) {
            dialog.setNegativeButton(R.string.Nexttime, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }
        dialog.setCancelable(false);
        dialog.show();
    }

}
