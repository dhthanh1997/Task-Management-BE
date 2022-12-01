package com.ansv.taskmanagement.dto.response;

import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class ResponseDataObject<T> {
    public String error;
    public String message;
    public Integer status;
    public LocalDateTime timestamp;
    public T data;
    public Page<T> pagingData;

    public ResponseDataObject() {
        this.timestamp = LocalDateTime.now();
    }

    public void success() {
        this.message = "Successful";
        this.status = HttpStatus.OK.value();
    }

    public void initData(T data) {
        this.data = data;
        this.success();
    }
}
