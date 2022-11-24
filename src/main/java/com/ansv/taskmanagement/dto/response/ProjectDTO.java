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
public class ProjectDTO extends BaseDTO<String>   {
    private Long id;

    private String name;

    private Long customerId;

    private BigDecimal totalCost;

    private BigDecimal revenue;

    private Float totalHour;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private LocalDateTime readStartDate;

    private LocalDateTime readEndDate;

    private Long parentId;

    private String attachFile;

}
