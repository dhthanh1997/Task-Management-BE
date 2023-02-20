package com.ansv.taskmanagement.service.impl;

import com.ansv.taskmanagement.service.StorageService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class StorageServiceImpl implements StorageService {

    @Override
    public void storageFile(List<MultipartFile> multipartFile, Boolean isTask) {
        if(isTask) {

        }
    }
}
