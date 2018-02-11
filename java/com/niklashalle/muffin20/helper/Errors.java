package com.niklashalle.muffin20.helper;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

public class Errors {
    /**
     * Show Error to User
     *
     * @param context  Context of Activity
     * @param errorMsg Error message to log
     */
    public static void errorToast(Context context, String errorMsg) {
        Toast toast = Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT);
        toast.show();
    }

    public static void errorPopup(Context context, String errorTitle, String errorBody, String btnText) {
        NewMessagePopup popup = new NewMessagePopup(context);
        popup.setTitle(errorTitle);
        popup.setBody(errorBody);
        popup.setBtnText(btnText);
        popup.setColor(Color.RED);
        popup.setAlign(Gravity.FILL_HORIZONTAL);

        popup.show();
    }

    public static void logError(Context context, String msg) {
        Log.e(context.getClass().getName(), msg);
    }

    public static void logWarning(Context context, String msg) {
        Log.w(context.getClass().getName(), msg);
    }
}
