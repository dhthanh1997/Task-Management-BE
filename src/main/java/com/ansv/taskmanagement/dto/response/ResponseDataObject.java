package com.ansv.taskmanagement.dto.response;

import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class ResponseDataObject<T> {
    public String error;
    public String message;
    public HttpStatus status;
    public LocalDateTime timestamp;
    public T data;
    public Page<T> pagingData;
}
