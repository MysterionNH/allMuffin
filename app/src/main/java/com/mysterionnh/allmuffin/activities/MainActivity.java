package com.mysterionnh.allmuffin.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

import com.mysterionnh.allmuffin.R;
import com.mysterionnh.allmuffin.fragments.SettingsFragment;
import com.mysterionnh.allmuffin.helper.NewMessagePopup;

/**
 * The main Activity of this app, from here all other "projects" are reachable
 */
public class MainActivity extends BaseActivity {

    private final Context _context = (Context) this;
    private final View.OnClickListener btnListener = new View.OnClickListener() {

        public void onClick(View v) {
            startActivity(new Intent(_context, RuleOfThreeActivity.class));
        }
    };
    private final View.OnClickListener btnListener2 = new View.OnClickListener() {

        public void onClick(View v) {
            startActivity(new Intent(_context, TimerActivity.class));
        }
    };
    private final View.OnClickListener btnListener3 = new View.OnClickListener() {

        public void onClick(View v) {
            startActivity(new Intent(_context, PlaygroundActivity.class));
        }
    };
    private final View.OnClickListener btnListener4 = new View.OnClickListener() {

        public void onClick(View v) {
            startActivity(new Intent(_context, CalculatorActivity.class));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PreferenceManager.setDefaultValues(_context, R.xml.preferences, false);
        setAppLanguageAccordingToSettings(_context);
        // Has to be done after setting the language, otherwise the change doesn't apply here
        setContentView(R.layout.activity_main);

        SharedPreferences shPref = PreferenceManager.getDefaultSharedPreferences(_context);
        if (shPref.getBoolean(SettingsFragment.KEY_PREF_SHOW_POPUP,
                Boolean.valueOf(_context.getResources().getString(R.string.pref_default_value_show_start_popup)))) {
            new NewMessagePopup(_context).showHintPopup();
        }

        setBGColorAccordingToSettings(_context);

        // Sets up the button listeners for the open buttons
        findViewById(R.id.drOpenButton).setOnClickListener(btnListener);    //RuleOfThree
        findViewById(R.id.tiOpenButton).setOnClickListener(btnListener2);   //Timer
        findViewById(R.id.pgOpenButton).setOnClickListener(btnListener3);   //Playground
        findViewById(R.id.calcOpenButton).setOnClickListener(btnListener4); //Calculator
    }
}