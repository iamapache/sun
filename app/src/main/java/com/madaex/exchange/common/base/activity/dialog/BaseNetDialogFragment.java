package com.madaex.exchange.common.base.activity.dialog;


import com.madaex.exchange.common.base.BaseContract;

import javax.inject.Inject;

/**
 * 项目：  frame
 * 类名：  BaseNetActivity.java
 * 时间：  2018/6/28 15:39
 * 描述：  ${TODO}
 */
public abstract class BaseNetDialogFragment<T1 extends BaseContract.BasePresenter> extends BaseDialogFragment {
	@Inject
	protected T1 mPresenter;


	/**
	 * [此方法不可再重写]
	 */

	@Override
	public void attachView() {
		if (mPresenter != null) {
			mPresenter.attachView(this);
		}
	}





	@Override
	public void onDestroy() {

		super.onDestroy();
		if (mPresenter != null) {
			mPresenter.detachView();
		}
	}
}
