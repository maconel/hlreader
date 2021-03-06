package maconel.app.hlreader;

import java.util.Date;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Canvas;
import android.graphics.Rect;

public class InfoBarControl extends Control {
    protected float mProgress;
    protected int mPowerLevel;

    protected BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mPowerLevel = intent.getIntExtra("level", 0);
        }
    };

    public InfoBarControl(Context context) {
        super(context);

        context.registerReceiver(mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    public void setProgress(float progress) {
        mProgress = progress;
    }

    public void draw(Canvas canvas) {
        canvas.drawRect(getRect(), mBackPaint);

        Date now = new Date(System.currentTimeMillis());
        String textInfo = String.format("%.2f%%[%d]%02d:%02d", mProgress, mPowerLevel, now.getHours(), now.getMinutes());
        float infoTextWidth = mForePaint.measureText(textInfo);
        canvas.drawText(textInfo, getRect().right - infoTextWidth, getRect().bottom, mForePaint);

        final int lineWidth = 3;
        Rect progressRect = getRect();
        progressRect.top = progressRect.bottom - 9;
        progressRect.left += lineWidth;
        progressRect.right -= infoTextWidth + 2;
        int progress = (int) ((progressRect.width() - lineWidth / 2 - infoTextWidth) * mProgress / 100);
        mForePaint.setStrokeWidth(lineWidth);
        canvas.drawLine(progressRect.left, progressRect.top, progressRect.left, progressRect.bottom, mForePaint);
        canvas.drawLine(progressRect.right, progressRect.top, progressRect.right, progressRect.bottom, mForePaint);
        canvas.drawLine(progressRect.left, progressRect.centerY(), progressRect.left + progress, progressRect.centerY(), mForePaint);
        mForePaint.setStrokeWidth(1);
        canvas.drawLine(progressRect.left + progress, progressRect.centerY(), progressRect.right, progressRect.centerY(), mForePaint);
    }
}
