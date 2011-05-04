package maconel.app.hlreader;

import java.io.IOException;

import android.graphics.Rect;

public class Config {
    static final protected String FILENAME = "cfg.ini";
    static public Rect nextPageButtonRect;
    static public Rect prevPageButtonRect;
    static public Rect findButtonRect;
    static public Rect posButtonRect;
    static public Rect openButtonRect;
    static public int foreColor;
    static public int backColor;
    static public int fontSize;

    static public void load() {
        nextPageButtonRect.left = getValue("nextPageButtonRect", "left", 0);
        nextPageButtonRect.top = getValue("nextPageButtonRect", "top", 0);
        nextPageButtonRect.right = getValue("nextPageButtonRect", "right", 0);
        nextPageButtonRect.bottom = getValue("nextPageButtonRect", "bottom", 0);

        prevPageButtonRect.left = getValue("prevPageButtonRect", "left", 0);
        prevPageButtonRect.top = getValue("prevPageButtonRect", "top", 0);
        prevPageButtonRect.right = getValue("prevPageButtonRect", "right", 0);
        prevPageButtonRect.bottom = getValue("prevPageButtonRect", "bottom", 0);

        findButtonRect.left = getValue("findButtonRect", "left", 0);
        findButtonRect.top = getValue("findButtonRect", "top", 0);
        findButtonRect.right = getValue("findButtonRect", "right", 0);
        findButtonRect.bottom = getValue("findButtonRect", "bottom", 0);

        posButtonRect.left = getValue("posButtonRect", "left", 0);
        posButtonRect.top = getValue("posButtonRect", "top", 0);
        posButtonRect.right = getValue("posButtonRect", "right", 0);
        posButtonRect.bottom = getValue("posButtonRect", "bottom", 0);

        openButtonRect.left = getValue("openButtonRect", "left", 0);
        openButtonRect.top = getValue("openButtonRect", "top", 0);
        openButtonRect.right = getValue("openButtonRect", "right", 0);
        openButtonRect.bottom = getValue("openButtonRect", "bottom", 0);

        foreColor = getValue("content", "foreColor", 0x808080);
        backColor = getValue("content", "backColor", 0x808080);
        fontSize = getValue("content", "fontSize", 28);
    }

    static public void save() {
        //
    }

    static protected int getValue(String section, String variable, int defaultValue) {
        int ret = 0;

        try {
            Integer.parseInt(ConfigurationFile.getProfileString(FILENAME, section, variable, ""));
        } catch (NumberFormatException e) {
            ret = defaultValue;
        } catch (IOException e) {
        }

        return ret;
    }
}
