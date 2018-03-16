package com.yatra.buddyup.model;

/**
 * Created by YATRAONLINE\v-pallav.srivastava on 16/3/18.
 */

public class Message {

    private String messageText;
    private User user;
    private long timestamp;
    private boolean isSender;


    public Message(String messageText, User user, long timestamp, boolean isSender) {
        this.messageText = messageText;
        this.user = user;
        this.timestamp = timestamp;
        this.isSender = isSender;
    }

    public Message(){
        //needed to fetch DataSnapShot from firebase.
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isSender() {
        return isSender;
    }

    public void setSender(boolean sender) {
        isSender = sender;
    }
}
