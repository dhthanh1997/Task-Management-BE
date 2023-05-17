package com.ansv.taskmanagement.dto.response.report;

import com.ansv.taskmanagement.dto.response.TaskDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.repository.NoRepositoryBean;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SectionAndTaskDTO {

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
    private List<SectionAndTaskDTO> children;
}
