package br.com.doublelogic.nubanktest.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.doublelogic.nubanktest.R;
import br.com.doublelogic.nubanktest.rest.bean.Bill;
import br.com.doublelogic.nubanktest.service.BillingService;
import br.com.doublelogic.nubanktest.service.util.LocalBroadcastMessages;
import br.com.doublelogic.nubanktest.service.util.ServiceKeys;
import br.com.doublelogic.nubanktest.util.LogUtil;
import br.com.doublelogic.nubanktest.view.billing.BillState;
import br.com.doublelogic.nubanktest.view.billing.BillingPagerAdapter;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends ActionBarActivity {

    private final static String TAG = LogUtil.getTag(MainActivity.class);

    private View loadingBar;

    private TextView triangle;

    private ViewPager billingPager;
    private BillingPagerAdapter billingPagerAdapter;

    private List<Bill> listBills;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);

        listBills = new ArrayList<>();

        loadingBar = findViewById(R.id.loadingBar);
        triangle = (TextView) findViewById(R.id.triangle);

        billingPagerAdapter = new BillingPagerAdapter(getApplicationContext(), getSupportFragmentManager());

        billingPager = (ViewPager) findViewById(R.id.billingPager);
        billingPager.setAdapter(billingPagerAdapter);
        billingPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if(listBills != null && listBills.size() > 0) {
                    Bill bill = listBills.get(position);
                    BillState billState = BillState.getBillState(bill.getState());
                    triangle.setTextColor(getResources().getColor(billState.getColorId()));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver();

        BillingService.startListBills(getApplicationContext());
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver();
    }

    private BroadcastReceiver messageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (StringUtils.equalsIgnoreCase(LocalBroadcastMessages.COMMON.INTERN_PROBLEM, intent.getAction())) {
                // do something
            } else if (StringUtils.equalsIgnoreCase(LocalBroadcastMessages.COMMON.INTERNET_PROBLEM, intent.getAction())) {
                // do something
            } else if (StringUtils.equalsIgnoreCase(LocalBroadcastMessages.REST.CONNECTION_PROBLEM, intent.getAction())) {
                // do something
            } else if (StringUtils.equalsIgnoreCase(LocalBroadcastMessages.REST.HTTP_400_ERROR, intent.getAction())) {
                // do something
            } else if (StringUtils.equalsIgnoreCase(LocalBroadcastMessages.REST.HTTP_500_ERROR, intent.getAction())) {
                // do something
            } else if (StringUtils.equalsIgnoreCase(LocalBroadcastMessages.REST.JSON_PARSER_ERROR, intent.getAction())) {
                // do something
            } else if (StringUtils.equalsIgnoreCase(LocalBroadcastMessages.SERVICE.PRE_EXECUTE_LIST_BILLS, intent.getAction())) {
                loadingBar.setVisibility(View.VISIBLE);
            } else if (StringUtils.equalsIgnoreCase(LocalBroadcastMessages.SERVICE.POST_EXECUTE_LIST_BILLS, intent.getAction())) {
                loadingBar.setVisibility(View.GONE);

                Bundle bundle = intent.getBundleExtra(ServiceKeys.EXTRA_BUNDLE_KEY);
                if (bundle != null) {
                    Serializable rawResult = bundle.getSerializable(ServiceKeys.RESULT_KEY);
                    if (rawResult != null) {
                        ArrayList<Bill> listBills = (ArrayList) rawResult;

                        MainActivity.this.listBills.clear();
                        MainActivity.this.listBills.addAll(listBills);

                        if(listBills != null && listBills.size() > 0) {
                            Bill bill = listBills.get(0);
                            BillState billState = BillState.getBillState(bill.getState());
                            triangle.setTextColor(getResources().getColor(billState.getColorId()));
                        }

                        billingPagerAdapter.setBillsList(listBills);
                    }
                }
            }
            Log.d("receiver", "Got message: " + intent.getAction());
        }
    };

    private void registerReceiver() {
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(messageReceiver, new IntentFilter(LocalBroadcastMessages.COMMON.INTERN_PROBLEM));
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(messageReceiver, new IntentFilter(LocalBroadcastMessages.COMMON.INTERNET_PROBLEM));
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(messageReceiver, new IntentFilter(LocalBroadcastMessages.REST.CONNECTION_PROBLEM));
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(messageReceiver, new IntentFilter(LocalBroadcastMessages.REST.HTTP_400_ERROR));
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(messageReceiver, new IntentFilter(LocalBroadcastMessages.REST.HTTP_500_ERROR));
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(messageReceiver, new IntentFilter(LocalBroadcastMessages.REST.JSON_PARSER_ERROR));
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(messageReceiver, new IntentFilter(LocalBroadcastMessages.SERVICE.PRE_EXECUTE_LIST_BILLS));
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(messageReceiver, new IntentFilter(LocalBroadcastMessages.SERVICE.POST_EXECUTE_LIST_BILLS));
    }

    private void unregisterReceiver() {
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(messageReceiver);
    }
}
