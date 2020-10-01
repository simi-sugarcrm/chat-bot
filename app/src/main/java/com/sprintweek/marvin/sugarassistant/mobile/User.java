package com.sprintweek.marvin.sugarassistant.mobile;

public class User {
    boolean human;
    String nickname;

    public User(boolean human, String nickname) {
        this.human = human;
        this.nickname = nickname;
    }


    public String getNickname() {
        return this.nickname;
    }

    public int getUserId() {
        if (human) {
            return Constants.HUMAN_ID;
        } else {
            return Constants.AI_ID;
        }
    }
}
