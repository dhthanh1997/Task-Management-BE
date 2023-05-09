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


    @XlsxSingleField(columnIndex = 0, name = "no")
    private Long no;

    @XlsxSingleField(columnIndex = 1, name = "sectionName")
    private String sectionName;

    @XlsxCompositeField(from = 0, to = 12)
    private List<XlsxTask> tasks;


}
