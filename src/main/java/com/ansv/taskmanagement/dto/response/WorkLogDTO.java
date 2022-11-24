package com.ansv.taskmanagement.dto.response;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkLogDTO extends BaseDTO<String>   {
    private Long id;

    private String name;

    private Long assignmentId;

    private String content;

    private LocalDateTime endDate;

}
