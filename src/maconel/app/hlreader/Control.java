package maconel.app.hlreader;

import android.content.Context;
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
    protected Context mContext;

    public Control(Context context) {
        mContext = context;
    }

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

    public void setRect(Rect rect) {
        mRect.set(rect);
    }

    public void setForeColor(int color) {
        mForeColor = color;
        mForePaint.setARGB(Color.alpha(color), Color.red(color), Color.green(color), Color.blue(color));
    }

    public int getForeColor() {
        return mForeColor;
    }

    public void setBackColor(int color) {
        mBackColor = color;
        mBackPaint.setARGB(Color.alpha(color), Color.red(color), Color.green(color), Color.blue(color));
    }

    public int getBackColor() {
        return mBackColor;
    }

    public void draw(Canvas canvas) {
    }

    public void touch(MotionEvent event) {
    }

    protected boolean inRect(int x, int y) {
        return mRect.contains(x, y);
    }

    protected Context getContext() {
        return mContext;
    }
}
