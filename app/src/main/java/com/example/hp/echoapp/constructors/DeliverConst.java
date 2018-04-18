package com.example.hp.echoapp.constructors;

/**
 * Created by HP on 12/8/2017.
 */

public class DeliverConst {
    String _id,fullname,delemail,password, address,state,zipcode,country,city, mobile;



    public DeliverConst(String fullname, String delemail,
                        String address, String state, String zipcode, String country, String city, String mobile) {
        this._id = _id;
        this.fullname = fullname;
        this.delemail = delemail;
        this.password = password;
        this.address = address;
        this.state = state;
        this.zipcode = zipcode;
        this.country = country;
        this.city = city;
        this.mobile = mobile;

    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getDelemail() {
        return delemail;
    }

    public void setDelemail(String delemail) {
        this.delemail = delemail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


}
