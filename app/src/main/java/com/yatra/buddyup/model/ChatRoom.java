package com.yatra.buddyup.model;

import java.util.List;

/**
 * Created by YATRAONLINE\v-pallav.srivastava on 16/3/18.
 */

public class ChatRoom {
    List<String> userIDList;
    List<Message> messageList;

    public ChatRoom(){

    }

    public ChatRoom(List<String> userIDList, List<Message> messageList) {
        this.userIDList = userIDList;
        this.messageList = messageList;
    }

    public List<String> getUserIDList() {
        return userIDList;
    }

    public void setUserIDList(List<String> userIDList) {
        this.userIDList = userIDList;
    }

    public List<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }
}
