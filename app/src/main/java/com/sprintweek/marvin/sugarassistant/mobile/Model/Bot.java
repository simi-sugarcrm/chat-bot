package com.sprintweek.marvin.sugarassistant.mobile.Model;

import com.sprintweek.marvin.sugarassistant.mobile.Constants;

public class Bot extends Entity {

    String name;

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
