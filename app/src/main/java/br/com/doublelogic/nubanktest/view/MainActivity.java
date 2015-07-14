package br.com.doublelogic.nubanktest.view;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.crashlytics.android.Crashlytics;

import br.com.doublelogic.nubanktest.R;
import br.com.doublelogic.nubanktest.service.BillingService;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        //setContentView(R.layout.activity_main);
        setContentView(R.layout.fragment_bill_interface);
    }

    @Override
    protected void onResume() {
        super.onResume();
        BillingService.startListBills(getApplicationContext());
    }
}
