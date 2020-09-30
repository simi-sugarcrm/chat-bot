package com.sprintweek.marvin.sugarassistant.mobile;

public class User {
    boolean human;
    String nickname;
    String profileUrl;

    public User(boolean human, String nickname, String profileUrl) {
        this.human = human;
        this.nickname = nickname;
        this.profileUrl = profileUrl;
    }


    public String getNickname(){
        return this.nickname;
    }

    public String getProfileUrl(){
        return this.profileUrl;
    }

    public int getUserId(){
        if (human){
            return Constants.HUMAN_ID;
        }
        else{
            return Constants.AI_ID;
        }
    }
}
