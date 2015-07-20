package io.github.mysterionnh.allmuffin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends BaseActivity {

    final Context _context = (Context) this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Sets up the button listener for the submit button
        Button btn1 = (Button)findViewById(R.id.drOpenButton);
        btn1.setOnClickListener(btnListener);
        Button btn2 = (Button)findViewById(R.id.tiOpenButton);
        btn2.setOnClickListener(btnListener2);
        Button btn3 = (Button)findViewById(R.id.pgOpenButton);
        btn3.setOnClickListener(btnListener3);
    }

    private View.OnClickListener btnListener = new View.OnClickListener() {

        public void onClick(View v) {
            startActivity(new Intent(_context, RuleOfThreeActivity.class));
        }
    };

    private View.OnClickListener btnListener2 = new View.OnClickListener() {

        public void onClick(View v) {
            startActivity(new Intent(_context, TimerActivity.class));
        }
    };

    private View.OnClickListener btnListener3 = new View.OnClickListener() {

        public void onClick(View v) {
            startActivity(new Intent(_context, PlaygroundActivity.class));
        }
    };
}

//TODO: Get settings (and saving to work)
//TODO: (LONG TERM) Add other projects