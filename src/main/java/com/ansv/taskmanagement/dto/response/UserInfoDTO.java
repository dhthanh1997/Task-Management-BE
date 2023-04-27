package com.ansv.taskmanagement.dto.response;


import com.ansv.taskmanagement.util.TreeComponent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDTO   {
    private Long id;

    private String name;

    private String username;

    private String phone;

    private String email;

    private List<TeamDTO> teams;

    private List<PermissionDTO> permissions;

    private List<String> menu;

    private List<TreeComponent> children;

}
