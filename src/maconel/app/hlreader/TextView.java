package maconel.app.hlreader;

import java.util.ArrayList;
import java.util.List;

import maconel.app.hlreader.ButtonControl.ButtonControlCallback;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

public class TextView extends SurfaceView implements SurfaceHolder.Callback, OnTouchListener, ButtonControlCallback {
    List<Control> mControls = new ArrayList<Control>();
    ContentControl mContentControl = null;
    InfoBarControl mInfoBarControl = null;
    ButtonControl mNextPageButton = null;
    ButtonControl mPrevPageButton = null;
    ButtonControl mFindPageButton = null;
    ButtonControl mPosPageButton = null;
    ButtonControl mOpenPageButton = null;
    Canvas mBitmapCanvas = new Canvas();
    Bitmap mBitmap;
    Bitmap mBitmap2;
    Paint mBitmapPaint = new Paint();
    FileReader mFileReader = new FileReader();

    public TextView(Context context) {
        super(context);

        mContentControl = new ContentControl(context);
        mInfoBarControl = new InfoBarControl(context);
        mNextPageButton = new ButtonControl(context);
        mPrevPageButton = new ButtonControl(context);
        mFindPageButton = new ButtonControl(context);
        mPosPageButton = new ButtonControl(context);
        mOpenPageButton = new ButtonControl(context);
    }

    public TextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContentControl = new ContentControl(context);
        mInfoBarControl = new InfoBarControl(context);
        mNextPageButton = new ButtonControl(context);
        mPrevPageButton = new ButtonControl(context);
        mFindPageButton = new ButtonControl(context);
        mPosPageButton = new ButtonControl(context);
        mOpenPageButton = new ButtonControl(context);
    }

    public void init() {
        getHolder().addCallback(this);
        setOnTouchListener(this);

        mNextPageButton.setCallback(this);
        mNextPageButton.setRect(Config.nextPageButtonRect);
        mNextPageButton.setForeColor(Config.buttonForeColor);
        mNextPageButton.setBackColor(Config.buttonBackColor);
        mControls.add(mNextPageButton);

        mPrevPageButton.setCallback(this);
        mPrevPageButton.setRect(Config.prevPageButtonRect);
        mPrevPageButton.setForeColor(Config.buttonForeColor);
        mPrevPageButton.setBackColor(Config.buttonBackColor);
        mControls.add(mPrevPageButton);

        mFindPageButton.setCallback(this);
        mFindPageButton.setRect(Config.findButtonRect);
        mFindPageButton.setForeColor(Config.buttonForeColor);
        mFindPageButton.setBackColor(Config.buttonBackColor);
        mControls.add(mFindPageButton);

        mPosPageButton.setCallback(this);
        mPosPageButton.setRect(Config.posButtonRect);
        mPosPageButton.setForeColor(Config.buttonForeColor);
        mPosPageButton.setBackColor(Config.buttonBackColor);
        mControls.add(mPosPageButton);

        mOpenPageButton.setCallback(this);
        mOpenPageButton.setRect(Config.openButtonRect);
        mOpenPageButton.setForeColor(Config.buttonForeColor);
        mOpenPageButton.setBackColor(Config.buttonBackColor);
        mControls.add(mOpenPageButton);

        mContentControl.setFileReader(mFileReader);
        mContentControl.setForeColor(Config.contentForeColor);
        mContentControl.setBackColor(Config.contentBackColor);
        mControls.add(mContentControl);

        mInfoBarControl.setProgress(20);
        mInfoBarControl.setForeColor(Config.contentForeColor);
        mInfoBarControl.setBackColor(Config.contentBackColor);
        mControls.add(mInfoBarControl);

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

    public void drawToScreen() {
        SurfaceHolder holder = getHolder();
        Canvas canvas = holder.lockCanvas();
        canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
        holder.unlockCanvasAndPost(canvas);
    }

    public void drawNextPage() {
        swapBitmap();
        drawToScreen();
        mFileReader.readNext();
        drawToBitmap();
    }

    public void drawPrevPage() {
        mFileReader.readPrev();
        drawToBitmap();
        drawNextPage();
    }

    public void drawToBitmap() {
        mBitmapCanvas.drawColor(Config.contentBackColor);
        for (Control control : mControls) {
            control.draw(mBitmapCanvas);
        }
    }

    public void swapBitmap() {
        Bitmap tmpBitmap = mBitmap;
        mBitmap = mBitmap2;
        mBitmap2 = tmpBitmap;
        mBitmapCanvas.setBitmap(mBitmap2);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        mContentControl.setRect(getLeft(), getTop(), getWidth(), getHeight() - 20);
        mInfoBarControl.setRect(0, getHeight() - 20, getWidth(), getHeight());

        mBitmap = Bitmap.createBitmap(getWidth(), getHeight(), android.graphics.Bitmap.Config.RGB_565);
        mBitmap2 = Bitmap.createBitmap(getWidth(), getHeight(), android.graphics.Bitmap.Config.RGB_565);
        mBitmapCanvas.setBitmap(mBitmap2);

        drawToBitmap();
        drawNextPage();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        for (Control control : mControls) {
            if (control.inRect((int) event.getX(), (int) event.getY())) {
                control.touch(event);
                break;
            }
        }
        return true;
    }

    @Override
    public void onClick(ButtonControl control) {
        SurfaceHolder holder = getHolder();
        Canvas canvas = holder.lockCanvas();
        canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
        control.drawFore(canvas);
        holder.unlockCanvasAndPost(canvas);

        if (control == mNextPageButton) {
            drawNextPage();
        } else if (control == mPrevPageButton) {
            drawPrevPage();
        } else if (control == mFindPageButton) {
        } else if (control == mPosPageButton) {
        } else if (control == mOpenPageButton) {
        }
    }
}
