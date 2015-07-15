package br.com.doublelogic.nubanktest.view.billing;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

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
        Bill bill = billsList.get(position);
        BillState billState = BillState.getBillState(bill.getState());
        String title = StringUtils.upperCase(sdf.format(bill.getSummary().getClose_date()));
        Spannable spannable = new SpannableString(title);
        spannable.setSpan(new ForegroundColorSpan(context.getResources().getColor(billState.getColorId())), 0, title.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannable;
    }

    public void setBillsList(List<Bill> billsList) {
        this.billsList.clear();
        this.billsList.addAll(billsList);
        notifyDataSetChanged();
    }
}
