package com.yjw.sprint.tech.dto.enumerate;

public enum OrderStatus {
    NONE, ORDER, PROCESS, REJECT, CANCEL, COMPLETE;

    private String name;

    public String getName(){
        return name;
    }
}
