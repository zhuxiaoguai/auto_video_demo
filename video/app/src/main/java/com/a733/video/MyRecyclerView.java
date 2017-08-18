package com.a733.video;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2017/8/17.
 */

public class MyRecyclerView extends RecyclerView {
    private boolean isInter = true;

    public void setInter(boolean inter) {
        isInter = inter;
    }

    public MyRecyclerView(Context context) {
        super(context);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (!isInter) {
            return false;
        }
        return super.onTouchEvent(e);
    }
}

