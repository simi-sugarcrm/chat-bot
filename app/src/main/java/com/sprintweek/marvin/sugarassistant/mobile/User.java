package com.sprintweek.marvin.sugarassistant.mobile;

public class User extends Entity{

    String name;
    String profileUrl;

    public User(String name, String profileUrl){
        this.name = name;
        this.profileUrl = profileUrl;
    }

    @Override
    public int getEntityId() {
        return Constants.HUMAN_ID;
    }

    @Override
    public String getName(){
        return this.name;
    }

    public String getProfileUrl(){
        return this.profileUrl;
    }
}
