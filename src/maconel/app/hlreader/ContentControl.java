package maconel.app.hlreader;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint.FontMetricsInt;
import android.util.Log;

public class ContentControl extends Control {
    int mFontHeight = 0;
    FileReader mFileReader;

    public ContentControl(Context context) {
        super(context);
    }

    public void init(Canvas canvas) {
        super.init(canvas);

        mForePaint.setTextSize(Config.fontSize);

        FontMetricsInt fmi = mForePaint.getFontMetricsInt();
        mFontHeight = fmi.bottom - fmi.top;
    }

    public void uninit() {
        super.uninit();
    }

    public void setFileReader(FileReader fileReader) {
        mFileReader = fileReader;
    }

    public void draw(Canvas canvas) {
        Log.d("hlreader", "TextView.onDraw|enter");
        char[] text = mFileReader.getLine();
        int start = 0;
        int yPos = mFontHeight;

        Log.d("hlreader", String.format("TextView.onDraw|getLine 0|%s|length=%d", text == null ? "text=null" : "got text", text == null ? 0 : text.length));
        while (text != null) {
            do {
                int count = mForePaint.breakText(text, start, text.length - start, getRect().width(), null);
                Log.d("hlreader", String.format("TextView.onDraw|breakText|count=%d", count));
                canvas.drawText(text, start, count, 0, yPos, mForePaint);
                start += count;
                yPos += mFontHeight;
                Log.d("hlreader", String.format("TextView.onDraw|draw line|start=%d|yPos=%d", start, yPos));
            } while (start < text.length && yPos < getRect().height());

            Log.d("hlreader", "TextView.onDraw|draw paragraph");
            if (yPos >= getRect().height()) {
                Log.d("hlreader", "TextView.onDraw|draw page");
                break;
            } else {
                text = mFileReader.getLine();
                Log.d("hlreader", String.format("TextView.onDraw|getLine 1|%s|length=%d", text == null ? "text=null" : "got text", text == null ? 0 : text.length));
                start = 0;
            }
        }

        if (text != null)
            mFileReader.rollback(text.length - start);
    }
}
