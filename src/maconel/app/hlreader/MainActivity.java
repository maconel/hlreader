package maconel.app.hlreader;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {
    TextView mTextView = null;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i("hlreader", "MainActivity.onCreate|================");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mTextView = (TextView) findViewById(R.id.textview);
    }

    protected void onStart() {
        super.onStart();

        mTextView.init();
    }

    protected void onStop() {
        super.onStop();

        mTextView.uninit();
    }
}
