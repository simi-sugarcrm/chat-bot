package com.sprintweek.marvin.sugarassistant.mobile;

public class UserMessage extends BaseMessage{
    User user;

    public UserMessage(String message, long createdAt, User user){
        super(message, createdAt);
        this.user = user;
    }
}
