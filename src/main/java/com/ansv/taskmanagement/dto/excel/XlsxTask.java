package com.ansv.taskmanagement.dto.excel;

import com.ansv.taskmanagement.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class XlsxTask {
    @XlsxSingleField(columnIndex = 0)
    private Long no;

    @XlsxSingleField(columnIndex = 1)
    private String mainTask;

    @XlsxSingleField(columnIndex = 2)
    private String markTime;

    @XlsxSingleField(columnIndex = 3)
    private LocalDateTime startDate;

    @XlsxSingleField(columnIndex = 4)
    private LocalDateTime estimateDate;

    @XlsxSingleField(columnIndex = 5)
    private LocalDateTime realStartDate;

    @XlsxSingleField(columnIndex = 6)
    private LocalDateTime realEndDate;

    @XlsxSingleField(columnIndex = 7)
    private Byte state;

    @XlsxSingleField(columnIndex = 8)
    private List<Long> membersId;

    @XlsxSingleField(columnIndex = 9)
    private String problem;

    @XlsxSingleField(columnIndex = 10)
    private String solution;

    @XlsxSingleField(columnIndex = 11)
    private String note;
}
