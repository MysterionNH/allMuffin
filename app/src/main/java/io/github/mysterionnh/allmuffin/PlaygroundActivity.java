package io.github.mysterionnh.allmuffin;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * An Activity only there to be cool and to test around with different stuff
 * Mostly used for fun and for development tests
 */
public class PlaygroundActivity extends BaseActivity {

    /** A hack used to get the context of this Activity in places where I need it put am not allowed
     * to get it. Sad life.
     */
    final Context _context = (Context) this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playground);

        // Initializes the buttons. Currently only the first two work
        findViewById(R.id.bakeToastBtn).setOnClickListener(btnListener0);
        findViewById(R.id.notifyStatusBarBtn).setOnClickListener(btnListener1);
        findViewById(R.id.button3).setOnClickListener(btnListener2);
        findViewById(R.id.button4).setOnClickListener(btnListener3);
    }

    // Opens a Toast with the text in the text field before the button
    private View.OnClickListener btnListener0 = new View.OnClickListener() {
        public void onClick(View v) {
            EditText toastText = (EditText) findViewById(R.id.toastText);
            Toast toast = Toast.makeText(_context, toastText.getText().toString(), Toast.LENGTH_LONG);
            toast.show();
        }
    };

    // Opens a (default) Notification with the text...
    private View.OnClickListener btnListener1 = new View.OnClickListener() {
        public void onClick(View v) {
            NewMessageNotification.notify(_context, (((EditText) findViewById(R.id.statusBarText)).getText().toString()), 5);
        }
    };

    // Does nothing yet
    private View.OnClickListener btnListener2 = new View.OnClickListener() {
        public void onClick(View v) {
        }
    };

    // Same here
    private View.OnClickListener btnListener3 = new View.OnClickListener() {
        public void onClick(View v) {
        }
    };
}
