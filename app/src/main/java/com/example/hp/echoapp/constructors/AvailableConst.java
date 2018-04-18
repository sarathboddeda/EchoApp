package com.example.hp.echoapp.constructors;

/**
 * Created by HP on 11/15/2017.
 */

public class AvailableConst {
    private String availableImage;

    private String availableTitle,availablekilos,availablecost,availabledelvery,pro_status,buyer_name,
            availableeta,availablepro,availableorder,availablebal,availabledesc,merchant_id,product_id,balanceproducts;

    public AvailableConst(String availableImage, String availableTitle, String availablekilos, String availablecost,
                           String availableeta, String availablepro, String availableorder,
                          String availablebal,String availabledesc,String merchant_id,String product_id,String balanceproducts,String pro_status,
                          String buyer_name) {
        this.availableImage = availableImage;
        this.availableTitle = availableTitle;
        this.availablekilos = availablekilos;
        this.availablecost = availablecost;
        this.availabledelvery = availabledelvery;
        this.availableeta = availableeta;
        this.availablepro = availablepro;
        this.availableorder = availableorder;
        this.availablebal = availablebal;
        this.availabledesc=availabledesc;
        this.merchant_id=merchant_id;
        this.product_id=product_id;
        this.balanceproducts=balanceproducts;
        this.pro_status=pro_status;
        this.buyer_name=buyer_name;
    }

    public String getAvailableImage() {
        return availableImage;
    }

    public void setAvailableImage(String availableImage) {
        this.availableImage = availableImage;
    }

    public String getAvailableTitle() {
        return availableTitle;
    }

    public void setAvailableTitle(String availableTitle) {
        this.availableTitle = availableTitle;
    }

    public String getAvailablekilos() {
        return availablekilos;
    }

    public void setAvailablekilos(String availablekilos) {
        this.availablekilos = availablekilos;
    }

    public String getAvailablecost() {
        return availablecost;
    }

    public void setAvailablecost(String availablecost) {
        this.availablecost = availablecost;
    }

    public String getAvailabledelvery() {
        return availabledelvery;
    }

    public void setAvailabledelvery(String availabledelvery) {
        this.availabledelvery = availabledelvery;
    }

    public String getAvailableeta() {
        return availableeta;
    }

    public void setAvailableeta(String availableeta) {
        this.availableeta = availableeta;
    }

    public String getAvailablepro() {
        return availablepro;
    }

    public void setAvailablepro(String availablepro) {
        this.availablepro = availablepro;
    }

    public String getAvailableorder() {
        return availableorder;
    }

    public void setAvailableorder(String availableorder) {
        this.availableorder = availableorder;
    }

    public String getAvailablebal() {
        return availablebal;
    }

    public void setAvailablebal(String availablebal) {
        this.availablebal = availablebal;
    }

    public String getAvailabledesc() {
        return availabledesc;
    }

    public void setAvailabledesc(String availabledesc) {
        this.availabledesc = availabledesc;
    }

    public String getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getBalanceproducts() {
        return balanceproducts;
    }

    public void setBalanceproducts(String balanceproducts) {
        this.balanceproducts = balanceproducts;
    }

    public String getPro_status() {
        return pro_status;
    }

    public void setPro_status(String pro_status) {
        this.pro_status = pro_status;
    }

    public String getBuyer_name() {
        return buyer_name;
    }

    public void setBuyer_name(String buyer_name) {
        this.buyer_name = buyer_name;
    }
}
