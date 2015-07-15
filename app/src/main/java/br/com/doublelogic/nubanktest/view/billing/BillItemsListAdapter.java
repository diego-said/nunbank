package br.com.doublelogic.nubanktest.view.billing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.doublelogic.nubanktest.R;
import br.com.doublelogic.nubanktest.rest.bean.BillItem;
import br.com.doublelogic.nubanktest.util.FormatUtil;
import br.com.doublelogic.nubanktest.util.LogUtil;

/**
 * Created by diegoalvessaidsimao on 13/07/15.
 */
public class BillItemsListAdapter extends BaseAdapter {

    private static final String TAG = LogUtil.getTag(BillItemsListAdapter.class);

    private final Context context;

    private final List<BillItem> billItemList;

    private final SimpleDateFormat sdf;
    private final DecimalFormat df;

    public BillItemsListAdapter(Context context) {
        this.context = context;
        billItemList = new ArrayList<>();

        sdf = new SimpleDateFormat("dd MMM");
        df = FormatUtil.getDecimalFormat(context, "###,###,###.00");
    }

    @Override
    public int getCount() {
        return billItemList.size();
    }

    @Override
    public BillItem getItem(int position) {
        return billItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        if(view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.cell_bill_item, parent, false);
        }

        ViewHolder holder = new ViewHolder();

        holder.billItemHighlight = (RelativeLayout) view.findViewById(R.id.billItemHighlight);

        holder.billItemCircle = view.findViewById(R.id.billItemCircle);
        holder.billItemBar = view.findViewById(R.id.billItemBar);
        holder.billItemBarHalf = view.findViewById(R.id.billItemBarHalf);

        holder.billItemDate = (TextView) view.findViewById(R.id.billItemDate);
        holder.billItemDesc = (TextView) view.findViewById(R.id.billItemDesc);
        holder.billItemValue = (TextView) view.findViewById(R.id.billItemValue);

        view.setTag(holder);

        holder.bind(billItemList.get(position), position == 0 ? true : false);

        return view;
    }

    public void setBillItemList(List<BillItem> list) {
        if (list != null) {
            billItemList.clear();
            billItemList.addAll(list);
            notifyDataSetChanged();
        }
    }

    private class ViewHolder {
        RelativeLayout billItemHighlight;
        View billItemCircle, billItemBar, billItemBarHalf;
        TextView billItemDate, billItemDesc, billItemValue;

        public void bind(BillItem billItem, boolean firstView) {
            if(firstView) {
                billItemBar.setVisibility(View.GONE);
                billItemBarHalf.setVisibility(View.VISIBLE);
            } else {
                billItemBar.setVisibility(View.VISIBLE);
                billItemBarHalf.setVisibility(View.GONE);
            }

            if(billItem.getAmount() < 0) {
                billItemDesc.setTextColor(context.getResources().getColor(R.color.bill_green));
                billItemValue.setTextColor(context.getResources().getColor(R.color.bill_green));
            } else {
                billItemDesc.setTextColor(context.getResources().getColor(R.color.bill_item_text));
                billItemValue.setTextColor(context.getResources().getColor(R.color.bill_item_text));
            }

            billItemDate.setText(StringUtils.upperCase(sdf.format(billItem.getPost_date())));

            StringBuilder itemTitle = new StringBuilder(StringUtils.trim(billItem.getTitle()));
            if(billItem.getCharges() != null && billItem.getCharges() > 1) {
                itemTitle.append(" ");
                itemTitle.append(billItem.getIndex() + 1);
                itemTitle.append("/");
                itemTitle.append(billItem.getCharges());
            }
            billItemDesc.setText(itemTitle.toString());

            billItemValue.setText(df.format(billItem.getAmount().doubleValue() / 100));
        }

    }

}
