package maconel.app.hlreader;

import java.util.ArrayList;
import java.util.List;

import maconel.app.hlreader.ButtonControl.ButtonControlCallback;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

public class TextView extends SurfaceView implements SurfaceHolder.Callback, OnTouchListener, ButtonControlCallback {
    List<Control> mControls = new ArrayList<Control>();
    ContentControl mContentControl = new ContentControl();
    ButtonControl mNextPageButton = new ButtonControl();
    ButtonControl mPrevPageButton = new ButtonControl();
    Canvas mBitmapCanvas = new Canvas();
    Bitmap mBitmap;
    Paint mBitmapPaint = new Paint();
    FileReader mFileReader = new FileReader();

    public TextView(Context context) {
        super(context);
    }

    public TextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void init() {
        getHolder().addCallback(this);
        setOnTouchListener(this);

        mNextPageButton.setBackColor(128, 128, 128);
        mNextPageButton.setRect(200, 100, 300, 200);
        mNextPageButton.setCallback(this);
        mControls.add(mNextPageButton);

        mPrevPageButton.setBackColor(128, 128, 128);
        mPrevPageButton.setRect(0, 100, 100, 200);
        mPrevPageButton.setCallback(this);
        mControls.add(mPrevPageButton);

        mContentControl.setFileReader(mFileReader);
        mControls.add(mContentControl);

        for (Control control : mControls) {
            control.init(mBitmapCanvas);
        }

        mFileReader.open("/sdcard/ebook/test.txt", 0);
    }

    public void uninit() {
        for (Control control : mControls) {
            control.uninit();
        }

        mFileReader.close();
    }

    public void draw() {
        SurfaceHolder holder = getHolder();
        Canvas canvas = holder.lockCanvas();
        canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
        holder.unlockCanvasAndPost(canvas);
    }

    public void drawNextPage() {
        draw();

        mBitmapCanvas.drawARGB(0xFF, 0, 0, 0);
        for (Control control : mControls) {
            control.draw(mBitmapCanvas);
        }
    }

    public void drawPrevPage() {
        mBitmapCanvas.drawARGB(0xFF, 0, 0, 0);
        for (Control control : mControls) {
            control.draw(mBitmapCanvas);
        }

        draw();

        mBitmapCanvas.drawARGB(0xFF, 0, 0, 0);
        for (Control control : mControls) {
            control.draw(mBitmapCanvas);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        mContentControl.setRect(getTop(), getLeft(), getWidth(), getHeight());

        mBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Config.RGB_565);
        mBitmapCanvas.setBitmap(mBitmap);

        mBitmapCanvas.drawARGB(0xFF, 0, 0, 0);
        for (Control control : mControls) {
            control.draw(mBitmapCanvas);
        }

        drawNextPage();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        for (Control control : mControls) {
            if (control.inRect((int) event.getX(), (int) event.getY()))
                control.touch(event);
        }
        return true;
    }

    @Override
    public void onClick(ButtonControl control) {
        if (control == mNextPageButton) {
            drawNextPage();
        } else if (control == mPrevPageButton) {
            mFileReader.readPrev();
            drawPrevPage();
        }
    }
}
