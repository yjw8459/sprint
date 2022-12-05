package com.yjw.sprint.tech.dto.enumerate;

public enum OrderStatus {
    ORDER, COMP, CANCEL;

    private String name;

    public String getName(){
        return name;
    }
}
