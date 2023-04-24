package com.ansv.taskmanagement.dto.response;


import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PermissionDTO extends BaseDTO<String>   {
    public Long id;

    public String name;

    public String code;

    public String description;

    public String parentCode;

    public Byte type;

}
