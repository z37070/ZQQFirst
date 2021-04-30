package com.example.teamwork.Main.MainCommunity;

public class CommunityBean {
    private String expressNumber;
    private String facility;
    private String address;
    private String state;
    private String objectId;
    public String getExpressNumber(){
        return expressNumber;
    }
    public void setExpressNumber(String expressNumber){
        this.expressNumber=expressNumber;
    }
    public String getFacility(){
        return facility;
    }
    public void setFacility(String facility){
        this.facility=facility;
    }
    public String getAddress(){
        return address;
    }
    public void setAddress(String address){
        this.address=address;
    }
    public String getState(){
        return state;
    }
    public void setState(String state){
        this.state=state;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
}
