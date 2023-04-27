package com.ansv.taskmanagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MenuDTO implements Serializable {
    private Long id;

    private String name;

    private String code;

    private String parentCode;

    private String description;

    private List<MenuDTO> children;
}
