package com.example.hp.echoapp.constructors;

/**
 * Created by HP on 11/16/2017.
 */

public class MyCartContructor {
    private String myCartImage;
    private String myCartName,myCartWeight,myCartCost,myCartMerchant,myCartDelwith,mtCartDelwithout,myproid;

    public MyCartContructor(String myCartImage, String myCartName, String myCartWeight, String myCartCost,
                            String myCartMerchant,String proid) {
        this.myCartImage = myCartImage;
        this.myCartName = myCartName;
        this.myCartWeight = myCartWeight;
        this.myCartCost = myCartCost;
        this.myCartMerchant = myCartMerchant;
        this.myproid = proid;

    }

    public String getMyCartImage() {
        return myCartImage;
    }

    public void setMyCartImage(String myCartImage) {
        this.myCartImage = myCartImage;
    }

    public String getMyCartName() {
        return myCartName;
    }

    public void setMyCartName(String myCartName) {
        this.myCartName = myCartName;
    }

    public String getMyCartWeight() {
        return myCartWeight;
    }

    public void setMyCartWeight(String myCartWeight) {
        this.myCartWeight = myCartWeight;
    }

    public String getMyCartCost() {
        return myCartCost;
    }

    public void setMyCartCost(String myCartCost) {
        this.myCartCost = myCartCost;
    }

    public String getMyCartMerchant() {
        return myCartMerchant;
    }

    public void setMyCartMerchant(String myCartMerchant) {
        this.myCartMerchant = myCartMerchant;
    }

    public String getMyCartDelwith() {
        return myCartDelwith;
    }

    public void setMyCartDelwith(String myCartDelwith) {
        this.myCartDelwith = myCartDelwith;
    }

    public String getMtCartDelwithout() {
        return mtCartDelwithout;
    }

    public void setMtCartDelwithout(String mtCartDelwithout) {
        this.mtCartDelwithout = mtCartDelwithout;

    }

    public String getMyproid() {
        return myproid;
    }

    public void setMyproid(String myproid) {
        this.myproid = myproid;
    }
}
