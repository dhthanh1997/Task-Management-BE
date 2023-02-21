package com.ansv.taskmanagement.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UploadFileDTO implements Serializable {
    public Long taskId;
    public Long projectId;
    public String name;
    public MultipartFile[] files;
    public String note;
}
