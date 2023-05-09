package com.ansv.taskmanagement.dto.excel;

import com.ansv.taskmanagement.annotation.*;
import com.ansv.taskmanagement.constants.StateEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class XlsxTask {
    @XlsxSingleField(columnIndex = 0, name = "no")
    private Long id;

    @XlsxSingleField(columnIndex = 1, name = "mainTask")
    private String name;

    @XlsxSingleField(columnIndex = 2, name = "markTime")
    private String markTime;

    @XlsxSingleField(columnIndex = 3, name = "startDate")
    private LocalDateTime startDate;

    @XlsxSingleField(columnIndex = 4, name = "startDate")
    private LocalDateTime endDate;

    @XlsxSingleField(columnIndex = 5, name = "estimateDate")
    private LocalDateTime estimateDate;

    @XlsxSingleField(columnIndex = 6, name = "realStartDate")
    private LocalDateTime realStartDate;

    @XlsxSingleField(columnIndex = 7, name = "realEndDate")
    private LocalDateTime realEndDate;



    private Byte state;

    @XlsxSingleField(columnIndex = 8, name = "state")
    private String stateName;

//    @Basic
//    private Byte state;
//
//    @Transient
//    @XlsxSingleField(columnIndex = 8, name = "state")
//    private String stateName;
//
    @PostLoad
    void fillTransient() {
        if (state > 0) {
            this.stateName = StateEnum.of(state).getName();
        }
    }
//
//    @PrePersist
//    void fillPersistent() {
//        if (stateName != null) {
//            this.state = (byte) StateEnum.ordinalOf(stateName);
//        }
//    }

    @XlsxSingleField(columnIndex = 9, name = "membersId")
    private List<Long> membersId;

    @XlsxSingleField(columnIndex = 10, name = "problem")
    private String problem;

    @XlsxSingleField(columnIndex = 11, name = "solution")
    private String solution;

    @XlsxSingleField(columnIndex = 12, name = "note")
    private String note;


}
