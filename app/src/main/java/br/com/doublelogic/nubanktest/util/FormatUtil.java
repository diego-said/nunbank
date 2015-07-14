package br.com.doublelogic.nubanktest.util;

import android.content.Context;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by diegoalvessaidsimao on 13/07/15.
 */
public class FormatUtil {

    public static DecimalFormat getDecimalFormat(Context context, int resId) {
        String pattern = context.getString(resId);
        return getDecimalFormat(context, pattern);
    }

    public static DecimalFormat getDecimalFormat(Context context, String pattern) {
        Locale locale = context.getResources().getConfiguration().locale;

        DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(locale);
        decimalFormat.applyPattern(pattern);

        return decimalFormat;
    }

}
