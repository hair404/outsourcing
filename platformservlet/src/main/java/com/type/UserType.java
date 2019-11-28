package com.type;

public enum UserType {

    UNKNOWN(-1),
    COMPANY(0),
    STUDIO(1),
    ADMIN(2),
    EXPERT(3);

    private int typeId;

    UserType(int typeId) {
        this.typeId = typeId;
    }

    public int getId() {
        return typeId;
    }

    public static UserType fromId(int id) {
        for (UserType type : UserType.values()) {
            if (type.typeId == id) {
                return type;
            }
        }
        return UserType.UNKNOWN;
    }
}
