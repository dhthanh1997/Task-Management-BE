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

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class StorageServiceImpl implements StorageService {


    @Value("${app.path.task:#{null}}")
    private String pathTaskStorage;

    @Value("${app.path.project:#{null}}")
    private String pathProjectStorage;


    @Override
    public Boolean storageFile(List<MultipartFile> files, UploadFileDTO item) throws IOException {
        if (!DataUtils.isNullOrEmpty(item.taskId)) {
            if (!DataUtils.isNullOrEmpty(pathTaskStorage)) {
                // Tạo folder
                if(!Files.isDirectory(Paths.get(pathTaskStorage))) {
                    Files.createDirectories(Paths.get(pathTaskStorage));
                }
                String pathTask = pathTaskStorage  + item.taskId.toString();
                if(!Files.isDirectory(Paths.get(pathTask))) {
                    Files.createDirectories(Paths.get(pathTask));
                }
                for(MultipartFile file: files) {
                    Files.copy(file.getInputStream(), Paths.get(pathTask).resolve(file.getOriginalFilename()));
                }
                return true;
            }
        }
        if (!DataUtils.isNullOrEmpty(item.projectId)) {
            if (!DataUtils.isNullOrEmpty(pathProjectStorage)) {
                // Tạo folder
                if(!Files.isDirectory(Paths.get(pathProjectStorage))) {
                    Files.createDirectories(Paths.get(pathProjectStorage));
                }
                String pathProject = pathProjectStorage  + item.taskId.toString();
                if(!Files.isDirectory(Paths.get(pathProject))) {
                    Files.createDirectories(Paths.get(pathProject));
                }
                for(MultipartFile file: files) {
                    Files.copy(file.getInputStream(), Paths.get(pathProject).resolve(file.getOriginalFilename()));
                }
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
//                for (MultipartFile multipartFile : item.getFiles()) {
//                    pathTask += multipartFile.getOriginalFilename();
//                    Files.delete(Paths.get(pathTask));
//                }
            }
            return true;
        }
        if (!DataUtils.isNullOrEmpty(item.projectId)) {
            if (!DataUtils.isNullOrEmpty(pathProjectStorage)) {
                String pathProject = pathProjectStorage + "/" + item.projectId.toString() + "/";
//                for (MultipartFile multipartFile : item.getFiles()) {
//                    pathProject += multipartFile.getOriginalFilename();
//                    Files.delete(Paths.get(pathProject));
//                }
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
//                for (MultipartFile multipartFile : item.getFiles()) {
//                    pathTask += multipartFile.getOriginalFilename();
//                    Resource resource = new UrlResource(pathTask);
//                    resources.add(resource);
//                }
            }
        }
        if (!DataUtils.isNullOrEmpty(item.projectId)) {
            if (!DataUtils.isNullOrEmpty(pathProjectStorage)) {
                String pathProject = pathProjectStorage + "/" + item.projectId.toString() + "/";
//                for (MultipartFile multipartFile : item.getFiles()) {
//                    pathProject += multipartFile.getOriginalFilename();
//                    Resource resource = new UrlResource(pathProject);
//                    resources.add(resource);
//                }
            }
        }
        return resources;
    }

    @Override
    public File getFileById(UploadFileDTO item) throws IOException {
        if (!DataUtils.isNullOrEmpty(item.taskId)) {
            String path = this.pathTaskStorage + item.getTaskId() + '/' + item.getName();
            File file = new File(path);
            return file;
        }
        if (!DataUtils.isNullOrEmpty(item.projectId)) {
            String path = this.pathProjectStorage + item.getTaskId() + '/' + item.getName();
            File file = new File(path);
            return file;
        }

        return null;
    }

    @Override
    public List<String> getName(UploadFileDTO item) throws IOException {
        List<String> listName = new ArrayList<>();
        if (!DataUtils.isNullOrEmpty(item.taskId)) {
            String path = this.pathTaskStorage + item.getTaskId();


        }
        if (!DataUtils.isNullOrEmpty(item.projectId)) {
            String path = this.pathProjectStorage + item.getTaskId();
        }

        return listName;
    }
}
