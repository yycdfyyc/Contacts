package com.yuyunchao.asus.contacts.entity;

/**
 * Created by asus on 2016/8/26.
 */
public class PhoneType {
    private String phoneType;
    public PhoneType(String phonetype){
        this.phoneType =phonetype;
    }
    public PhoneType(){}
    public String getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(String phoneType) {
        this.phoneType = phoneType;
    }
}
