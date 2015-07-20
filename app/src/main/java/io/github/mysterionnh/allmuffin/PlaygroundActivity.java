package io.github.mysterionnh.allmuffin;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PlaygroundActivity extends BaseActivity {

    final Context _context = (Context) this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playground);

        Button btn0 = (Button)findViewById(R.id.bakeToastBtn);
        btn0.setOnClickListener(btnListener0);

        Button btn1 = (Button)findViewById(R.id.notifyStatusBarBtn);
        btn1.setOnClickListener(btnListener1);

        Button btn2 = (Button)findViewById(R.id.button3);
        btn2.setOnClickListener(btnListener2);

        Button btn3 = (Button)findViewById(R.id.button4);
        btn3.setOnClickListener(btnListener3);
    }

    private View.OnClickListener btnListener0 = new View.OnClickListener() {
        public void onClick(View v) {
            EditText toastText = (EditText) findViewById(R.id.toastText);
            Toast toast = Toast.makeText(_context, toastText.getText().toString(), Toast.LENGTH_LONG);
            toast.show();
        }
    };


    private View.OnClickListener btnListener1 = new View.OnClickListener() {
        public void onClick(View v) {
            NewMessageNotification.notify(_context, (((EditText) findViewById(R.id.statusBarText)).getText().toString()), 5);
        }
    };


    private View.OnClickListener btnListener2 = new View.OnClickListener() {
        public void onClick(View v) {
        }
    };


    private View.OnClickListener btnListener3 = new View.OnClickListener() {
        public void onClick(View v) {
        }
    };
}
