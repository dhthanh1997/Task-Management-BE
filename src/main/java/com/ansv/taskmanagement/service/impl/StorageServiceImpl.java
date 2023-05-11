package com.ansv.taskmanagement.service.impl;

import com.ansv.taskmanagement.dto.request.UploadFileDTO;
import com.ansv.taskmanagement.service.StorageService;
import com.ansv.taskmanagement.util.DataUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
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
                String pathTask = pathTaskStorage + "/" + item.taskId.toString();
                Path path = Paths.get(pathTask).resolve(item.getName());
                Files.deleteIfExists(path);
            }
            return true;

        }
        if (!DataUtils.isNullOrEmpty(item.projectId)) {
            if (!DataUtils.isNullOrEmpty(pathProjectStorage)) {
                String pathProject = pathProjectStorage + "/" + item.projectId.toString();
                Path path = Paths.get(pathProject).resolve(item.getName());
                Files.deleteIfExists(path);
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
    public Resource getFileById(UploadFileDTO item) throws IOException {
        if (!DataUtils.isNullOrEmpty(item.taskId)) {
            Path rawPath = Paths.get(this.pathTaskStorage + item.getTaskId()).resolve(item.getName());
            Resource resource = new UrlResource(rawPath.toUri());
            return resource;
        }
        if (!DataUtils.isNullOrEmpty(item.projectId)) {
            Path rawPath = Paths.get(this.pathProjectStorage + item.getTaskId()).resolve(item.getName());
            Resource resource = new UrlResource(rawPath.toUri());
            return resource;
        }
        return null;
    }

    @Override
    public List<UploadFileDTO> getNameFileOfTask(Long id) {
        String path = this.pathTaskStorage + id;
        List<UploadFileDTO> names = new ArrayList<>();
        if(Files.isDirectory(Paths.get(path))) {
            File folder = new File(path);
            File[] files = folder.listFiles();
            for(File file: files) {
                UploadFileDTO item = new UploadFileDTO();
                item.taskId = id;
                item.name = file.getName();
                item.size = file.length();
                item.path = file.getPath();
                names.add(item);
            }
        }
        return names;
    }

    @Override
    public List<UploadFileDTO> getNameFileOfProject(Long id) {
        String path = this.pathProjectStorage + id;
        List<UploadFileDTO> names = new ArrayList<>();
        if(Files.isDirectory(Paths.get(path))) {
            File folder = new File(path);
            File[] files = folder.listFiles();
            for(File file: files) {
                UploadFileDTO item = new UploadFileDTO();
                item.taskId = id;
                item.name = file.getName();
                item.size = file.length();
                item.path = file.getPath();
                names.add(item);
            }
        }
        return names;
    }


}
