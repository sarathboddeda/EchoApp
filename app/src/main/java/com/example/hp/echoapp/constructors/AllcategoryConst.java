package com.example.hp.echoapp.constructors;

/**
 * Created by HP on 1/10/2018.
 */

public class AllcategoryConst {
    String catid,catname;

    public AllcategoryConst(String catid, String catname) {
        this.catid = catid;
        this.catname = catname;
    }

    public String getCatid() {
        return catid;
    }

    public void setCatid(String catid) {
        this.catid = catid;
    }

    public String getCatname() {
        return catname;
    }

    public void setCatname(String catname) {
        this.catname = catname;
    }
}
