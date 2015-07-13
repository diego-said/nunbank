package br.com.doublelogic.nubanktest.util;

import android.content.Context;
import android.widget.Toast;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by diegoalvessaidsimao on 12/07/15.
 */
public class ToastUtil {

    public static void message(Context context, String message) {
        toast(context, message, Toast.LENGTH_SHORT);
    }

    public static void message(Context context, int resId) {
        String message = context.getString(resId);
        if(StringUtils.isNotEmpty(message)) {
            toast(context, message, Toast.LENGTH_SHORT);
        }
    }

    private static void toast(Context context, String message, int duration) {
        Toast.makeText(context, message, duration).show();
    }



}
