package com.sprintweek.marvin.sugarassistant.mobile.Model;

public abstract class BaseMessage {
    String message;
    long createdAt;

    public BaseMessage(String message, long createdAt) {
        this.message = message;
        this.createdAt = createdAt;
    }
}
