package com.sprintweek.marvin.sugarassistant.mobile;

public class Bot extends Entity{

    String name;
    String profileUrl;

    public Bot(){
        this.name = Constants.AI_NAME;
    }

    @Override
    public int getEntityId() {
        return Constants.AI_ID;
    }

    @Override
    public String getName(){
        return this.name;
    }
}
