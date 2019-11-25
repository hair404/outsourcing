package com.type;

public enum UserType {

    UNKNOWN(0),
    COMPANY(1),
    STUDIO(2),
    ADMIN(3);

    private int typeId;

    UserType(int typeId) {
        this.typeId = typeId;
    }

    public int getTypeId() {
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
