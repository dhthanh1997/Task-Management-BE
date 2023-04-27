package com.ansv.taskmanagement.dto.response;


import lombok.*;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportDTO {
//    private Long id;

    private Long projectId;

    private Long taskId;

    private Byte state; 

    private String username;

    private Long teamId;

}
