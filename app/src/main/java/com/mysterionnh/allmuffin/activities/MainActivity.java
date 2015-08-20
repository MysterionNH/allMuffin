package com.mysterionnh.allmuffin.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

import com.mysterionnh.allmuffin.R;
import com.mysterionnh.allmuffin.activities.games.GameHubActivity;
import com.mysterionnh.allmuffin.fragments.SettingsFragment;
import com.mysterionnh.allmuffin.helper.NewMessagePopup;

/**
 * The main Activity of this app, from here all other "projects" are accessible
 */
public class MainActivity extends BaseActivity {

    private final Context mContext = (Context) this;
    private final View.OnClickListener btnListener = new View.OnClickListener() {

        public void onClick(View v) {
            startActivity(new Intent(mContext, RuleOfThreeActivity.class));
        }
    };
    private final View.OnClickListener btnListener2 = new View.OnClickListener() {

        public void onClick(View v) {
            startActivity(new Intent(mContext, TimerActivity.class));
        }
    };
    private final View.OnClickListener btnListener3 = new View.OnClickListener() {

        public void onClick(View v) {
            startActivity(new Intent(mContext, PlaygroundActivity.class));
        }
    };
    private final View.OnClickListener btnListener4 = new View.OnClickListener() {

        public void onClick(View v) {
            startActivity(new Intent(mContext, CalculatorActivity.class));
        }
    };
    private final View.OnClickListener btnListener5 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(mContext, GameHubActivity.class));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PreferenceManager.setDefaultValues(mContext, R.xml.preferences, false);
        setAppLanguageAccordingToSettings(mContext);
        // Has to be done after setting the language, otherwise the change doesn't apply here
        setContentView(R.layout.activity_main);

        SharedPreferences shPref = PreferenceManager.getDefaultSharedPreferences(mContext);
        if (shPref.getBoolean(SettingsFragment.KEY_PREF_SHOW_POPUP,
                Boolean.valueOf(mContext.getResources().getString(R.string.pref_default_value_show_start_popup)))) {
            new NewMessagePopup(mContext).showHintPopup();
        }

        setBGColorAccordingToSettings(mContext);

        // Sets up the button listeners for the open buttons
        findViewById(R.id.drOpenButton).setOnClickListener(btnListener);    //RuleOfThree
        findViewById(R.id.tiOpenButton).setOnClickListener(btnListener2);   //Timer
        findViewById(R.id.pgOpenButton).setOnClickListener(btnListener3);   //Playground
        findViewById(R.id.calcOpenButton).setOnClickListener(btnListener4); //Calculator
        findViewById(R.id.ghOpenButton).setOnClickListener(btnListener5);   //GameHub
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //TODO : Add a do you really want to leave dialog
    }
}