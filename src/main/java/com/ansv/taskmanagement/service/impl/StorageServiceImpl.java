package com.ansv.taskmanagement.service.impl;

import com.ansv.taskmanagement.dto.request.UploadFileDTO;
import com.ansv.taskmanagement.service.StorageService;
import com.ansv.taskmanagement.util.DataUtils;
import com.ansv.taskmanagement.util.FileUtils;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class StorageServiceImpl implements StorageService {


    @Value("${app.path.task:#{null}}")
    private String pathTaskStorage;

    @Value("${app.path.project:#{null}}")
    private String pathProjectStorage;


    @Override
    public Boolean storageFile(MultipartFile file, UploadFileDTO item) throws IOException {
        if (!DataUtils.isNullOrEmpty(item.taskId)) {
            if (!DataUtils.isNullOrEmpty(pathTaskStorage)) {
                String pathTask = pathTaskStorage + "/" + item.taskId.toString() + "/";
                Files.copy(file.getInputStream(), Paths.get(pathTask));
                return true;
            }
        }
        if (!DataUtils.isNullOrEmpty(item.projectId)) {
            if (!DataUtils.isNullOrEmpty(pathProjectStorage)) {
                String pathProject = pathProjectStorage + "/" + item.projectId.toString() + "/";
                Files.copy(file.getInputStream(), Paths.get(pathProject));
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean deleteFile(UploadFileDTO item) throws IOException {
        if (!DataUtils.isNullOrEmpty(item.taskId)) {
            if (!DataUtils.isNullOrEmpty(pathTaskStorage)) {
                String pathTask = pathTaskStorage + "/" + item.taskId.toString() + "/";
                for (MultipartFile multipartFile : item.getFiles()) {
                    pathTask += multipartFile.getOriginalFilename();
                    Files.delete(Paths.get(pathTask));
                }
            }
            return true;
        }
        if (!DataUtils.isNullOrEmpty(item.projectId)) {
            if (!DataUtils.isNullOrEmpty(pathProjectStorage)) {
                String pathProject = pathProjectStorage + "/" + item.projectId.toString() + "/";
                for (MultipartFile multipartFile : item.getFiles()) {
                    pathProject += multipartFile.getOriginalFilename();
                    Files.delete(Paths.get(pathProject));
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public List<Resource> loadFile(UploadFileDTO item) throws IOException {
        List<Resource> resources = new ArrayList<>();
        if (!DataUtils.isNullOrEmpty(item.taskId)) {
            if (!DataUtils.isNullOrEmpty(pathTaskStorage)) {
                String pathTask = pathTaskStorage + "/" + item.taskId.toString() + "/";
                for (MultipartFile multipartFile : item.getFiles()) {
                    pathTask += multipartFile.getOriginalFilename();
                    Resource resource = new UrlResource(pathTask);
                    resources.add(resource);
                }
            }
        }
        if (!DataUtils.isNullOrEmpty(item.projectId)) {
            if (!DataUtils.isNullOrEmpty(pathProjectStorage)) {
                String pathProject = pathProjectStorage + "/" + item.projectId.toString() + "/";
                for (MultipartFile multipartFile : item.getFiles()) {
                    pathProject += multipartFile.getOriginalFilename();
                    Resource resource = new UrlResource(pathProject);
                    resources.add(resource);
                }
            }
        }
        return resources;
    }
}
