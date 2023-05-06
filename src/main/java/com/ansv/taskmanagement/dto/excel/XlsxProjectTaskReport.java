package com.ansv.taskmanagement.dto.excel;

import com.ansv.taskmanagement.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XlsxSheet(value = "Report")
public class XlsxProjectTaskReport {


    @XlsxSingleField(columnIndex = 0)
    private Long no;

    @XlsxSingleField(columnIndex = 1)
    private String sectionName;

    @XlsxCompositeField(from = 0, to = 11)
    private List<XlsxTask> tasks;


}
