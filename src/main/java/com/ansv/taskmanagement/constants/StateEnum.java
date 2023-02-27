package com.ansv.taskmanagement.constants;

import lombok.Getter;

import javax.swing.plaf.nimbus.State;

public enum StateEnum {

//    TODO("Chưa thực hiện"),
    NOT_DONE("Chưa hoàn thành"),
    DONE("Đã hoàn thành");

    final String typeValue;

    private StateEnum(final String typeValue) {
        this.typeValue = typeValue;
    }

    public String getName() {
        return name();
    }

    public String getValue() {
        return typeValue;
    }

    public Integer getOrdinal(String name) {
        return StateEnum.valueOf(name).ordinal();
    }

    @Override
    public String toString() {
        return name();
    }
}
