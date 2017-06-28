package com.example.ajp.s_cape_app.Utility;

import android.app.Activity;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by AJP on 5/7/17.
 */

public class Utility_KeyboardDismiss {

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        if(activity.getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(), 0);
        }

    }

}
