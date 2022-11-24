package com.ansv.taskmanagement.dto.response;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class ResponseDataObject<T> {
    public String error;
    public String message;
    public Integer stattus;
    public T data;
}
