package maconel.app.hlreader;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

public class ButtonControl extends Control {
    protected ButtonControlCallback mCallback;

    public ButtonControl(Context context) {
        super(context);
    }

    interface ButtonControlCallback {
        public void onClick(ButtonControl control);
    }

    public void setCallback(ButtonControlCallback callback) {
        mCallback = callback;
    }

    public void setForeColor(int color) {
        super.setForeColor(color);

        mForePaint.setStyle(Paint.Style.STROKE);
    }

    public void draw(Canvas canvas) {
        canvas.drawRect(mRect, mBackPaint);
    }

    public void drawFore(Canvas canvas) {
        canvas.drawRect(mRect, mForePaint);
    }

    public void touch(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (mCallback != null)
                mCallback.onClick(this);
        }
    }
}
