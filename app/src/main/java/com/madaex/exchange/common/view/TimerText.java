package com.madaex.exchange.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.madaex.exchange.R;


/**
 * 用于显示倒计时的TextView
 *
 * @author Administrator
 * @date 2018/6/11
 */

public class TimerText extends AppCompatTextView {
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private OnTimerListener mTimerListener;
    private Integer mCountTime = 60 * 1000;
    private Integer mIntervalTime = 1000;
    private String mAfterTimerText;
    private String mDefaultText;
    /**
     * 是否已启动倒计时
     */
    private boolean mIsStart = false;

    public TimerText(Context context) {
        super(context);
    }


    public TimerText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TimerText);
        mDefaultText = array.getString(R.styleable.TimerText_default_text);
        mAfterTimerText = array.getString(R.styleable.TimerText_after_timer_text);
        mCountTime = array.getInteger(R.styleable.TimerText_count_time, mCountTime);
        mIntervalTime = array.getInteger(R.styleable.TimerText_interval_time, mIntervalTime);
        if(!TextUtils.isEmpty(mDefaultText)) {
            setText(mDefaultText);
        }
        array.recycle();
    }

    public void setCountTime(int time) {
        mCountTime = time;
    }

    public void setIntervalTime(int intervalTime) {
        mIntervalTime = intervalTime;
    }

    public void setOnTimerListener(OnTimerListener timerListener) {
        mTimerListener = timerListener;
    }

    public void start() {
        if(mIsStart) {
            return;
        }
        setClickable(false);
        if(TextUtils.isEmpty(mDefaultText) || !TextUtils.isEmpty(getText())) {
            mDefaultText = getText().toString();
        }
        if(mIntervalTime > 0 && mCountTime > mIntervalTime && mHandler != null) {
            mHandler.postDelayed(new TimerRunnable(), mIntervalTime);
            mIsStart = true;
        }
    }

    public void cancel() {
        if(mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
        setClickable(true);
        mIsStart = false;
        mHandler = null;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        cancel();
        mTimerListener = null;
    }

    public interface OnTimerListener {
        /**
         * 每次计时后的监听
         *
         * @param millisUntilFinished
         */
        void onTick(long millisUntilFinished);

        /**
         * 倒计时结束的监听
         */
        void onFinish();
    }

    private class TimerRunnable implements Runnable {

        @Override
        public void run() {
            if(mCountTime > 0 && mCountTime > mIntervalTime) {
                mCountTime = mCountTime - mIntervalTime;
                if(mTimerListener != null) {
                    mTimerListener.onTick(mCountTime);
                }
                if(mHandler != null) {
                    mHandler.postDelayed(this, mIntervalTime);
                }
            } else {
                mIsStart = false;
                setClickable(true);
                if(mHandler != null) {
                    mHandler.removeCallbacksAndMessages(null);
                }
                if(mTimerListener != null) {
                    mTimerListener.onFinish();
                }
                if(!TextUtils.isEmpty(mAfterTimerText)) {
                    setText(mAfterTimerText);
                }
            }
        }
    }

    public void reset() {
        setClickable(true);
        mCountTime = 60 * 1000;
        setText(mDefaultText);
        mHandler.removeCallbacksAndMessages(null);
    }
}
