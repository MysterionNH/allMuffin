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
 */
public class BaseActivity extends Activity {

    final public int DEFAULT_TEXT_SIZE = 20;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        if (id == R.id.action_hint) {
            openHintPopup();
        }

        return super.onOptionsItemSelected(item);
    }

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

        //Create custom message
        TextView messageText = (TextView) dialog.findViewById(android.R.id.message);
        messageText.setGravity(align);
    }

    //Opens the Info popup from the menu, always the same, that's why it's got it's own method
    public void openHintPopup() {
        // The object that builds our popup
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.PopupCustom));

        //Everything for the title
        TextView myMsg = new TextView(this);
        myMsg.setText(R.string.popup_title);
        myMsg.setTextColor(Color.DKGRAY);
        myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
        myMsg.setTextSize(20);

        //Set custom title
        builder.setCustomTitle(myMsg);

        //Set body text
        builder.setMessage(R.string.popup_body);

        //Add confirmation button
        builder.setPositiveButton(R.string.popup_btnText, null);

        //Shows the finished popup
        AlertDialog dialog = builder.show();

        //Create custom message
        TextView messageText = (TextView) dialog.findViewById(android.R.id.message);
        messageText.setGravity(Gravity.FILL_HORIZONTAL);
    }
}
