package com.vashchenko.education.t1.task_1.model.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.HashMap;
import java.util.Map;

public enum OrderStatus {
    New, Processing, Completed;
    private static final Map<String, OrderStatus > statusMap = new HashMap<>(3);

    static {
        statusMap.put("new", New);
        statusMap.put("processing", Processing);
        statusMap.put("completed", Completed);
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static OrderStatus forValue(String value) {
        return statusMap.get(value.toLowerCase());
    }

    @JsonValue
    public String toValue() {
        for (Map.Entry<String, OrderStatus > entry : statusMap.entrySet()) {
            if (entry.getValue() == this)
                return entry.getKey();
        }
        return null;
    }
}
