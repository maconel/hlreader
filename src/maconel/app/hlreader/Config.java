package maconel.app.hlreader;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Rect;

public class Config {
    protected static final String PREFS_NAME = "hlreader";
    public static Rect nextPageButtonRect = new Rect();
    public static Rect prevPageButtonRect = new Rect();
    public static Rect findButtonRect = new Rect();
    public static Rect posButtonRect = new Rect();
    public static Rect openButtonRect = new Rect();
    public static int contentForeColor;
    public static int contentBackColor;
    public static int buttonForeColor;
    public static int buttonBackColor;
    public static int fontSize;

    public static void load(Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);

        // size = (120, 120), position = (x_center, y_center)
        nextPageButtonRect.left = settings.getInt("nextPageButtonRect.left", 180);
        nextPageButtonRect.top = settings.getInt("nextPageButtonRect.top", 367);
        nextPageButtonRect.right = settings.getInt("nextPageButtonRect.right", 300);
        nextPageButtonRect.bottom = settings.getInt("nextPageButtonRect.bottom", 487);

        // size = (80, 80), position = (x_center, top_of_nextPageButton)
        prevPageButtonRect.left = settings.getInt("prevPageButtonRect.left", 200);
        prevPageButtonRect.top = settings.getInt("prevPageButtonRect.top", 257);
        prevPageButtonRect.right = settings.getInt("prevPageButtonRect.right", 280);
        prevPageButtonRect.bottom = settings.getInt("prevPageButtonRect.bottom", 337);

        // size = (80, 80), position = (x_center, y_top)
        findButtonRect.left = settings.getInt("findButtonRect.left", 200);
        findButtonRect.top = settings.getInt("findButtonRect.top", 20);
        findButtonRect.right = settings.getInt("findButtonRect.right", 280);
        findButtonRect.bottom = settings.getInt("findButtonRect.bottom", 100);

        // size = (80, 80), position = (x_left, y_top)
        posButtonRect.left = settings.getInt("posButtonRect.left", 20);
        posButtonRect.top = settings.getInt("posButtonRect.top", 20);
        posButtonRect.right = settings.getInt("posButtonRect.right", 100);
        posButtonRect.bottom = settings.getInt("posButtonRect.bottom", 100);

        // size = (80, 80), position = (x_right, y_top)
        openButtonRect.left = settings.getInt("openButtonRect.left", 380);
        openButtonRect.top = settings.getInt("openButtonRect.top", 20);
        openButtonRect.right = settings.getInt("openButtonRect.right", 460);
        openButtonRect.bottom = settings.getInt("openButtonRect.bottom", 100);

        contentForeColor = settings.getInt("content.contentForeColor", 0xFF808080);
        contentBackColor = settings.getInt("content.contentBackColor", 0xFF000000);
        buttonForeColor = settings.getInt("content.buttonForeColor", 0xFFFFFF00);
        buttonBackColor = settings.getInt("content.buttonBackColor", 0xFF808080);
        fontSize = settings.getInt("content.fontSize", 28);
    }

    public static void save(Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();

        editor.clear();
        editor.commit();

        editor.putInt("nextPageButtonRect.left", nextPageButtonRect.left);
        editor.putInt("nextPageButtonRect.top", nextPageButtonRect.top);
        editor.putInt("nextPageButtonRect.right", nextPageButtonRect.right);
        editor.putInt("nextPageButtonRect.bottom", nextPageButtonRect.bottom);

        editor.putInt("prevPageButtonRect.left", prevPageButtonRect.left);
        editor.putInt("prevPageButtonRect.top", prevPageButtonRect.top);
        editor.putInt("prevPageButtonRect.right", prevPageButtonRect.right);
        editor.putInt("prevPageButtonRect.bottom", prevPageButtonRect.bottom);

        editor.putInt("findButtonRect.left", findButtonRect.left);
        editor.putInt("findButtonRect.top", findButtonRect.top);
        editor.putInt("findButtonRect.right", findButtonRect.right);
        editor.putInt("findButtonRect.bottom", findButtonRect.bottom);

        editor.putInt("posButtonRect.left", posButtonRect.left);
        editor.putInt("posButtonRect.top", posButtonRect.top);
        editor.putInt("posButtonRect.right", posButtonRect.right);
        editor.putInt("posButtonRect.bottom", posButtonRect.bottom);

        editor.putInt("openButtonRect.left", openButtonRect.left);
        editor.putInt("openButtonRect.top", openButtonRect.top);
        editor.putInt("openButtonRect.right", openButtonRect.right);
        editor.putInt("openButtonRect.bottom", openButtonRect.bottom);

        editor.putInt("content.contentForeColor", contentForeColor);
        editor.putInt("content.contentBackColor", contentBackColor);
        editor.putInt("content.buttonForeColor", buttonForeColor);
        editor.putInt("content.buttonBackColor", buttonBackColor);
        editor.putInt("content.fontSize", fontSize);

        // editor.commit();
    }
}
