package mysnapp.app.dei.com.mysnapp.thirdparty;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import mysnapp.app.dei.com.mysnapp.R;

public class VSCacheImageView extends AppCompatImageView implements Observer {

    public static ArrayList<String> arrDownloadingStatus = new ArrayList<String>();
    public static ArrayList<String> arrLocalDownloadingStatus = new ArrayList<String>();
    public static int totalCount = 0, currentCount = 0;
    Context mContext;
    private boolean isDownLoaded = false;
    private String currentURL;
    private VscacheImageLoader vsImageLoader;
    private int count;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private int currentDownloadingProgress = 0;
    private boolean attrIsthumb = true;

    public VSCacheImageView(Context context) {
        this(context, null);
        init(context);

    }

    public VSCacheImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init(context);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.VSCacheImageView, 0, 0);
        try {
            attrIsthumb = ta.getBoolean(R.styleable.VSCacheImageView_isthumb, true);
        } finally {
            ta.recycle();
        }
    }

    public VSCacheImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                inSampleSize = Math.round((float) height / (float) reqHeight);
            } else {
                inSampleSize = Math.round((float) width / (float) reqWidth);
            }
        }

        return inSampleSize;
    }

    public static Bitmap getScalledBitmap(File file) {
        Bitmap bm = null;

        int reqWidth = 0, reqHeight = 0;

        try {
            BitmapFactory.Options options = new BitmapFactory.Options();

            options.inJustDecodeBounds = true;

            bm = BitmapFactory.decodeStream(new FileInputStream(file), null, options);

            reqHeight = 600;

            if (options.outHeight <= reqHeight) {
                reqHeight = options.outHeight;
                reqWidth = options.outWidth;
            } else {
                reqWidth = (options.outHeight * reqHeight) / options.outWidth;
            }

            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
            options.inJustDecodeBounds = false;

            bm = BitmapFactory.decodeStream(new FileInputStream(file), null, options);

        } catch (FileNotFoundException e) {
            Log.e("Exception", e.getLocalizedMessage());
        }

        return bm;
    }

    public static Bitmap getScalledThumbnailBitmap(File file) {
        Bitmap bm = null;

        int reqWidth = 0, reqHeight = 0;

        try {
            BitmapFactory.Options options = new BitmapFactory.Options();

            options.inJustDecodeBounds = true;

            bm = BitmapFactory.decodeStream(new FileInputStream(file), null, options);

            reqHeight = 200;

            if (options.outHeight <= reqHeight) {
                reqHeight = options.outHeight;
                reqWidth = options.outWidth;
            } else {
                reqWidth = (options.outHeight * reqHeight) / options.outWidth;
            }

            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
            options.inJustDecodeBounds = false;

            bm = BitmapFactory.decodeStream(new FileInputStream(file), null, options);

        } catch (FileNotFoundException e) {
            Log.e("Exception", e.getLocalizedMessage());
        }

        return bm;
    }

    private void init(Context context) {
        mContext = context;
        //SmartApplication.REF_SMART_APPLICATION.getObserver().addObserver(this);
    }

    public void setAttrIsthumb(boolean attrIsthumb) {
        this.attrIsthumb = attrIsthumb;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!attrIsthumb) {
            try {
                Drawable drawable = getDrawable();

                if (drawable == null) {
                    setMeasuredDimension(0, 0);
                } else {
                    float imageSideRatio = (float) drawable.getIntrinsicWidth() / (float) drawable.getIntrinsicHeight();
                    float viewSideRatio = (float) MeasureSpec.getSize(widthMeasureSpec)
                            / (float) MeasureSpec.getSize(heightMeasureSpec);
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
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    public void setVsCacheImageLoader(VscacheImageLoader listener) {
        vsImageLoader = listener;
    }


    @Override
    protected void finalize() throws Throwable {
        ((BitmapDrawable) getDrawable()).getBitmap().recycle();
        super.finalize();
    }

    public boolean isDownLoaded() {
        return isDownLoaded;
    }

    public void setBitmapSafely(final Bitmap bitmap, final int tag) {
        mHandler.post(new Runnable() {

            @Override
            public void run() {
                // if (getTag() != null && getTag().equals(new Integer(tag))) {
                setImageResource(0);
                setImageBitmap(bitmap);
                isDownLoaded = true;
                if (vsImageLoader != null) {
                    vsImageLoader.onVscacheImageLoad();
                }
                // } else {
                // BitmapDrawable tempBitmap = (BitmapDrawable)
                // getResources().getDrawable(R.drawable.image_loader);
                // setImageBitmap(tempBitmap.getBitmap());
                // }
            }
        });
    }

    public void publishProgressSafely(int progress) {
        currentDownloadingProgress = progress;
        System.out.println("Progress :: " + progress + " of file " + currentCount + " out of " + totalCount);
    }

    @Override
    public void update(Observable observable, Object data) {

    }

    public interface VscacheImageLoader {
        void onVscacheImageLoad();
    }
}
