package com.ansv.taskmanagement.dto.response.report;


import com.ansv.taskmanagement.dto.response.BaseDTO;
import com.ansv.taskmanagement.dto.response.ProjectDTO;
import com.ansv.taskmanagement.dto.response.SectionDTO;
import com.ansv.taskmanagement.dto.response.TaskDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.repository.NoRepositoryBean;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectAndTaskReportDTO {
    private ProjectDTO projectDTO;
    private List<SectionAndTaskDTO> sections;

}
