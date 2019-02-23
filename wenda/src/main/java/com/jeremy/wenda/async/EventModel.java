package com.jeremy.wenda.async;

import java.util.HashMap;
import java.util.Map;

public class EventModel {
    //事件名称
    private EventType eventType;
    //发起事件的Id，例如评论人的Id
    private int actorId;
    //实体描述
    private int entityType;
    private int entityId;
    //作者，事件发起者
    private int entityOwnerId;
    private Map<String,String> exts = new HashMap<String, String>();
    public EventModel(){

    }

    public EventModel setExt(String key,String value) {
        exts.put(key,value);
        return this;
    }
    public EventModel(EventType eventType){
        this.eventType = eventType;
    }
    public String getExt(String key) {
        return exts.get(key);
    }

    public EventType getEventType() {
        return eventType;
    }

    public EventModel setEventType(EventType eventType) {
        this.eventType = eventType;
        return this;
    }

    public int getActorId() {
        return actorId;
    }

    public EventModel setActorId(int actorId) {
        this.actorId = actorId;
        return this;
    }

    public int getEntityType() {
        return entityType;
    }

    public EventModel setEntityType(int entityType) {
        this.entityType = entityType;
        return this;
    }

    public int getEntityId() {
        return entityId;
    }

    public EventModel setEntityId(int entityId) {
        this.entityId = entityId;
        return this;
    }

    public int getEntityOwnerId() {
        return entityOwnerId;
    }

    public EventModel setEntityOwnerId(int entityOwnerId) {
        this.entityOwnerId = entityOwnerId;
        return this;
    }
    public Map<String, String> getExts() {
        return exts;
    }

    public EventModel setExts(Map<String, String> exts) {
        this.exts = exts;
        return this;
    }
}
