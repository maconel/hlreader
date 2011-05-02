package maconel.app.hlreader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.LinkedList;
import java.util.List;

import android.util.Log;

public class FileReader {

    protected static final int PAGE_SIZE = 1024;
    protected List<Integer> mPagePoses = new LinkedList<Integer>();
    protected int mPos = 0;
    RandomAccessFile mFile = null;
    String mText = "";
    int mStartInText = 0;

    public boolean open(String filename, int pos) {
        boolean ret = false;

        try {
            mFile = new RandomAccessFile(filename, "r");
            mPos = pos;
            mFile.seek(pos);
            mText = "";
            mStartInText = 0;
            readNext();
            ret = true;
        } catch (FileNotFoundException e) {
            // TODO: 继续抛出异常。
            Log.e("hlreader", String.format("FileReader.open|error 0|e=%s", e.getMessage()));
        } catch (IOException e) {
            // TODO: 继续抛出异常。
            Log.e("hlreader", String.format("FileReader.open|error 1|e=%s", e.getMessage()));
        } catch (Exception e) {
            Log.e("hlreader", String.format("FileReader.open|error 2|e=%s", e.getMessage()));
            mFile = null;
            mPos = 0;
            ret = false;
        }

        Log.d("hlreader", String.format("FileReader.open|leave|ret=%s", ret ? "true" : "false"));
        return ret;
    }

    public void close() {
        try {
            if (mFile != null)
                mFile.close();
        } catch (IOException e) {
            Log.e("hlreader", String.format("FileReader.close|error 0|e=%s", e.getMessage()));
        }
        mPos = 0;
        mFile = null;
        mText = "";
        mStartInText = 0;
        Log.d("hlreader", "FileReader.close|leave");
    }

    public char[] getLine() {
        int index = mText.indexOf('\n', mStartInText);
        Log.d("hlreader", String.format("FileReader.getLine|find \\n|mStartInText=%d|index=%d", mStartInText, index));
        if (index == -1) {
            if (mStartInText >= mText.length())
                return null;
            index = mText.length();
        }

        char[] ret = new char[index - mStartInText];
        mText.getChars(mStartInText, index, ret, 0);
        mStartInText = index + 1;

        return ret;
    }

    public void rollback(int count) {
        Log.d("hlreader", String.format("FileReader.rollback|enter|count=%d", count));
        if (count > 0)
            mStartInText -= (count + 1);
    }

    public void readNext() {
        if (mStartInText >= mText.length())
            mText = "";
        else
            mText = mText.substring(mStartInText);
        mStartInText = 0;
        if (mText.length() < PAGE_SIZE) {
            Log.d("hlreader", "FileReader.readNext|need read");
            mText += read(PAGE_SIZE);
        }
        Log.d("hlreader", String.format("readNext|remain text|length=%d", mText.length()));
    }

    public void readPrev() {
        Log.d("hlreader", "FileReader.readPrev");
        readPos(mPos - PAGE_SIZE / 2);
    }

    public void readPos(int pos) {
        Log.d("hlreader", String.format("FileReader.readPos|enter|pos=%d", pos));
        try {
            mText = "";
            mStartInText = 0;
            mFile.seek(pos);
            readNext();
            mPos = pos;
        } catch (IOException e) {
            // TODO 继续抛出异常。
            Log.e("hlreader", String.format("FileReader.readPos|error 0|e=%s", e.getMessage()));
        } catch (Exception e) {
        }
    }

    protected String read(int count) {
        Log.d("hlreader", String.format("FileReader.read|enter|count=%d", count));
        String ret = null;

        try {
            byte[] buffer = new byte[count];
            int readSize = mFile.read(buffer);
            Log.d("hlreader", String.format("FileReader.read|read|readSize=%d", readSize));
            if (readSize <= 0)
                readSize = 0;
            mPos += readSize;
            ret = new String(buffer, "UTF-16LE");
        } catch (IOException e) {
            // TODO: 继续抛出异常。
            Log.e("hlreader", String.format("FileReader.readPos|error 0|e=%s", e.getMessage()));
        } catch (Exception e) {
            Log.e("hlreader", String.format("FileReader.readPos|error 1|e=%s", e.getMessage()));
            ret = "";
        }

        return ret;
    }
}
