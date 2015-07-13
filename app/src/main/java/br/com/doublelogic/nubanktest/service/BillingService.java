package br.com.doublelogic.nubanktest.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

import br.com.doublelogic.nubanktest.rest.BillingAPI;
import br.com.doublelogic.nubanktest.rest.bean.Bill;
import br.com.doublelogic.nubanktest.service.util.LocalBroadcastMessages;
import br.com.doublelogic.nubanktest.service.util.ServiceKeys;
import br.com.doublelogic.nubanktest.service.util.ServiceMessages;
import br.com.doublelogic.nubanktest.util.LogUtil;
import br.com.doublelogic.nubanktest.util.NetworkUtil;

public class BillingService extends IntentService {

    private final static String TAG = LogUtil.getTag(BillingService.class);

    public BillingService() {
        super(TAG);
    }

    public static void startListBills(Context context) {
        Intent intent = new Intent(context, BillingService.class);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        ServiceMessages.sendBroadcastMessage(LocalBroadcastMessages.SERVICE.PRE_EXECUTE_LIST_BILLS, this);
        if (intent != null && NetworkUtil.isConnected(getApplicationContext())) {
            BillingAPI api = new BillingAPI(getApplicationContext());
            ArrayList<Bill> listBills = api.bills();
            if(listBills != null) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(ServiceKeys.RESULT_KEY, listBills);

                ServiceMessages.sendBroadcastMessageWithExtra(LocalBroadcastMessages.SERVICE.POST_EXECUTE_LIST_BILLS, this, bundle);
            }
        } else {
            if(!NetworkUtil.isConnected(getApplicationContext())) {
                ServiceMessages.sendBroadcastMessage(LocalBroadcastMessages.COMMON.INTERNET_PROBLEM, this);
            } else {
                ServiceMessages.sendBroadcastMessage(LocalBroadcastMessages.COMMON.INTERN_PROBLEM, this);
            }
        }
    }

}
