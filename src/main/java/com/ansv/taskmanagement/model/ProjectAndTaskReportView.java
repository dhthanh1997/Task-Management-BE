package com.ansv.taskmanagement.model;


import com.ansv.taskmanagement.dto.response.report.SectionAndTaskDTO;
import lombok.*;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Immutable
public class ProjectAndTaskReportView implements Serializable {

    @Id
    private Long id;
    private Integer no;
    private String name;
    private String sectionName;
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
    private Long parentId;
    private Byte state;
    private String stateNum;
    private Long numberOfSubTask;
    private Long sectionId;
    private Long tagId;


}

