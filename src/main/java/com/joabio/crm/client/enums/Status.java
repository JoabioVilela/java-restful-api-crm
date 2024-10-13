package com.joabio.crm.client.enums;

public enum Status {
    ACTIVE("Active"), INACTIVE("Inactive");

    private final String value;

    Status(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value; // required for @ValueOfEnum
    }
}