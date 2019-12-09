package com.madaex.exchange.common.base.activity;

import android.support.annotation.NonNull;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * 项目：  loan
 * 类名：  BasePermissionActivity.java
 * 时间：  2018/6/26 15:57
 * 描述：  ${TODO}
 */
public abstract class BasePermissionActivity extends RxAppCompatActivity implements EasyPermissions.PermissionCallbacks{


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //将请求结果传递EasyPermission库处理
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        /**
//         * 1.使用getSupportFragmentManager().getFragments()获取到当前Activity中添加的Fragment集合
//         * 2.遍历Fragment集合，手动调用在当前Activity中的Fragment中的onActivityResult()方法。
//         */
//        if (getSupportFragmentManager().getFragments() != null && getSupportFragmentManager().getFragments().size() > 0) {
//            List<Fragment> fragments = getSupportFragmentManager().getFragments();
//            for (Fragment mFragment : fragments) {
//                mFragment.onActivityResult(requestCode, resultCode, data);
//            }
//        }
//        switch (requestCode) {
//            //当从软件设置界面，返回当前程序时候
//            case AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE:
//                ToastUtils.showToast("当从软件设置界面，返回当前程序时候");
//                break;
//            default:
//                break;
//        }
//    }
    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
    }

}
