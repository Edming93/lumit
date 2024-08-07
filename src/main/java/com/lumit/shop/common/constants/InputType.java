package com.lumit.shop.common.constants;

public enum InputType {
    RADIO("radio"), TEXT("text"), EMAIL("email"), PASSWORD("password"), HIDDEN("hidden");

    private final String label;

    InputType(String label) {
        this.label = label;
    }

    public String label() {
        return label;
    }
}
