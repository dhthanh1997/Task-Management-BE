package com.ansv.taskmanagement.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TreeDTO {
    private Long id;

    private String name;

    private String code;

    private String parentCode;

    private String description;

    private List<TreeDTO> children;
}
