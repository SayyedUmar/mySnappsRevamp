package mysnapp.app.dei.com.mysnapp.utils.textrotation;

import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

class ViewOnTouchListener implements View.OnTouchListener {
    Point pushPoint;
    int lastImgLeft;
    int lastImgTop;
    FrameLayout.LayoutParams viewLP;
    FrameLayout.LayoutParams pushBtnLP, editBtnLP;
    int lastPushBtnLeft, lastEditBtnLeft;
    int lastPushBtnTop, lastEditBtnTop;
    private View mPushView, mEditview;
    private int parentWidth, parentHeight;
    private boolean isReturn = false;

    ViewOnTouchListener(View mPushView, View mEditView, int _pWidth, int _pHeight, boolean isreturn) {
        this.mPushView = mPushView;
        this.mEditview = mEditView;
        this.parentWidth = _pWidth;
        this.parentHeight = _pHeight;
        this.isReturn = isreturn;
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                if (null == viewLP) {
                    viewLP = (FrameLayout.LayoutParams) view.getLayoutParams();
                }
                if (null == pushBtnLP) {
                    pushBtnLP = (FrameLayout.LayoutParams) mPushView.getLayoutParams();
                }

                if (null == editBtnLP) {
                    editBtnLP = (FrameLayout.LayoutParams) mEditview.getLayoutParams();
                }

                pushPoint = getRawPoint(event);
                lastImgLeft = viewLP.leftMargin;
                lastImgTop = viewLP.topMargin;
                lastEditBtnLeft = editBtnLP.leftMargin;
                lastEditBtnTop = editBtnLP.topMargin;
                lastPushBtnLeft = pushBtnLP.leftMargin;
                lastPushBtnTop = pushBtnLP.topMargin;
                break;
            case MotionEvent.ACTION_MOVE:
                Point newPoint = getRawPoint(event);
                float moveX = newPoint.x - pushPoint.x;
                float moveY = newPoint.y - pushPoint.y;

                float left = (int) (lastImgLeft + moveX);
                float top = (int) (lastImgTop + moveY);

                float right = (int) left + viewLP.width;
                float bottom = (int) top + viewLP.height;

                if ((left > 0) && (top > 0) && (right < parentWidth) && (bottom < parentHeight)) {
                    viewLP.leftMargin = (int) (lastImgLeft + moveX);
                    viewLP.topMargin = (int) (lastImgTop + moveY);
                    view.setLayoutParams(viewLP);

                    pushBtnLP.leftMargin = (int) (lastPushBtnLeft + moveX);
                    pushBtnLP.topMargin = (int) (lastPushBtnTop + moveY);

                    editBtnLP.leftMargin = (int) (lastEditBtnLeft + moveX);
                    editBtnLP.topMargin = (int) (lastEditBtnTop + moveY);

                    mPushView.setLayoutParams(pushBtnLP);
                    mEditview.setLayoutParams(editBtnLP);
                }

                break;

        }
        return isReturn;
    }

    private Point getRawPoint(MotionEvent event) {
        return new Point((int) event.getRawX(), (int) event.getRawY());
    }
}
