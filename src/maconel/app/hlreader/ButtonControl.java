package maconel.app.hlreader;

import android.content.Context;
import android.graphics.Canvas;
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

    public void draw(Canvas canvas) {
        canvas.drawRect(mRect, mBackPaint);
    }

    public void touch(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (mCallback != null)
                mCallback.onClick(this);
        }
    }
}
