package com.ansv.taskmanagement.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO extends BaseDTO<String> {
    private Long id;

    private String name;

    private Long taskId;

    private Long activityId;

    private String description;

    private String content;

    private String attachFile;

    private Long parentId;



}
