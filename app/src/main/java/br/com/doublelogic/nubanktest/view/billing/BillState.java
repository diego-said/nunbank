package br.com.doublelogic.nubanktest.view.billing;

import org.apache.commons.lang3.StringUtils;

import br.com.doublelogic.nubanktest.R;

/**
 * Created by diegoalvessaidsimao on 13/07/15.
 */
public enum BillState {

    OVERDUE(R.color.bill_green),
    CLOSED(R.color.bill_red),
    OPEN(R.color.bill_blue),
    FUTURE(R.color.bill_orange),
    UNKNOWN(android.R.color.black);

    private final int colorId;

    BillState(int colorId) {
        this.colorId = colorId;
    }

    public int getColorId() {
        return colorId;
    }

    public static BillState getBillState(String state) {
        for(BillState billState : values()) {
            if(StringUtils.equalsIgnoreCase(billState.name(), state)) {
                return billState;
            }
        }
        return UNKNOWN;
    }
}
