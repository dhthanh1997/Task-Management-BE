package com.ansv.taskmanagement.dto.response;


import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TagDTO extends BaseDTO<String> {
    private Long id;

    private String name;

    private String slug;

    private String icon;

    private String color;

}
