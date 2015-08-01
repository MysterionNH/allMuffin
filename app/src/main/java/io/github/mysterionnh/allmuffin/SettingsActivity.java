package io.github.mysterionnh.allmuffin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * Meh
 */
public class SettingsActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    Context _context = this;
    public SharedPreferences mSharedPreferences;

    public static final String KEY_PREF_SHOW_POPUP = "pref_key_show_start_popup";
    public static final String KEY_PREF_ALLOW_BG_COLOR = "pref_key_allow_bg_color_change";
    public static final String KEY_PREF_BG_COLOR = "pref_key_bg_color";
    public static final String KEY_PREF_ALLOW_LANG_CHANGE = "pref_key_allow_lang_change";
    public static final String KEY_PREF_LANG = "pref_key_lang";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        mSharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

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
            Toast toast = Toast.makeText(_context,
                    _context.getResources().getString(R.string.app_restart),
                    Toast.LENGTH_SHORT);
            toast.show();
            MagicAppRestarter.doRestart(this);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        String updateMsg = "";
        switch (key) {
            case KEY_PREF_SHOW_POPUP :
            {
                if (mSharedPreferences.getBoolean(KEY_PREF_SHOW_POPUP,
                        Boolean.valueOf(_context.getResources().getString(R.string.pref_default_value_show_start_popup)))) {
                    updateMsg = _context.getResources().getString(R.string.pref_positive_msg_show_start_popup);
                } else {
                    updateMsg = _context.getResources().getString(R.string.pref_negative_msg_show_start_popup);
                }
                break;
            }
            case KEY_PREF_ALLOW_BG_COLOR :
            {
                if (mSharedPreferences.getBoolean(KEY_PREF_ALLOW_BG_COLOR,
                        Boolean.valueOf(_context.getResources().getString(R.string.pref_default_value_allow_bg_color_change)))) {
                    updateMsg = _context.getResources().getString(R.string.pref_positive_allow_bg_color_change);
                } else {
                    updateMsg = _context.getResources().getString(R.string.pref_negative_allow_bg_color_change);
                }
                break;
            }
            case KEY_PREF_BG_COLOR :
            {
                updateMsg = _context.getResources().getString(R.string.pref_onchange_msg_bg_color,
                        mSharedPreferences.getString(KEY_PREF_BG_COLOR, _context.getResources().getString(R.string.pref_default_value_bg_color)));
                break;
            }
            case KEY_PREF_ALLOW_LANG_CHANGE :
            {
                if (mSharedPreferences.getBoolean(KEY_PREF_ALLOW_LANG_CHANGE,
                        Boolean.valueOf(_context.getResources().getString(R.string.pref_default_value_allow_lang_change)))) {
                    updateMsg = _context.getResources().getString(R.string.pref_positive_msg_allow_lang_change);
                } else {
                    updateMsg = _context.getResources().getString(R.string.pref_negative_msg_allow_lang_change);
                }
                break;
            }
            case KEY_PREF_LANG :
            {
                switch (mSharedPreferences.getString(KEY_PREF_LANG, _context.getResources().getString(R.string.pref_default_lang))) {
                    case "uk" :
                    {
                        updateMsg = _context.getResources().getString(R.string.pref_onchange_lang_uk);
                        break;
                    }
                    case "de" :
                    {
                        updateMsg = _context.getResources().getString(R.string.pref_onchange_lang_de);
                        break;
                    }
                    default :
                    {
                        updateMsg = _context.getResources().getString(R.string.pref_onchange_lang);
                        break;
                    }
                }
            }
        }
        Toast toast = Toast.makeText(_context,
                updateMsg,
                Toast.LENGTH_SHORT);
        toast.show();
    }
}