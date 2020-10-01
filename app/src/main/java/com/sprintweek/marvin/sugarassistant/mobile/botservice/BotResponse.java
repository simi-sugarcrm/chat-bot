package com.sprintweek.marvin.sugarassistant.mobile.botservice;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BotResponse {

    @SerializedName("recipient_id")
    private String recipientId;

    private String text;

    private List<BotResponseButton> buttons;

    public BotResponse() {
    }

    public BotResponse(String recipientId, String text, List<BotResponseButton> buttons) {
        this.recipientId = recipientId;
        this.text = text;
        this.buttons = buttons;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<BotResponseButton> getButtons() {
        return buttons;
    }

    public void setButtons(List<BotResponseButton> buttons) {
        this.buttons = buttons;
    }
}
