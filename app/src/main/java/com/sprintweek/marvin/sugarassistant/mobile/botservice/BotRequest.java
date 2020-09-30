package com.sprintweek.marvin.sugarassistant.mobile.botservice;

public class BotRequest {

    private String sender;

    private String message;

    public BotRequest() {
    }

    public BotRequest(String sender, String message) {
        this.sender = sender;
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
