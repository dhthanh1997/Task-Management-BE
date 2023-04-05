package com.ansv.taskmanagement.dto.response;


import com.ansv.taskmanagement.model.Team;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO extends BaseDTO<String>   {
    private Long id;

    private String name;

    private String username;

    private String phone;

    private String email;

    private Long roleId;

    private Set<TeamDTO> teams;

}
