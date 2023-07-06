package com.remi.blendphoto.photoblender.photomixer.viewcustom.erase;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.remi.blendphoto.photoblender.photomixer.R;

import java.util.ArrayList;

@SuppressLint("ViewConstructor")
public class HoverView extends View {

    public Context mContext;
    public static int mode = 0;
    Bitmap bm;
    Bitmap clippedBitmap;
    Bitmap magicPointer;
    int[] saveBitmapData;
    int[] lastBitmapData;
    static Canvas newCanvas;
    static Paint eraser, uneraser;
    private Paint mBitmapPaint;
    private Paint mMaskPaint;

    private static Path mPath, mPathErase;
    public static int ERASE_MODE = 0;
    public static int UNERASE_MODE = 1;
    public static int MAGIC_MODE = 2;
    public static int MAGIC_MODE_RESTORE = 3;
    public static int MOVING_MODE = 4;
    public static int MIRROR_MODE = 5;
    public PointF touchPoint;
    public PointF drawingPoint;

    public int magicTouchRange = 200;
    public int magicThreshold = 15;
    public int alphaErase = 255, alphaUnErase = 255;
    private float mX, mY;
    private static final float TOUCH_TOLERANCE = 4;
    private int strokeWidthErase = 40;
    private int strokeWidthUnErase = 40;

    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    int touchMode = NONE;
    String TAG = "2tdp";

    ArrayList<int[]> stackChange;
    ArrayList<Boolean> checkMirrorStep;
    int currentIndex = -1;
    final int STACKSIZE = 10;
    public static int POINTER_DISTANCE;

    public HoverView(Context context) {
        super(context);
        mContext = context;
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    public void setBitmap(Bitmap bm) {
        init(bm);
    }

    public void setAlphaErase(int alpha) {
        alphaErase = alpha;

        invalidate();
    }

    public int getAlphaErase() {
        return alphaErase;
    }

    public void setAlphaUnErase(int alpha) {
        alphaUnErase = alpha;

        invalidate();
    }

    public int getAlphaUnErase() {
        return alphaUnErase;
    }

    public int getSizeErase() {
        return strokeWidthErase;
    }

    public int getSizeUnErase() {
        return strokeWidthUnErase;
    }

    public void switchMode(int _mode) {
        mode = _mode;
        resetPath();
        saveLastMaskData();
        if (mode == MAGIC_MODE || mode == MAGIC_MODE_RESTORE)
            magicPointer = BitmapFactory.decodeResource(getResources(), R.drawable.ic_erase1);
        else if (mode == ERASE_MODE || mode == UNERASE_MODE)
            magicPointer = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_erase1), strokeWidthErase + 5, strokeWidthErase + 5, false);

