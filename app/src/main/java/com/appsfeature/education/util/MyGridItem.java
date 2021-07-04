package com.appsfeature.education.util;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by Abhijit on 21-Dec-16.
 */

public class MyGridItem extends LinearLayout {
    public MyGridItem(Context context) {
        super(context);
    }

    public MyGridItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyGridItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec); // This is the key that will make the height equivalent to its width
    }
}