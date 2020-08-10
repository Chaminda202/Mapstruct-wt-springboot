package com.mapstruct.explicit.enumeration;

public enum PhoneType {
    MOBILE("MOBILE", "Mobile"),
    LAND_LINE("LAND_LINE", "Land line"),
    OFFICE("OFFICE", "Office");

    private String value;
    private String description;
    private PhoneType(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}
