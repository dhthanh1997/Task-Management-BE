package com.ansv.taskmanagement.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StorageService {
   void storageFile(List<MultipartFile> multipartFile, Boolean isTask) ;
}
