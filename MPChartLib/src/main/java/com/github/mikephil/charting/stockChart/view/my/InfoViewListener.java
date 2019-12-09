package com.github.mikephil.charting.stockChart.view.my;

import android.content.Context;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.DisplayUtils;

/**
 * Created by dell on 2017/9/28.
 */

public class InfoViewListener implements OnChartValueSelectedListener {

    private double mLastClose;
    private int mWidth;
    /**
     * if otherChart not empty, highlight will disappear after 3 second
     */
    private Chart[] mOtherChart;


    public InfoViewListener(Context context, Chart... otherChart) {
        mWidth = DisplayUtils.getWidthHeight(context)[0];
        mOtherChart = otherChart;
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        int x = (int) e.getX();
        if (mOtherChart != null) {
            for (Chart aMOtherChart : mOtherChart) {
                aMOtherChart.highlightValues(new Highlight[]{new Highlight(h.getX(), Float.NaN, h.getDataSetIndex())});
            }
        }
    }

    @Override
    public void onNothingSelected() {
        if (mOtherChart != null) {
            for (int i = 0; i < mOtherChart.length; i++) {
                mOtherChart[i].highlightValues(null);
            }
        }
    }
}
