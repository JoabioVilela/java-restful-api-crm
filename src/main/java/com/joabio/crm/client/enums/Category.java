package com.joabio.crm.client.enums;

public enum Category {
    BRONZE("Bronze"), PRATA("Prata"), OURO("Ouro");

    private final String value;

    Category(String value) {
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