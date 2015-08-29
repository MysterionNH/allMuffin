package com.mysterionnh.allmuffin.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;

import com.mysterionnh.allmuffin.R;
import com.mysterionnh.allmuffin.fragments.SettingsFragment;
import com.mysterionnh.allmuffin.helper.MagicAppRestarter;
import com.mysterionnh.allmuffin.helper.NewMessagePopup;

import java.util.Locale;

/**
 * This is a base for all activities in this project
 * It contains the default menu. Nothing else atm.
 */
@SuppressLint("Registered")
public class BaseActivity extends Activity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_default, menu);
        return true;
    }

    /**
     * This is the default OptionsMenu, which is used in all (Child-) Activities when not overridden
     * Contains two items, one for the default settings page, the other for an default information-
     * page over this app
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        if (id == R.id.action_hint) {
            new NewMessagePopup(this).showHintPopup();
            return true;
        }

        if (id == R.id.action_restart) {
            MagicAppRestarter.doRestart(this);
        }
        return super.onOptionsItemSelected(item);
    }

    protected void setBGColorAccordingToSettings(Context con) {
        // Creating the SharedPreferences, the key-value file used for settings

        String color = con.getResources().getString(R.string.pref_default_value_bg_color);
        SharedPreferences shPref = PreferenceManager.getDefaultSharedPreferences(con);
        if (shPref.getBoolean(SettingsFragment.KEY_PREF_ALLOW_BG_COLOR,
                Boolean.valueOf(con.getResources().getString(R.string.pref_default_value_allow_bg_color_change)))) {
            color = shPref.getString(SettingsFragment.KEY_PREF_BG_COLOR,
                    con.getResources().getString(R.string.pref_default_value_bg_color));
        }
        try {
            findViewById(R.id.MainFrame).setBackgroundColor(Color.parseColor(color));
        } catch (java.lang.IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    protected void setAppLanguageAccordingToSettings(Context con) {
        SharedPreferences shPref = PreferenceManager.getDefaultSharedPreferences(con);

        // This should be dangerous, but apparently isn't
        // pref_default_lang is "Phone Default" and KEY_PREF_LANG can be "--", both aren't valid
        // locales afaik but it still works somehow
        if (shPref.getBoolean(SettingsFragment.KEY_PREF_ALLOW_LANG_CHANGE,
                Boolean.valueOf(con.getResources().getString(R.string.pref_default_value_allow_lang_change)))) {
            Locale loc = new Locale(shPref.getString(SettingsFragment.KEY_PREF_LANG,
                    con.getResources().getString(R.string.pref_default_lang)));
            Locale.setDefault(loc);
            android.content.res.Configuration config = new android.content.res.Configuration();
            config.locale = loc;
            con.getResources().updateConfiguration(config, con.getResources().getDisplayMetrics());
        }
    }
}
