package com.ansv.taskmanagement.controllers;

import com.ansv.taskmanagement.dto.request.UploadFileDTO;
import com.ansv.taskmanagement.dto.response.ResponseDataObject;
import com.ansv.taskmanagement.dto.response.WorkLogDTO;
import com.ansv.taskmanagement.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController()
@RequestMapping("/api/uploadFile")
public class UploadController extends BaseController {

    @Autowired
    private StorageService storageService;

    @PostMapping("/task")
    public ResponseEntity<ResponseDataObject<String>> uploadFileInTask(@RequestParam("files") MultipartFile file, @RequestParam("data") UploadFileDTO item) throws IOException {
        ResponseDataObject<String> response = new ResponseDataObject<>();
        Boolean storage =  storageService.storageFile(file, item);
        if(storage) {
            response.success();
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/project")
    public ResponseEntity<ResponseDataObject<String>> uploadFileInProject(@RequestParam("files") MultipartFile file, @RequestParam("data") UploadFileDTO item) throws IOException {
        ResponseDataObject<String> response = new ResponseDataObject<>();
        Boolean storage =  storageService.storageFile(file, item);
        if(storage) {
            response.success();
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
