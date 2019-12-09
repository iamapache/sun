package com.madaex.exchange.common.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.madaex.exchange.R;


/**
 * 带清除功能的edittext
 *
 * @author Administrator
 * @date 2018/6/8
 */

public class ClearEditText extends AppCompatEditText {

    public ClearEditText(Context context) {
        this(context, null);
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        final Drawable drawable = context.getResources().getDrawable(R.mipmap.icon_clear);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());

        if(!TextUtils.isEmpty(getText()) && hasFocus()) {
            setCompoundDrawables(null, null, drawable, null);
        } else {
            setCompoundDrawables(null, null, null, null);
        }
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!TextUtils.isEmpty(getText()) && hasFocus()) {
                    setCompoundDrawables(null, null, drawable, null);
                } else {
                    setCompoundDrawables(null, null, null, null);
                }
            }
        });
        setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!TextUtils.isEmpty(getText()) && hasFocus) {
                    setCompoundDrawables(null, null, drawable, null);
                } else {
                    setCompoundDrawables(null, null, null, null);
                }
            }
        });
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case  MotionEvent.ACTION_DOWN:
                        if(event.getRawX() > getRight() - drawable.getMinimumWidth() - getPaddingRight()) {
                            getText().clear();
                            return true;
                        }
                        break;
                    default:

                        break;
                }
                return false;
            }
        });
    }
}
