package com.esprit.animatemymeal.Entities;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Yahya on 22/01/2018.
 */

@IgnoreExtraProperties
public class User {

    private String name;
    private String address ;
    private String mobile ;
    private String photo  ;
    private String email;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }


    public User(String name, String address, String mobile, String photo, String email) {
        this.name = name;
        this.address = address;
        this.mobile = mobile;
        this.photo = photo;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}