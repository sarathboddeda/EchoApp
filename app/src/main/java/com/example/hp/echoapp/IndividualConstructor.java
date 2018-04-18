package com.example.hp.echoapp;

/**
 * Created by HP on 11/17/2017.
 */

public class IndividualConstructor {
    private String individualTitle,individualDate,individualQty;

    public IndividualConstructor(String individualTitle, String individualDate, String individualQty) {
        this.individualTitle = individualTitle;
        this.individualDate = individualDate;
        this.individualQty = individualQty;
    }

    public String getIndividualTitle() {
        return individualTitle;
    }

    public void setIndividualTitle(String individualTitle) {
        this.individualTitle = individualTitle;
    }

    public String getIndividualDate() {
        return individualDate;
    }

    public void setIndividualDate(String individualDate) {
        this.individualDate = individualDate;
    }

    public String getIndividualQty() {
        return individualQty;
    }

    public void setIndividualQty(String individualQty) {
        this.individualQty = individualQty;
    }
}
