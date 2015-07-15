package br.com.doublelogic.nubanktest.view.billing;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.doublelogic.nubanktest.rest.bean.Bill;
import br.com.doublelogic.nubanktest.util.LogUtil;

/**
 * Created by diegoalvessaidsimao on 14/07/15.
 */
public class BillingPagerAdapter extends FragmentStatePagerAdapter {

    private final static String TAG = LogUtil.getTag(BillingPagerAdapter.class);

    private final Context context;

    private List<Bill> billsList;
    private SimpleDateFormat sdf;

    public BillingPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;

        billsList = new ArrayList<>();
        sdf = new SimpleDateFormat("MMM");
    }

    @Override
    public Fragment getItem(int position) {
        BillInterfaceFragment fragment = new BillInterfaceFragment();
        fragment.setBill(billsList.get(position));
        return fragment;
    }

    @Override
    public int getCount() {
        return billsList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return StringUtils.upperCase(sdf.format(billsList.get(position).getSummary().getClose_date()));
    }

    public void setBillsList(List<Bill> billsList) {
        this.billsList.clear();
        this.billsList.addAll(billsList);
        notifyDataSetChanged();
    }
}
