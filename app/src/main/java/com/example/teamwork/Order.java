package com.example.teamwork;

import cn.bmob.v3.BmobObject;

public class Order extends BmobObject {
    private String expressNumber;//订单号(发布人ID+发布时间)
    private String facility;//取件处
    private String pickupNumber;//取件码
    private String receiveAddress;//快递送达地点
    private String receiveName;//收货人姓名
    private String receivePhone;//收货人手机号
    private String substituteID;//代取人ID
    private String substitutePhone;//代取人手机号
    private String state;//状态
    private String receiveID;
    public String getExpressNumber() {
        return expressNumber;
    }

    public String getFacility() {
        return facility;
    }

    public String getPickupNumber() {
        return pickupNumber;
    }

    public String getReceiveAddress() {
        return receiveAddress;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public String getReceivePhone() {
        return receivePhone;
    }

    public String getState() {
        return state;
    }

    public String getSubstituteID() {
        return substituteID;
    }

    public String getSubstitutePhone() {
        return substitutePhone;
    }

    public void setExpressNumber(String expressNumber) {
        this.expressNumber = expressNumber;
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }

    public void setPickupNumber(String pickupNumber) {
        this.pickupNumber = pickupNumber;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public void setReceivePhone(String receivePhone) {
        this.receivePhone = receivePhone;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setSubstituteID(String substituteID) {
        this.substituteID = substituteID;
    }

    public void setSubstitutePhone(String substitutePhone) {
        this.substitutePhone = substitutePhone;
    }

    public void setReceiveID(String receiveID) {
        this.receiveID = receiveID;
    }

    public String getReceiveID() {
        return receiveID;
    }
}
