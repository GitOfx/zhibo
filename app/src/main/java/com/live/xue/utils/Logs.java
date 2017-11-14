package com.live.xue.utils;

import android.util.Log;

/**
 * Created by Ofx on 2017/10/27.
 */

public class Logs {
    public static final String default_tag = "app_tag";
    public static final boolean isDebug = true;
    public static void e(String msg) {
        if (isDebug) {
            Log.e(default_tag, msg );
        }
    }
}