        invalidate();
    }

    public int getMode() {
        return mode;
    }

    public void setMagicThreshold(int value) {
        magicThreshold = value;
    }

    public void mirrorImage() {
        Matrix matrix = new Matrix();
        matrix.preScale(-1.0f, 1.0f);
        Bitmap tempBm = bm;
        bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
        tempBm.recycle();

        Bitmap tempMask = clippedBitmap;
        clippedBitmap = Bitmap.createBitmap(clippedBitmap, 0, 0, clippedBitmap.getWidth(), clippedBitmap.getHeight(), matrix, true);
        tempMask.recycle();

        bm.getPixels(saveBitmapData, 0, bm.getWidth(), 0, 0, bm.getWidth(), bm.getHeight());
        saveLastMaskData();

        newCanvas = new Canvas(clippedBitmap);
        newCanvas.save();
        invalidate();
        addToStack(true);
    }

    public void setEraseOffset(int type, int offSet) {
        switch (type) {
            case 0:
                mode = UNERASE_MODE;
                strokeWidthUnErase = offSet;
                uneraser.setStrokeWidth(offSet);
                uneraser.setShadowLayer(0, 0, 0, Color.BLACK);
                magicPointer = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_erase1), offSet, offSet, false);
                break;
            case 1:
                mode = UNERASE_MODE;
                strokeWidthUnErase = offSet;
                uneraser.setStrokeWidth(offSet);
                uneraser.setShadowLayer(34, 0, 0, Color.BLACK);
                magicPointer = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_erase2), offSet, offSet, false);
                break;
            case 2:
                mode = ERASE_MODE;
                strokeWidthErase = offSet;
                eraser.setStrokeWidth(offSet);
                eraser.setShadowLayer(0, 0, 0, Color.WHITE);
                magicPointer = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_erase3), offSet, offSet, false);
                break;
            case 3:
                mode = ERASE_MODE;
                strokeWidthErase = offSet;
                eraser.setStrokeWidth(offSet);
                eraser.setShadowLayer(34, 0, 0, Color.WHITE);
                magicPointer = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_erase4), offSet, offSet, false);
                break;
        }
        resetPath();
        invalidate();
    }

    @SuppressLint("DefaultLocale")
    void init(Bitmap bitmap) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        mPath = new Path();
        mPathErase = new Path();

        eraser = new Paint();
        eraser.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
        eraser.setAntiAlias(true);
        eraser.setStyle(Paint.Style.STROKE);
        eraser.setStrokeJoin(Paint.Join.ROUND);
        eraser.setStrokeCap(Paint.Cap.ROUND);
        eraser.setStrokeWidth(strokeWidthErase);

        uneraser = new Paint();
        uneraser.setXfermode(new PorterDuffXfermode(Mode.SRC_ATOP));
        uneraser.setAntiAlias(true);
        uneraser.setStyle(Paint.Style.STROKE);
        uneraser.setStrokeJoin(Paint.Join.ROUND);
        uneraser.setStrokeCap(Paint.Cap.ROUND);
        uneraser.setStrokeWidth(strokeWidthUnErase);

        mBitmapPaint = new Paint();
        mBitmapPaint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
        mBitmapPaint.setAntiAlias(true);

        mMaskPaint = new Paint();
        mMaskPaint.setAntiAlias(true);

        bm = bitmap;
        bm = bm.copy(Config.ARGB_8888, true);

        clippedBitmap = Bitmap.createBitmap(w, h, Config.ARGB_8888);

        newCanvas = new Canvas(clippedBitmap);
        newCanvas.save();

        mMaskPaint.setXfermode(new PorterDuffXfermode(Mode.SRC_ATOP));
        newCanvas.drawBitmap(bm, 0, 0, mMaskPaint);
        mMaskPaint.setXfermode(null);

        magicTouchRange = w > h ? h / 2 : w / 2;
        saveBitmapData = new int[w * h];
        bm.getPixels(saveBitmapData, 0, bm.getWidth(), 0, 0, bm.getWidth(), bm.getHeight());
        lastBitmapData = new int[w * h];

        magicPointer = BitmapFactory.decodeResource(getResources(), R.drawable.ic_erase);
        touchPoint = new PointF(w / 2f, h / 2f);
        drawingPoint = new PointF(w / 2f, h / 2f);

        saveLastMaskData();
        stackChange = new ArrayList<>();
        checkMirrorStep = new ArrayList<>();
        addToStack(false);

        POINTER_DISTANCE = (int) (50 * mContext.getResources().getDisplayMetrics().density);
    }

    void addToStack(boolean isMirror) {
        if (stackChange.size() >= STACKSIZE) {
            stackChange.remove(0);
            if (currentIndex > 0) currentIndex--;
        }

        if (currentIndex == 0) {
            int size = stackChange.size();
            for (int i = size - 1; i > 0; i--) {
                stackChange.remove(i);
                checkMirrorStep.remove(i);
            }
        }

        int[] pix = new int[clippedBitmap.getWidth() * clippedBitmap.getHeight()];
        clippedBitmap.getPixels(pix, 0, clippedBitmap.getWidth(), 0, 0, clippedBitmap.getWidth(), clippedBitmap.getHeight());
        stackChange.add(pix);
        checkMirrorStep.add(isMirror);
        currentIndex = stackChange.size() - 1;
    }


    public Bitmap drawBitmap(Bitmap bmpDraw) {

        if (mode == ERASE_MODE || mode == UNERASE_MODE) {
            if (mode == ERASE_MODE) uneraser.setXfermode(new PorterDuffXfermode(Mode.SRC_OUT));
            else uneraser.setXfermode(new PorterDuffXfermode(Mode.SRC));

            float strokeRatio = 1;

            if (scale > 1) strokeRatio = scale;

            eraser.setStrokeWidth(strokeWidthErase / strokeRatio);
            uneraser.setStrokeWidth(strokeWidthUnErase / strokeRatio);

            if (mode == ERASE_MODE) eraser.setAlpha(alphaErase);

            newCanvas.drawPath(mPath, eraser);
            newCanvas.drawPath(mPathErase, uneraser);
        }

        return clippedBitmap;
    }

    public Bitmap saveDrawnBitmap() {
        Bitmap saveBitmap = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(), Config.ARGB_8888);
        Paint paint = new Paint();

        Canvas cv = new Canvas(saveBitmap);

        // Draws the photo.
        cv.drawBitmap(bm, 0, 0, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
        cv.drawBitmap(clippedBitmap, 0, 0, paint);

        return saveBitmap;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (bm == null) return;

        if (mode == UNERASE_MODE) mMaskPaint.setAlpha(alphaUnErase);
        canvas.drawBitmap(bm, matrix, mMaskPaint);
        canvas.drawBitmap(drawBitmap(bm), matrix, mBitmapPaint);

        if (mode == MAGIC_MODE || mode == MAGIC_MODE_RESTORE || mode == ERASE_MODE || mode == UNERASE_MODE)
            canvas.drawBitmap(magicPointer, drawingPoint.x - magicPointer.getWidth() / 2f, drawingPoint.y - magicPointer.getHeight() / 2f, mMaskPaint);
    }

    public void saveLastMaskData() {
        clippedBitmap.getPixels(lastBitmapData, 0, clippedBitmap.getWidth(), 0, 0, clippedBitmap.getWidth(), clippedBitmap.getHeight());
    }

    public void resetPath() {
        mPath.reset();
        mPathErase.reset();
    }

    private void touch_start(float x, float y) {
        mPath.reset();
        mPathErase.reset();

        if (mode == ERASE_MODE) mPath.moveTo(x, y);
        else mPathErase.moveTo(x, y);
        mX = x;
        mY = y;
    }

    private void touch_move(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            if (mode == ERASE_MODE) mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            else mPathErase.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;
        }
    }

    private void touch_up() {
        if (mode == ERASE_MODE) mPath.lineTo(mX, mY);
        else mPathErase.lineTo(mX, mY);
    }

    PointF DownPT = new PointF(); // Record Mouse Position When Pressed Down
    // these PointF objects are used to record the point(s) the user is touching
    PointF start = new PointF();
    PointF mid = new PointF();
    float oldDist = 1f;

    Matrix matrix = new Matrix();
    Matrix savedMatrix = new Matrix();
    float scale = 1.0f;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        if (mode == ERASE_MODE || mode == UNERASE_MODE) y = y - POINTER_DISTANCE;

        if (mode == MAGIC_MODE || mode == MAGIC_MODE_RESTORE || mode == ERASE_MODE || mode == UNERASE_MODE) {
            drawingPoint.x = x;
            drawingPoint.y = y;
        }

        if (mode != MOVING_MODE) {
            float[] v = new float[9];
            matrix.getValues(v);
            // translation
            float mScalingFactor = v[Matrix.MSCALE_X];

            RectF r = new RectF();
            matrix.mapRect(r);

            // mScalingFactor shall contain the scale/zoom factor
            float scaledX = (x - r.left);
            float scaledY = (y - r.top);

            scaledX /= mScalingFactor;
            scaledY /= mScalingFactor;

            x = scaledX;
            y = scaledY;
        }

        int maskedAction = event.getActionMasked();

        switch (maskedAction) {
            case MotionEvent.ACTION_DOWN:

                savedMatrix.set(matrix);
                start.set(event.getX(), event.getY());

                touchMode = DRAG;

                if (mode == ERASE_MODE || mode == UNERASE_MODE) touch_start(x, y);
                else if (mode == MOVING_MODE) {
                    DownPT.x = event.getX();
                    DownPT.y = event.getY();
                }
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                if (touchMode == DRAG) {
                    if (mode == ERASE_MODE || mode == UNERASE_MODE) touch_move(x, y);
                    else if (mode == MOVING_MODE) {
                        PointF mv = new PointF(event.getX() - DownPT.x, event.getY() - DownPT.y);
                        matrix.postTranslate(mv.x, mv.y);
                        DownPT.x = event.getX();
                        DownPT.y = event.getY();
                    } else if (mode == MAGIC_MODE || mode == MAGIC_MODE_RESTORE) {
                        touchPoint.x = x;
                        touchPoint.y = y;
                    }
                    invalidate();
                } else if (touchMode == ZOOM && mode == MOVING_MODE) {
                    // pinch zooming
                    float newDist = spacing(event);
                    if (newDist > 5f) {
                        matrix.set(savedMatrix);
                        scale = newDist / oldDist; // setting the scaling of the
                        // matrix...if scale > 1 means
                        // zoom in...if scale < 1 means
                        // zoom out
                        matrix.postScale(scale, scale, mid.x, mid.y);
                        Log.d(TAG, "scale =" + scale);
                    }

                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                if (mode == ERASE_MODE || mode == UNERASE_MODE) {
                    touch_up();
                    addToStack(false);
                } else if (mode == MAGIC_MODE || mode == MAGIC_MODE_RESTORE) {
                    touchPoint.x = x;
                    touchPoint.y = y;
                    saveLastMaskData();
                }
                invalidate();
                resetPath();
                break;
            case MotionEvent.ACTION_POINTER_UP: // second finger lifted
                touchMode = NONE;
                Log.d(TAG, "mode=NONE");
                break;
            case MotionEvent.ACTION_POINTER_DOWN: // first and second finger down
                oldDist = spacing(event);
                if (oldDist > 5f) {
                    savedMatrix.set(matrix);
                    midPoint(mid, event);
                    touchMode = ZOOM;
                }
                break;
        }
        return true;
    }

    /*
     * --------------------------------------------------------------------------
     * Method: spacing Parameters: MotionEvent Returns: float Description:
     * checks the spacing between the two fingers on touch
     * ----------------------------------------------------
     */

    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    /*
     * --------------------------------------------------------------------------
     * Method: midPoint Parameters: PointF object, MotionEvent Returns: void
     * Description: calculates the midpoint between the two fingers
     * ------------------------------------------------------------
     */

    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }
}
