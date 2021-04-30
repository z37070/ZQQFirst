package com.example.teamwork;

public class StartBean {
    private static  String id;
    private static  String phone;
    public void setId(String id) {
        this.id = id;
    }

    public static String getId() {
        return id;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public static String getPhone() {
        return phone;
    }
}
