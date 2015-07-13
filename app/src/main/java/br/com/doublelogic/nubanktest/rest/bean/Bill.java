package br.com.doublelogic.nubanktest.rest.bean;

import java.io.Serializable;

/**
 * Created by diegoalvessaidsimao on 12/07/15.
 */
public class Bill implements Serializable {

    private String id;
    private String state;
    private String barcode;
    private String linha_digitavel;

    private Summary summary;

    private BillItem[] line_items;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getLinha_digitavel() {
        return linha_digitavel;
    }

    public void setLinha_digitavel(String linha_digitavel) {
        this.linha_digitavel = linha_digitavel;
    }

    public Summary getSummary() {
        return summary;
    }

    public void setSummary(Summary summary) {
        this.summary = summary;
    }

    public BillItem[] getLine_items() {
        return line_items;
    }

    public void setLine_items(BillItem[] line_items) {
        this.line_items = line_items;
    }
}
