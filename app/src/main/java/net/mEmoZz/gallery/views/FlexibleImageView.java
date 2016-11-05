package net.mEmoZz.gallery.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by mEmoZz on 11/4/16.
 * muhamed.gendy@gmail.com
 */

public class FlexibleImageView extends ImageView {

    private float ratio = 0;

    public FlexibleImageView(Context context) {
        super(context);
    }

    public FlexibleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlexibleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setRatio(float ratio) {
        this.ratio = ratio;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (ratio != 0) {
            int width = getMeasuredWidth();
            int height = (int) (ratio * width);
            setMeasuredDimension(width, height);
        }
    }
}
