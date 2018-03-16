package com.yatra.buddyup.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by YATRAONLINE\v-pallav.srivastava on 16/3/18.
 */
@IgnoreExtraProperties
public class ChatRoom {

    private String chatRoomId;
    private List<String> userIDList;
    private List<Message> messageList;

    public ChatRoom(){

    }

    public ChatRoom(String chatRoomId, List<String> userIDList, List<Message> messageList) {
        this.chatRoomId = chatRoomId;
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

    public String getChatRoomId() {
        return chatRoomId;
    }
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("chatRoomId", chatRoomId);
        result.put("userIdList", userIDList);
        result.put("messageList", messageList);
        return result;
    }



    public void setChatRoomId(String chatRoomId) {
        this.chatRoomId = chatRoomId;
    }
}
