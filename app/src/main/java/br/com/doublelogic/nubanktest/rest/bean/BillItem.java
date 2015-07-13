package br.com.doublelogic.nubanktest.rest.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by diegoalvessaidsimao on 12/07/15.
 */
public class BillItem implements Serializable {

    private Date post_date;
    private Integer amount;
    private String title;
    private Integer index;
    private Integer charges;
    private String href;

    public Date getPost_date() {
        return post_date;
    }

    public void setPost_date(Date post_date) {
        this.post_date = post_date;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getCharges() {
        return charges;
    }

    public void setCharges(Integer charges) {
        this.charges = charges;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
