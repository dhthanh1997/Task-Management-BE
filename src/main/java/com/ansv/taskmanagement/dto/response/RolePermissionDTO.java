package com.ansv.taskmanagement.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RolePermissionDTO   {
    private Long id;

//    private String name;
//
//    private String code;
//
//    private String parentCode;

//    private String description;

    private Long roleId;

    private Long permissionId;
}
