package mysnapp.app.dei.com.mysnapp.utils.textrotation;

import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

class PushBtnTouchListener implements View.OnTouchListener {
    private static final double PI = 3.14159265359;
    Point pushPoint;
    int lastImgWidth;
    int lastImgHeight;
    int lastImgLeft;
    int lastImgTop;
    int lastImgAngle, lastImgAngle_edt;
    double lastComAngle, lastComAngle_edt;
    int pushImgWidth;
    int pushImgHeight;
    int edtImgWidth;
    int edtImgHeight;
    int lastPushBtnLeft;
    int lastPushBtnTop;
    int lastEdtBtnLeft;
    int lastEdtBtnTop;
    float lastX = -1;
    float lastY = -1;
    private int parentWidth, parentHeight;
    private View mView, mEdit;
    private Point mViewCenter;
    private FrameLayout.LayoutParams pushBtnLP, editBtnLP;
    private FrameLayout.LayoutParams imgLP;

    public PushBtnTouchListener(View mView, View mEdit, int _pWidth, int _pHeight) {
        this.mView = mView;
        this.mEdit = mEdit;
        this.parentWidth = _pWidth;
        this.parentHeight = _pHeight;
    }

    @Override
    public boolean onTouch(View pushView, MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            // 主点按下
            case MotionEvent.ACTION_DOWN:
                pushBtnLP = (FrameLayout.LayoutParams) pushView.getLayoutParams();
                imgLP = (FrameLayout.LayoutParams) mView.getLayoutParams();
                editBtnLP = (FrameLayout.LayoutParams) mEdit.getLayoutParams();

                pushPoint = getPushPoint(pushBtnLP, event);

                // System.out.println("Initial height :: "+ imgLP.height +
                // ", width :: "+imgLP.width);

                lastImgWidth = imgLP.width;
                lastImgHeight = imgLP.height;
                lastImgLeft = imgLP.leftMargin;
                lastImgTop = imgLP.topMargin;
                lastImgAngle = (int) mView.getRotation();

                lastPushBtnLeft = pushBtnLP.leftMargin;
                lastPushBtnTop = pushBtnLP.topMargin;

                lastEdtBtnLeft = editBtnLP.leftMargin;
                lastEdtBtnTop = editBtnLP.topMargin;

                pushImgWidth = pushBtnLP.width;
                pushImgHeight = pushBtnLP.height;

                edtImgWidth = editBtnLP.width;
                edtImgHeight = editBtnLP.height;

                lastX = event.getRawX();
                lastY = event.getRawY();
                refreshImageCenter();
                break;
            // 副点按下
            case MotionEvent.ACTION_POINTER_DOWN:
                break;
            case MotionEvent.ACTION_UP: {
                break;
            }
            case MotionEvent.ACTION_POINTER_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                float rawX = event.getRawX();
                float rawY = event.getRawY();
                if (lastX != -1) {
                    if (Math.abs(rawX - lastX) < 5 && Math.abs(rawY - lastY) < 5) {
                        return false;
                    }
                }
                lastX = rawX;
                lastY = rawY;

                Point O = mViewCenter,
                        A = pushPoint,
                        B = getPushPoint(pushBtnLP, event),
                        C = getPushPoint(editBtnLP, event);

                float dOA = getDistance(O, A);
                float dOB = getDistance(O, B);
                float dOC = getDistance(O, C);

                float f = dOB / dOA;

                Point newPoint = getRawPoint(event);
                float moveX = newPoint.x - pushPoint.x;
                float moveY = newPoint.y - pushPoint.y;

                float left = (int) (lastImgLeft + moveX);
                float top = (int) (lastImgTop + moveY);

                float right = (int) left + imgLP.width;
                float bottom = (int) top + imgLP.height;

                int newWidth = (int) (lastImgWidth * f);
                int newHeight = (int) (lastImgHeight * f);

                int _left = lastImgLeft - ((newWidth - lastImgWidth) / 2);
                int _top = lastImgTop - ((newHeight - lastImgHeight) / 2);

                if ((_left > 0) && (_top > 0) && ((_left + newWidth) < parentWidth) && ((_top + newHeight) < parentHeight)) {
                    imgLP.leftMargin = _left;
                    imgLP.topMargin = _top;
                    imgLP.width = newWidth;
                    imgLP.height = newHeight;
//				mView.setLayoutParams(imgLP);
                }

                float fz = (((A.x - O.x) * (B.x - O.x)) + ((A.y - O.y) * (B.y - O.y)));
                float fm = dOA * dOB;
                double comAngle = (180 * Math.acos(fz / fm) / PI);
                if (Double.isNaN(comAngle)) {
                    comAngle = (lastComAngle < 90 || lastComAngle > 270) ? 0 : 180;
                } else if ((B.y - O.y) * (A.x - O.x) < (A.y - O.y) * (B.x - O.x)) {
                    comAngle = 360 - comAngle;
                }
                lastComAngle = comAngle;

                float angle = (float) (lastImgAngle + comAngle);
                angle = angle % 360;
                mView.setRotation(angle);

                Point imageRB = new Point(mView.getLeft() + mView.getWidth(), mView.getTop() + mView.getHeight());

                Point imageRT = new Point(mView.getLeft() + mView.getWidth(), mView.getTop());

                Point anglePoint = getAnglePoint(O, imageRB, angle, false);
                Point anglePoint1 = getAnglePoint(O, imageRT, angle - 60.0f, false);

                pushBtnLP.leftMargin = (int) (anglePoint.x - pushImgWidth / 2);
                pushBtnLP.topMargin = (int) (anglePoint.y - pushImgHeight / 2);

                editBtnLP.leftMargin = (int) (anglePoint1.x - edtImgWidth / 2);
                editBtnLP.topMargin = (int) (anglePoint1.y - edtImgHeight / 2);
                mEdit.setLayoutParams(editBtnLP);

                pushView.setLayoutParams(pushBtnLP);

                break;
        }
        return false;
    }

    private void refreshImageCenter() {
        int x = mView.getLeft() + mView.getWidth() / 2;
        int y = mView.getTop() + mView.getHeight() / 2;
        mViewCenter = new Point(x, y);
    }

    private Point getPushPoint(FrameLayout.LayoutParams lp, MotionEvent event) {
        return new Point(lp.leftMargin + (int) event.getX(), lp.topMargin + (int) event.getY());
    }

    private float getDistance(Point a, Point b) {
        float v = ((a.x - b.x) * (a.x - b.x)) + ((a.y - b.y) * (a.y - b.y));
        return ((int) (Math.sqrt(v) * 100)) / 100f;
    }

    private Point getAnglePoint(Point O, Point A, float angle, boolean isMinus) {
        int x, y;
        float dOA = getDistance(O, A);
        double p1;
        if (isMinus) {
            p1 = angle * (PI / 180f) * -1.0f;
        } else {
            p1 = angle * PI / 180f;
        }
        double p2 = Math.acos((A.x - O.x) / dOA);
        x = (int) (O.x + dOA * Math.cos(p1 + p2));

        double p3 = Math.acos((A.x - O.x) / dOA);
        y = (int) (O.y + dOA * Math.sin(p1 + p3));
        return new Point(x, y);
    }

    private Point getRawPoint(MotionEvent event) {
        return new Point((int) event.getRawX(), (int) event.getRawY());
    }

    interface layouparam {
        void onLayoutChange();
    }

}
