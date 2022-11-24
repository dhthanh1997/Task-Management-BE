package com.ansv.taskmanagement.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleOfProjectDTO extends BaseDTO<String>   {
    private Long id;

    private String name;

    private String code;

    private String description;


}
