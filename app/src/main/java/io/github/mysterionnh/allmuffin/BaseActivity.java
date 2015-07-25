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

    final public int DEFAULT_TEXT_SIZE = 20;

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
            openHintPopup();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Opens a popup with a message, default dialog one with only a title, a bod and a close button
     *
     * @param title     The title text (String) used in the popup
     * @param body      The body text (String) used in the popup
     * @param btnText   The text (String) on the close button of the popup
     * @param color     The color (e.g. Color.RED) used for the title of the popup
     * @param align     The Gravity (e.g. Gravity.FILL_HORIZONTAL) of the body text
     * @param text_size The size (int) of the title.. i guess
     */
    public void openPopup(String title, String body, String btnText, int color, int align, int text_size) {
        // The object that builds our popup
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.PopupCustom));

        //Everything for the title
        TextView myMsg = new TextView(this);
        myMsg.setText(title);
        myMsg.setTextColor(color);
        myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
        myMsg.setTextSize(text_size);

        //Set custom title
        builder.setCustomTitle(myMsg);

        //Set body text
        builder.setMessage(body);

        //Add confirmation button
        builder.setPositiveButton(btnText, null);

        //Shows the finished popup
        AlertDialog dialog = builder.show();

        // I don't completely understand this, but it seems like I'm looking up how it has to look..
        TextView messageText = (TextView) dialog.findViewById(android.R.id.message);

        messageText.setGravity(align);
    }

    /**
     * Just like @see openPopup, just with default values, will certainly b removed in the future (TODO)
     * Opens when the MenuItem in the Actionbar is clicked
     */
    public void openHintPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.PopupCustom));
        TextView myMsg = new TextView(this);
        myMsg.setText(R.string.popup_title);
        myMsg.setTextColor(Color.DKGRAY);
        myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
        myMsg.setTextSize(20);
        builder.setCustomTitle(myMsg);
        builder.setMessage(R.string.popup_body);
        builder.setPositiveButton(R.string.popup_btnText, null);
        AlertDialog dialog = builder.show();
        TextView messageText = (TextView) dialog.findViewById(android.R.id.message);
        messageText.setGravity(Gravity.FILL_HORIZONTAL);
    }
}
