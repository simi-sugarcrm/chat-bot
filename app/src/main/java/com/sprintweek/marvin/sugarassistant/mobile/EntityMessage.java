package com.sprintweek.marvin.sugarassistant.mobile;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EntityMessage extends BaseMessage{
    Entity entity;

    public EntityMessage(String message, long createdAt, Entity entity){
        super(message, createdAt);
        this.entity = entity;
    }

    public String getMessage(){
        return this.message;
    }

    public Entity getSender(){
        return this.entity;
    }

    public long getCreatedAt(){
        return this.createdAt;
    }

    public String getFormattedCreatedAt(){
        Date date = new Date(this.createdAt);
        Format format = new SimpleDateFormat("hh:mm aa");
        return format.format(date);
    }
}
