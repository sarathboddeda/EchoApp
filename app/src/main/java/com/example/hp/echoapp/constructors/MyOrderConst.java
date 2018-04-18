package com.example.hp.echoapp.constructors;

/**
 * Created by HP on 10/23/2017.
 */

public class MyOrderConst  {
    private String productImage;
    private String productTitle;
    private String productWeight;
    private String productDelivery;

    public MyOrderConst(String productImage, String productTitle, String productWeight, String productDelivery) {
        this.productImage = productImage;
        this.productTitle = productTitle;
        this.productWeight = productWeight;
        this.productDelivery = productDelivery;

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


}
