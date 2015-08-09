package com.mysterionnh.allmuffin.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.mysterionnh.allmuffin.R;
import com.mysterionnh.allmuffin.fragments.SettingsFragment;
import com.mysterionnh.allmuffin.helper.MagicAppRestarter;
import com.mysterionnh.allmuffin.helper.NewMessagePopup;

/**
 * Settings 'n stuff
 */
public class SettingsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
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

        if (id == R.id.action_hint) {
            new NewMessagePopup(this).showHintPopup();
            return true;
        }

        if (id == R.id.action_restart) {
            MagicAppRestarter.doRestart(this);
        }
        return super.onOptionsItemSelected(item);
    }
}