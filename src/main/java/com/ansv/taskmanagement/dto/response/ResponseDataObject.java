package com.ansv.taskmanagement.dto.response;

import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class ResponseDataObject<T> {
    public String error;
    public String message;
    public HttpStatus stattus;
    public T data;
    public Page<T> pagingData;
}
