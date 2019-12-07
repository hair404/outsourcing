package com.type;

import lombok.Getter;

public enum ActionType {
    UNKNOWN(-1),
    JUMP_PROJECT(0),
    // solr_id 项目的solr_id
    JUMP_USER(1),
    // id 用户id
    CENTER(2);

    @Getter
    private int id;

    ActionType(int id) {
        this.id = id;
    }

    public static ActionType fromId(int id) {
        for (ActionType type : ActionType.values()) {
            if (type.id == id) {
                return type;
            }
        }
        return ActionType.UNKNOWN;
    }

    public static String generateJumpProjectParams(String solrId) {
        return "{\"solrId\":\"{0}\"}".replace("{0}", solrId);
    }

}
