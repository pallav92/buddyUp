package com.yatra.buddyup.model;

import java.util.List;

/**
 * Created by YATRAONLINE\v-pallav.srivastava on 16/3/18.
 */

public class User {

    private String userId;
    private String name;
    private String email;
    private String imgURL;
    private List<String> interests;

    public User(String userId, String name, String email, String imgURL, List<String> interests) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.imgURL = imgURL;
        this.interests = interests;
    }

    /*
    * Needed for the firebase to work
    * */
    public User(){

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getInterests() {
        return interests;
    }

    public void setInterests(List<String> interests) {
        this.interests = interests;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }
}
