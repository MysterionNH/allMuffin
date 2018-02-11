package com.niklashalle.muffin20.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.niklashalle.muffin20.activities.R;
import com.niklashalle.muffin20.helper.MagicAppRestarter;

public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    public static final String KEY_PREF_SHOW_POPUP = "pref_key_show_start_popup";
    public static final String KEY_PREF_ALLOW_BG_COLOR = "pref_key_allow_bg_color_change";
    public static final String KEY_PREF_BG_COLOR = "pref_key_bg_color";
    public static final String KEY_PREF_ALLOW_LANG_CHANGE = "pref_key_allow_lang_change";
    public static final String KEY_PREF_LANG = "pref_key_lang";
    private static final String KEY_PREF_CALC = "pref_key_calc";
    private Context mContext;
    private SharedPreferences mSharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        // Context can not be assigned earlier, there's none before
        mContext = getActivity();

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        mSharedPreferences.registerOnSharedPreferenceChangeListener(this);

        Preference restartButton = findPreference(getString(R.string.pref_button_restart_key));
        Preference resetButton = findPreference(getString(R.string.pref_button_reset_key));

        restartButton.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                MagicAppRestarter.doRestart(mContext);
                return true;
            }
        });

        resetButton.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                SharedPreferences.Editor editor = mSharedPreferences.edit();
                editor.clear();
                editor.commit();
                MagicAppRestarter.doRestart(mContext);
                return true;
            }
        });
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Resources res = mContext.getResources();
        String updateMsg = "";
        switch (key) {
            case KEY_PREF_SHOW_POPUP: {
                if (mSharedPreferences.getBoolean(KEY_PREF_SHOW_POPUP,
                        Boolean.valueOf(res.getString(R.string.pref_default_value_show_start_popup)))) {
                    updateMsg = res.getString(R.string.pref_positive_msg_show_start_popup);
                } else {
                    updateMsg = res.getString(R.string.pref_negative_msg_show_start_popup);
                }
                break;
            }
            case KEY_PREF_ALLOW_BG_COLOR: {
                if (mSharedPreferences.getBoolean(KEY_PREF_ALLOW_BG_COLOR,
                        Boolean.valueOf(res.getString(R.string.pref_default_value_allow_bg_color_change)))) {
                    updateMsg = res.getString(R.string.pref_positive_allow_bg_color_change);
                } else {
                    updateMsg = res.getString(R.string.pref_negative_allow_bg_color_change);
                }
                break;
            }
            case KEY_PREF_BG_COLOR: {
                updateMsg = res.getString(R.string.pref_onchange_msg_bg_color,
                        mSharedPreferences.getString(KEY_PREF_BG_COLOR, res.getString(R.string.pref_default_value_bg_color)));
                break;
            }
            case KEY_PREF_ALLOW_LANG_CHANGE: {
                if (mSharedPreferences.getBoolean(KEY_PREF_ALLOW_LANG_CHANGE,
                        Boolean.valueOf(res.getString(R.string.pref_default_value_allow_lang_change)))) {
                    updateMsg = res.getString(R.string.pref_positive_msg_allow_lang_change);
                } else {
                    updateMsg = res.getString(R.string.pref_negative_msg_allow_lang_change);
                }
                break;
            }
            case KEY_PREF_LANG: {
                //noinspection ConstantConditions
                switch (mSharedPreferences.getString(KEY_PREF_LANG, res.getString(R.string.pref_default_lang))) {
                    case "uk": {
                        updateMsg = res.getString(R.string.pref_onchange_lang_uk);
                        break;
                    }
                    case "de": {
                        updateMsg = res.getString(R.string.pref_onchange_lang_de);
                        break;
                    }
                    default: {
                        updateMsg = res.getString(R.string.pref_onchange_lang);
                        break;
                    }
                }
                break;
            }
            case KEY_PREF_CALC: {
                updateMsg = (mContext.getResources().getString(R.string.pref_onchange_calc) + " " + sharedPreferences.getString(SettingsFragment.KEY_PREF_CALC,
                        mContext.getResources().getString(R.string.pref_default_calc)));
                break;
            }
        }
        Toast toast = Toast.makeText(mContext,
                updateMsg,
                Toast.LENGTH_SHORT);
        toast.show();
    }
}