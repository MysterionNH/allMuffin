package io.github.mysterionnh.allmuffin;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Meh
 */
public class SettingsActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    Context _context = this;

    public static final String KEY_PREF_SHOW_POPUP = "pref_key_show_start_popup";
    public static final String KEY_PREF_ALLOW_BG_COLOR = "pref_key_allow_bg_color_change";
    public static final String KEY_PREF_BG_COLOR = "pref_key_bg_color";
    public static final String KEY_PREF_ALLOW_LANG_CHANGE = "pref_key_allow_lang_change";
    public static final String KEY_PREF_LANG = "pref_key_lang";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(KEY_PREF_SHOW_POPUP)) {
            NewMessagePopup popup = new NewMessagePopup(_context);
            popup.setTitle("Setting changed");
            popup.setBody("Wuhu");
            popup.show();
        }
    }
}