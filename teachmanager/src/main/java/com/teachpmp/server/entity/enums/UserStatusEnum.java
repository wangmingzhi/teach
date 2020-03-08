package com.teachpmp.server.entity.enums;


import java.util.HashMap;
import java.util.Map;

public enum UserStatusEnum {

    Enable("1", "启用"),
    Disable("0", "禁用");

    String code;
    String name;

    UserStatusEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    private static final Map<String, UserStatusEnum> keyMap = new HashMap<>();

    static {
        for (UserStatusEnum item : UserStatusEnum.values()) {
            keyMap.put(item.getCode(), item);
        }
    }

    public static UserStatusEnum fromCode(String code) {
        return keyMap.get(code);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
