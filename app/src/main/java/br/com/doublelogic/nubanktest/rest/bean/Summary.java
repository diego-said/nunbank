package br.com.doublelogic.nubanktest.rest.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by diegoalvessaidsimao on 12/07/15.
 */
public class Summary implements Serializable {

    private Date due_date;
    private Date open_date;
    private Date close_date;
    private Integer past_balance;
    private Integer balance;
    private Integer total_balance;
    private Integer interest;
    private Integer total_cumulative;
    private Integer paid;
    private Integer minimum_payment;

    public Date getDue_date() {
        return due_date;
    }

    public void setDue_date(Date due_date) {
        this.due_date = due_date;
    }

    public Date getOpen_date() {
        return open_date;
    }

    public void setOpen_date(Date open_date) {
        this.open_date = open_date;
    }

    public Date getClose_date() {
        return close_date;
    }

    public void setClose_date(Date close_date) {
        this.close_date = close_date;
    }

    public Integer getPast_balance() {
        return past_balance;
    }

    public void setPast_balance(Integer past_balance) {
        this.past_balance = past_balance;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public Integer getTotal_balance() {
        return total_balance;
    }

    public void setTotal_balance(Integer total_balance) {
        this.total_balance = total_balance;
    }

    public Integer getInterest() {
        return interest;
    }

    public void setInterest(Integer interest) {
        this.interest = interest;
    }

    public Integer getTotal_cumulative() {
        return total_cumulative;
    }

    public void setTotal_cumulative(Integer total_cumulative) {
        this.total_cumulative = total_cumulative;
    }

    public Integer getPaid() {
        return paid;
    }

    public void setPaid(Integer paid) {
        this.paid = paid;
    }

    public Integer getMinimum_payment() {
        return minimum_payment;
    }

    public void setMinimum_payment(Integer minimum_payment) {
        this.minimum_payment = minimum_payment;
    }
}
