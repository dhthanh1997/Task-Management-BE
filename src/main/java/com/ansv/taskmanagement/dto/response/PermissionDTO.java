package com.ansv.taskmanagement.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PermissionDTO extends BaseDTO<String>   {
    public Long id;

    public String name;

    public String code;

    public String description;

    public String parentCode;

    public Byte type;

}
