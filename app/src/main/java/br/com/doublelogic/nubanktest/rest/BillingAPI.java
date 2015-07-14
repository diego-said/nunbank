package br.com.doublelogic.nubanktest.rest;

import android.content.Context;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Collections;

import br.com.doublelogic.nubanktest.rest.bean.Bill;
import br.com.doublelogic.nubanktest.rest.bean.BillItem;
import br.com.doublelogic.nubanktest.rest.bean.Summary;
import br.com.doublelogic.nubanktest.util.LogUtil;

/**
 * Created by diegoalvessaidsimao on 12/07/15.
 */
public class BillingAPI extends AbstractRestAPI {

    private static final String TAG = LogUtil.getTag(BillingAPI.class);

    public BillingAPI(Context context) {
        super(context);
    }

    @Override
    protected String getTag() {
        return TAG;
    }

    public ArrayList<Bill> bills() {
        ArrayList<Bill> billList = new ArrayList<>();

        StringBuilder url = new StringBuilder(backendAPI);
        String result = requestThroughHttpGet(url.toString());
        JsonArray object = parseHttpRequestResult(result);

        if(object != null) {
            for(int i=0; i < object.size(); i++) {
                JsonElement element = object.get(i);
                Bill bill =  gson.fromJson(element.getAsJsonObject().get("bill"), Bill.class);
                billList.add(bill);
            }
        }
        return billList;
    }

}
