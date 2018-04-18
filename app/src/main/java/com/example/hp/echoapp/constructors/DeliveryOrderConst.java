package com.example.hp.echoapp.constructors;

/**
 * Created by HP on 2/14/2018.
 */

public class DeliveryOrderConst {
    private String productImage;
    private String productTitle;
    private String productWeight;
    private String productDelivery;
    private String product_id;
    private String merchant_name;
    private String buyer_name;
    private String user_id;
    private String dcenter_id;


    public DeliveryOrderConst(String productImage, String productTitle, String productWeight, String productDelivery,
                              String product_id,String merchant_name,String buyer_name,String user_id,String dcenter_id) {
        this.productImage = productImage;
        this.productTitle = productTitle;
        this.productWeight = productWeight;
        this.productDelivery = productDelivery;
        this.product_id = product_id;
        this.merchant_name = merchant_name;
        this.buyer_name = buyer_name;
        this.user_id = user_id;
        this.dcenter_id = dcenter_id;

    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getProductWeight() {
        return productWeight;
    }

    public void setProductWeight(String productWeight) {
        this.productWeight = productWeight;
    }

    public String getProductDelivery() {
        return productDelivery;
    }

    public void setProductDelivery(String productDelivery) {
        this.productDelivery = productDelivery;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getMerchant_name() {
        return merchant_name;
    }

    public void setMerchant_name(String merchant_name) {
        this.merchant_name = merchant_name;
    }

    public String getBuyer_name() {
        return buyer_name;
    }

    public void setBuyer_name(String buyer_name) {
        this.buyer_name = buyer_name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getDcenter_id() {
        return dcenter_id;
    }

    public void setDcenter_id(String dcenter_id) {
        this.dcenter_id = dcenter_id;
    }
}
