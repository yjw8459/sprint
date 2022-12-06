package com.yjw.sprint.tech.dto.enumerate;

public enum DeliveryStatus {
    ORDER, WAIT, SHIP_PROCESS, SHIPPING, COMPLETE, CANCEL;

    public String name;

    public String getName() {
        return name;
    }
}
