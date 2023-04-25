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
public class RolePermissionDTO  {

    private Long id;

    private Long roleId;

    private Long permissionId;

//    private List<PermissionDTO> children;


}
