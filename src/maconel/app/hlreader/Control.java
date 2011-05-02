package maconel.app.hlreader;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

public class Control {
    protected Rect mRect = new Rect();
    protected int mForeColor;
    protected int mBackColor;
    protected Paint mForePaint = new Paint();
    protected Paint mBackPaint = new Paint();
    protected Canvas mCanvas;

    public void init(Canvas canvas) {
        mCanvas = canvas;
    }

    public void uninit() {
        mCanvas = null;
    }

    public Rect getRect() {
        return mRect;
    }

    public void setRect(int left, int top, int right, int bottom) {
        mRect.left = left;
        mRect.top = top;
        mRect.right = right;
        mRect.bottom = bottom;
    }

    public void setForeColor(int r, int g, int b) {
        mForeColor = Color.rgb(r, g, b);
        mForePaint.setARGB(0xFF, r, g, b);
    }

    public void setBackColor(int r, int g, int b) {
        mBackColor = Color.rgb(r, g, b);
        mBackPaint.setARGB(0xFF, r, g, b);
    }

    public void draw(Canvas canvas) {
    }

    public void touch(MotionEvent event) {
    }

    protected boolean inRect(int x, int y) {
        return mRect.contains(x, y);
    }
}
