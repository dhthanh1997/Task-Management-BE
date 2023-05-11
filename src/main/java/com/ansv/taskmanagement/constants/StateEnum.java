package com.ansv.taskmanagement.constants;

import lombok.Getter;

import javax.swing.plaf.nimbus.State;
import java.util.stream.Stream;

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

    public static StateEnum of(int state) {
        return Stream.of(StateEnum.values()).filter(s -> state == s.ordinal()).findFirst().orElseThrow(IllegalArgumentException::new);
    }

    public static int ordinalOf(String name) {
        return Stream.of(StateEnum.values()).filter(s -> s.name().equals(name)).findFirst().orElseThrow(IllegalArgumentException::new).ordinal();
    }

}
