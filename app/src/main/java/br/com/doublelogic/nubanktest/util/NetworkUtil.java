package br.com.doublelogic.nubanktest.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by diegoalvessaidsimao on 12/07/15.
 */
public class NetworkUtil {

    private static final String TAG  = LogUtil.getTag(NetworkUtil.class);

    public static boolean isConnected(Context context) {
        NetworkInfo activeNetwork = getActiveNetworkInfo(context);
        if(activeNetwork != null && activeNetwork.isAvailable() && activeNetwork.isConnected()) {
            return true;
        }
        return false;
    }

    private static NetworkInfo getActiveNetworkInfo(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }

}
