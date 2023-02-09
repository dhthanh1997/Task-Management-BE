package com.ansv.taskmanagement.constants;

public enum TypePermissionEnum {
    MENU(0),
    ACTION(1);

    final Integer typeValue;

    private TypePermissionEnum(Integer typeValue) {
        this.typeValue = typeValue;
    }

    public String getName() {
        return this.name();
    }

    public Integer getValue() {
        return typeValue;
    }

    @Override
    public String toString() {
        return name();
    }

}
