package com.ansv.taskmanagement.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentDTO extends BaseDTO<String>  {

    private Long id;

    private Long memberId;

    private Long taskId;

    private Long activityId;

    private String description;

}
