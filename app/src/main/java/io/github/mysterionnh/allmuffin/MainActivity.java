package io.github.mysterionnh.allmuffin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.LinearLayout;

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

        // Creating the SharedPreferences, the key-value file used for settings
        PreferenceManager.setDefaultValues(_context, R.xml.preferences, false);

        String color = "WHITE";
        SharedPreferences shPref = PreferenceManager.getDefaultSharedPreferences(_context);
        if (shPref.getBoolean(SettingsActivity.KEY_PREF_ALLOW_BG_COLOR, false)) {
            color = shPref.getString(SettingsActivity.KEY_PREF_BG_COLOR, "WHITE");
        }
        try {
            findViewById(R.id.MainFrame).setBackgroundColor(Color.parseColor(color));
        } catch (java.lang.IllegalArgumentException e) {
            e.printStackTrace();
        }

        if (shPref.getBoolean(SettingsActivity.KEY_PREF_SHOW_POPUP, true)) {
            new NewMessagePopup(_context).showHintPopup();
        }
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