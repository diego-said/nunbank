package br.com.doublelogic.nubanktest.service.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

/**
 * Created by diegoalvessaidsimao on 11/07/15.
 */
public class ServiceMessages {

    public static void sendBroadcastMessage(String broadcastMessage, Context context) {
        sendBroadcastMessageWithExtra(broadcastMessage, context, null);
    }

    public static void sendBroadcastMessageWithExtra(String broadcastMessage, Context context, Bundle extra) {
        if(broadcastMessage != null && context != null) {
            Intent intent = new Intent(broadcastMessage);
            if(extra != null) {
                intent.putExtra(ServiceKeys.EXTRA_BUNDLE_KEY, extra);
            }
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        }
    }

}
