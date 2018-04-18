package com.example.hp.echoapp.constructors;

/**
 * Created by HP on 1/26/2018.
 */

public class DeliverynewCons {
    String address,delid;

    public DeliverynewCons(String address,String delid) {
        this.address = address;
        this.delid=delid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDelid() {
        return delid;
    }

    public void setDelid(String delid) {
        this.delid = delid;
    }
}
