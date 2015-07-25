package io.github.mysterionnh.allmuffin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * The main Activity of this app, from here all other "projects" are reachable
 */
public class MainActivity extends BaseActivity {

    /** A hack used to get the context of this Activity in places where I need it put am not allowed
      * to get it. Sad life.
      */
    final Context _context = (Context) this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Sets up the button listeners for the open buttons
        findViewById(R.id.drOpenButton).setOnClickListener(btnListener);    //RuleOfThree
        findViewById(R.id.tiOpenButton).setOnClickListener(btnListener2);   //Timer
        findViewById(R.id.pgOpenButton).setOnClickListener(btnListener3);   //Playground
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