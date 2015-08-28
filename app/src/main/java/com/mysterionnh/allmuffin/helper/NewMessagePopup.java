package com.mysterionnh.allmuffin.helper;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.widget.TextView;

import com.mysterionnh.allmuffin.R;

public class NewMessagePopup {

    private final Context _context;
    private String title;
    private String body;
    private String btnText;
    private int color;
    private int align;
    private int textSize;

    public NewMessagePopup(Context ct) {
        _context = ct;
        title = "Default title";
        body = "Default body";
        btnText = "Okay";
        color = Color.BLACK;
        align = Gravity.FILL_HORIZONTAL;
        textSize = 20;
    }

    /**
     * @param align The Gravity (e.g. Gravity.FILL_HORIZONTAL) of the body text
     */
    public void setAlign(@SuppressWarnings("SameParameterValue") int align) {
        this.align = align;
    }

    /**
     * @param body The body text (String) used in the popup
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * @param btnText The text (String) on the close button of the popup
     */
    public void setBtnText(String btnText) {
        this.btnText = btnText;
    }

    /**
     * @param color The color (e.g. Color.RED) used for the title of the popup
     */
    public void setColor(@SuppressWarnings("SameParameterValue") int color) {
        this.color = color;
    }

    /**
     * @param textSize The size (int) of the title.. i guess
     */
    public void setTextSize(@SuppressWarnings("SameParameterValue") int textSize) {
        this.textSize = textSize;
    }

    /**
     * @param title The title text (String) used in the popup
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Opens a popup with a message, default dialog one with only a title, a bod and a close button
     */
    public void show() {
        // The object that builds our popup
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(_context, R.style.CustomPopup));

        //Everything for the title
        TextView myMsg = new TextView(_context);
        myMsg.setText(title);
        myMsg.setTextColor(color);
        myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
        myMsg.setTextSize(textSize);

        //Set custom title
        builder.setCustomTitle(myMsg);

        //Set body text
        builder.setMessage(body);

        //Add confirmation button
        builder.setPositiveButton(btnText, null);

        //Shows the finished popup
        AlertDialog dialog = builder.show();

        // Sets the gravity of the message view in the finished (hence why we do it after show()) dialog
        TextView messageText = (TextView) dialog.findViewById(android.R.id.message);

        messageText.setGravity(align);
    }

    /**
     * Just like {@see show()}, just with default values, will maybe be removed in the future
     * Opens when the MenuItem in the Actionbar is clicked
     */
    public void showHintPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(_context, R.style.CustomPopup));
        TextView myMsg = new TextView(_context);
        myMsg.setText(R.string.popup_title);
        myMsg.setTextColor(Color.DKGRAY);
        myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
        myMsg.setTextSize(20);
        builder.setCustomTitle(myMsg);
        builder.setMessage(R.string.popup_body);
        builder.setPositiveButton(R.string.popup_positive_button_text, null);
        AlertDialog dialog = builder.show();
        TextView messageText = (TextView) dialog.findViewById(android.R.id.message);
        messageText.setGravity(Gravity.FILL_HORIZONTAL);
    }
}