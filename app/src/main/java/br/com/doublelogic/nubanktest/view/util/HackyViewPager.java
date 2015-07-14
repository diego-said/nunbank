package br.com.doublelogic.nubanktest.view.util;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import br.com.doublelogic.nubanktest.util.LogUtil;

/**
 * Created by diegoalvessaidsimao on 14/07/15.
 */
public class HackyViewPager extends ViewPager {

    private static final String TAG = LogUtil.getTag(HackyViewPager.class);

    public HackyViewPager(Context context) {
        super(context);
    }

    public HackyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        try {
            return super.onTouchEvent(ev);
        } catch (IllegalArgumentException e) {
            Log.e(TAG, e.getMessage(), e);
            return false;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException e) {
            Log.e(TAG, e.getMessage(), e);
            return false;
        }
    }
}
