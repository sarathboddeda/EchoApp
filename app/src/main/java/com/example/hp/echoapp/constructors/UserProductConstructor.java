package com.example.hp.echoapp.constructors;

/**
 * Created by HP on 10/17/2017.
 */

public class UserProductConstructor {
    private String producttitle,productbuy,productimage,productid,merchantid,merchantName,productdel;
    private int count,productQuantity;
    private double productprice;


    public UserProductConstructor(String merchantid,String productid ,String merchantName, String productimage, String producttitle, double productprice, String productbuy,int productQuantity,String productdel) {

        this.productid = productid;
        this.merchantid = merchantid;
        this.productimage = productimage;
        this.producttitle = producttitle;
        this.merchantName = merchantName;
        this.productprice = productprice;
        this.productbuy = productbuy;
        this.productQuantity = productQuantity;
        this.productdel = productdel;
    }

    public String  getProductimage() {
        return productimage;
    }

    public void setProductimage(String productimage) {
        this.productimage = productimage;
    }

    public String getProducttitle() {
        return producttitle;
    }

    public void setProducttitle(String producttitle) {
        this.producttitle = producttitle;
    }

    public double getProductprice() {
        return productprice;
    }

    public void setProductprice(double productprice) {
        this.productprice = productprice;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getProductbuy() {
        return productbuy;
    }

    public void setProductbuy(String productbuy) {
        this.productbuy = productbuy;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getMerchantid() {
        return merchantid;
    }

    public void setMerchantid(String merchantid) {
        this.merchantid = merchantid;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getProductdel() {
        return productdel;
    }

    public void setProductdel(String productdel) {
        this.productdel = productdel;
    }
}
