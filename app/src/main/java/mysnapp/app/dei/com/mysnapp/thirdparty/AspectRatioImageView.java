/*
 * 
 */
package mysnapp.app.dei.com.mysnapp.thirdparty;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

// TODO: Auto-generated Javadoc

/**
 * The Class AspectRatioImageView.
 * <p/>
 * <com.example.view.AspectRatioImageView
 * android:id="@+id/fragment_example_image" android:layout_width="match_parent"
 * android:layout_height="wrap_content" android:adjustViewBounds="true"
 * android:scaleType="fitCenter" android:src="@drawable/placeholder" />
 */
public class AspectRatioImageView extends android.support.v7.widget.AppCompatImageView {

    /**
     * Instantiates a new aspect ratio image view.
     *
     * @param context the context
     */
    public AspectRatioImageView(Context context) {
        super(context);
    }

    /**
     * Instantiates a new aspect ratio image view.
     *
     * @param context the context
     * @param attrs   the attrs
     */
    public AspectRatioImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Instantiates a new aspect ratio image view.
     *
     * @param context  the context
     * @param attrs    the attrs
     * @param defStyle the def style
     */
    public AspectRatioImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /*
     * (non-Javadoc)
     *
     * @see android.widget.ImageView#onMeasure(int, int)
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//		int width = MeasureSpec.getSize(widthMeasureSpec);
//		int height = 0;
//
//		try {
//			if (getDrawable() != null) {
//				height = width * getDrawable().getIntrinsicHeight() / getDrawable().getIntrinsicWidth();
//			}
//		} catch (ArithmeticException e) {
//
//		}
//
//		setMeasuredDimension(width, height);
        try {
            Drawable drawable = getDrawable();

            if (drawable == null) {
                setMeasuredDimension(0, 0);
            } else {
                float imageSideRatio = (float) drawable.getIntrinsicWidth() / (float) drawable.getIntrinsicHeight();
                float viewSideRatio = (float) MeasureSpec.getSize(widthMeasureSpec) / (float) MeasureSpec.getSize(heightMeasureSpec);
                if (imageSideRatio >= viewSideRatio) {
                    // Image is wider than the display (ratio)
                    int width = MeasureSpec.getSize(widthMeasureSpec);
                    int height = (int) (width / imageSideRatio);
                    setMeasuredDimension(width, height);
                } else {
                    // Image is taller than the display (ratio)
                    int height = MeasureSpec.getSize(heightMeasureSpec);
                    int width = (int) (height * imageSideRatio);
                    setMeasuredDimension(width, height);
                }
            }
        } catch (Exception e) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }

    }
}
