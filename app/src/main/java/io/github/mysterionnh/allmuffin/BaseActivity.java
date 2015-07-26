package io.github.mysterionnh.allmuffin;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * This is a base for all activities in this project
 * It contains methods useful for all other activities and whatnot else :D
 * I probably should put this variables in different classes, for example the popup, but meh..
 * TODO I guess :/
 */
public class BaseActivity extends Activity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        return super.onOptionsItemSelected(item);
    }
}
