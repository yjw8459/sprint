package com.yjw.sprint.tech.dto.enumerate;

public enum OrderStatus {
    ORDER, PROCESS, CANCEL, COMP;

    private String name;

    public String getName(){
        return name;
    }
}
