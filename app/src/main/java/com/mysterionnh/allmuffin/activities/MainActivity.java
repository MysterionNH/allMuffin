package com.mysterionnh.allmuffin.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
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

    /**
     * Open a confirmation dialog on back press and close it properly if wanted
     */
    @Override
    public void onBackPressed() {
        Resources res = mContext.getResources();
        new AlertDialog.Builder(this)
                .setIcon(R.mipmap.ic_launcher)
                .setTitle(res.getString(R.string.leave_title))
                .setMessage(res.getString(R.string.leave_message))
                .setPositiveButton(res.getString(R.string.popup_positive_button_text), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton(R.string.popup_negative_button_text, null)
                .show();
    }
}