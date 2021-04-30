package com.example.teamwork;

import cn.bmob.v3.BmobObject;

public class Userinfo extends BmobObject {
    private String id;
    private String password;
    private String phone;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
