package com.sprintweek.marvin.sugarassistant.mobile.botservice;

public class BotResponseButton {

    private String title;

    private String payload;

    public BotResponseButton() {
    }

    public BotResponseButton(String title, String payload) {
        this.title = title;
        this.payload = payload;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}
