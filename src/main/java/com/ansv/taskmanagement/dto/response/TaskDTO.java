package com.ansv.taskmanagement.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO extends BaseDTO<String> {
    private Long id;

    private String name;

    private Long projectId;

    private BigDecimal totalCost;

    private BigDecimal revenue;

    private Float totalHour;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private LocalDateTime realStartDate;

    private LocalDateTime realEndDate;

    private String description;
    private String problem;
    private String solution;
    private String note;

    private String attachFile;

    private Long parentId;

    private Byte state;
    private Long numberOfSubTask;
    private Long sectionId;
    private Long tagId;

}
