/**
 * Copyright 2016 JustWayward Team
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.madaex.exchange.common.view;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;

import com.madaex.exchange.R;


public class HeidongDialog extends Dialog {

    public HeidongDialog(Context context) {
        this(context, 0);
    }

    public HeidongDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public static HeidongDialog instance(Context activity) {
        LoadingView v = (LoadingView) View.inflate(activity, R.layout.dialog_heidong, null);
        v.setColor(ContextCompat.getColor(activity, R.color.colorPrimary));
        HeidongDialog dialog = new HeidongDialog(activity, R.style.loading_dialog);
        dialog.setContentView(v,
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT)
        );
        return dialog;
    }
}
