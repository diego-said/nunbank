package br.com.doublelogic.nubanktest.view.billing;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import br.com.doublelogic.nubanktest.R;
import br.com.doublelogic.nubanktest.rest.bean.Bill;
import br.com.doublelogic.nubanktest.util.FormatUtil;

public class BillInterfaceFragment extends Fragment {

    private Bill bill;

    private RelativeLayout billHeaderHighlight;
    private TextView billHeaderValue;
    private TextView billHeaderDueDate;
    private TextView billHeaderDesc;

    private RelativeLayout billPaymentReceived;
    private TextView billPaymentReceivedDesc;
    private TextView billPaymentReceivedValue;

    private RelativeLayout billStatement;
    private TextView billStatementMonth;
    private TextView billStatementMonthValue;
    private TextView billNotPaid;
    private TextView billNotPaidValue;
    private TextView billInterest;
    private TextView billInterestValue;

    private Button billButtonPay;

    private RelativeLayout billItemsHeader;
    private TextView billItemDateRange;
    private TextView billItemValues;

    private ListView listBillItems;
    private BillItemsListAdapter listAdapter;

    private SimpleDateFormat sdf;
    private SimpleDateFormat sdfMonth;
    private DecimalFormat df;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bill_interface, container, false);

        billHeaderHighlight = (RelativeLayout) view.findViewById(R.id.billHeaderHighlight);
        billHeaderValue = (TextView) view.findViewById(R.id.billHeaderValue);
        billHeaderDueDate = (TextView) view.findViewById(R.id.billHeaderDueDate);
        billHeaderDesc = (TextView) view.findViewById(R.id.billHeaderDesc);

        billPaymentReceived = (RelativeLayout) view.findViewById(R.id.billPaymentReceived);
        billPaymentReceivedDesc = (TextView) view.findViewById(R.id.billPaymentReceivedDesc);
        billPaymentReceivedValue = (TextView) view.findViewById(R.id.billPaymentReceivedValue);

        billStatement = (RelativeLayout) view.findViewById(R.id.billStatement);
        billStatementMonth = (TextView) view.findViewById(R.id.billStatementMonth);
        billStatementMonthValue = (TextView) view.findViewById(R.id.billStatementMonthValue);
        billNotPaid = (TextView) view.findViewById(R.id.billNotPaid);
        billNotPaidValue = (TextView) view.findViewById(R.id.billNotPaidValue);
        billInterest = (TextView) view.findViewById(R.id.billInterest);
        billInterestValue = (TextView) view.findViewById(R.id.billInterestValue);

        billButtonPay = (Button) view.findViewById(R.id.billButtonPay);

        billItemsHeader = (RelativeLayout) view.findViewById(R.id.billItemsHeader);
        billItemDateRange = (TextView) view.findViewById(R.id.billItemDateRange);
        billItemValues = (TextView) view.findViewById(R.id.billItemValues);

        listBillItems = (ListView) view.findViewById(R.id.listBillItems);
        listAdapter = new BillItemsListAdapter(getActivity().getApplicationContext());
        listBillItems.setAdapter(listAdapter);

        sdf = new SimpleDateFormat("dd MMM");
        sdfMonth = new SimpleDateFormat("dd 'DE' MMMMM");
        df = FormatUtil.getDecimalFormat(getActivity().getApplicationContext(), "###,###,###.00");

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        final BillState billState = BillState.getBillState(bill.getState());

        billHeaderValue.setText(df.format(bill.getSummary().getTotal_balance() / 100));
        billHeaderDueDate.setText(sdf.format(bill.getSummary().getDue_date()));
        billHeaderDesc.setVisibility(View.GONE);

        String dtOpen = sdf.format(bill.getSummary().getOpen_date());
        String dtClose = sdf.format(bill.getSummary().getClose_date());
        String billDateRange = String.format(getString(R.string.bill_date_range), dtOpen, dtClose);
        billItemDateRange.setText(billDateRange);

        billPaymentReceived.setVisibility(View.GONE);
        billStatement.setVisibility(View.GONE);
        billButtonPay.setVisibility(View.GONE);

        billHeaderHighlight.setBackgroundResource(billState.getColorId());

        switch (billState) {
            case FUTURE:
                billPaymentReceived.setVisibility(View.GONE);
                billStatement.setVisibility(View.GONE);
                billButtonPay.setVisibility(View.GONE);

                billHeaderDesc.setText(R.string.bill_future_desc);
                billHeaderDesc.setVisibility(View.VISIBLE);
                break;

            case OPEN:
                billPaymentReceived.setVisibility(View.GONE);
                billStatement.setVisibility(View.GONE);
                billButtonPay.setVisibility(View.VISIBLE);
                billButtonPay.setBackgroundResource(R.drawable.btn_default_blue_selector);
                //TODO mudar a cor do texto do botão



                billHeaderDesc.setText(String.format(getString(R.string.bill_open_desc), bill.getSummary().getClose_date()));
                billHeaderDesc.setVisibility(View.VISIBLE);
                break;

            case CLOSED:
                billPaymentReceived.setVisibility(View.GONE);
                billStatement.setVisibility(View.VISIBLE);
                billButtonPay.setVisibility(View.VISIBLE);

                if(bill.getSummary().getTotal_cumulative() > 0) {

                }


                if(bill.getSummary().getInterest() > 0) {
                    billInterest.setVisibility(View.VISIBLE);
                    billInterestValue.setVisibility(View.VISIBLE);

                    billInterestValue.setText(df.format(bill.getSummary().getInterest() / 100));
                } else {
                    billInterest.setVisibility(View.GONE);
                    billInterestValue.setVisibility(View.GONE);
                }
                break;

            case OVERDUE:
                billPaymentReceived.setVisibility(View.VISIBLE);
                billStatement.setVisibility(View.GONE);
                billButtonPay.setVisibility(View.GONE);

                billPaymentReceivedValue.setText(df.format(bill.getSummary().getPaid()));
                break;
        }


    }

    public void setBill(Bill bill) {
        this.bill = bill;
        if(listAdapter != null) {
            listAdapter.setBillItemList(Arrays.asList(bill.getLine_items()));
        }
    }
}
