package com.ansv.taskmanagement.service;

import com.ansv.taskmanagement.dto.request.UploadFileDTO;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


public interface StorageService {

   Boolean storageFile(MultipartFile file, UploadFileDTO item) throws IOException;

   Boolean deleteFile(UploadFileDTO item) throws IOException;

   List<Resource> loadFile(UploadFileDTO item) throws IOException;

}
