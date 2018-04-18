package com.example.hp.echoapp.constructors;

/**
 * Created by HP on 1/8/2018.
 */

public class AllMerchantConst {
    String _id,merchantname,merchantnumber;

    public AllMerchantConst(String _id, String merchantname, String merchantnumber) {
        this._id = _id;
        this.merchantname = merchantname;
        this.merchantnumber = merchantnumber;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getMerchantname() {
        return merchantname;
    }

    public void setMerchantname(String merchantname) {
        this.merchantname = merchantname;
    }

    public String getMerchantnumber() {
        return merchantnumber;
    }

    public void setMerchantnumber(String merchantnumber) {
        this.merchantnumber = merchantnumber;
    }
}
