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
public class TaskDTO extends BaseDTO<String>   {
    private Long id;

    private String name;

    private Long projectId;

    private BigDecimal totalCost;

    private BigDecimal revenue;

    private Float totalHour;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private LocalDateTime readStartDate;

    private LocalDateTime readEndDate;

    private String description;

    private String attachFile;

    private Long parentId;

}
