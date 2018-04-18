package com.example.hp.echoapp.constructors;

/**
 * Created by HP on 11/17/2017.
 */

public class OrderedConst {
    private String orderedImage;
    private String OrderedTitle,Orderedkilos,Orderedcost,buyer_name;

    public OrderedConst(String orderedImage, String orderedTitle, String orderedkilos, String orderedcost,String buyer_name) {
        this.orderedImage = orderedImage;
        this.OrderedTitle = orderedTitle;
        Orderedkilos = orderedkilos;
        Orderedcost = orderedcost;
        this.buyer_name = buyer_name;
    }

    public String getOrderedImage() {
        return orderedImage;
    }

    public void setOrderedImage(String orderedImage) {
        this.orderedImage = orderedImage;
    }

    public String getOrderedTitle() {
        return OrderedTitle;
    }

    public void setOrderedTitle(String orderedTitle) {
        OrderedTitle = orderedTitle;
    }

    public String getOrderedkilos() {
        return Orderedkilos;
    }

    public void setOrderedkilos(String orderedkilos) {
        Orderedkilos = orderedkilos;
    }

    public String getOrderedcost() {
        return Orderedcost;
    }

    public void setOrderedcost(String orderedcost) {
        Orderedcost = orderedcost;
    }

    public String getBuyer_name() {
        return buyer_name;
    }

    public void setBuyer_name(String buyer_name) {
        this.buyer_name = buyer_name;
    }
}
