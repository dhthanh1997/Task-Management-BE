package com.ansv.taskmanagement.controllers;


import com.ansv.taskmanagement.dto.response.ReportDTO;
import com.ansv.taskmanagement.dto.response.ResponseDataObject;
import com.ansv.taskmanagement.dto.response.TaskDTO;
import com.ansv.taskmanagement.dto.response.UserInfoDTO;
import com.ansv.taskmanagement.service.ReportService;
import com.ansv.taskmanagement.service.TaskService;
import com.ansv.taskmanagement.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@RestController
@RequestMapping("/api/report")
public class ReportController extends BaseController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private TaskService taskService;

    @GetMapping("")
    public ResponseEntity<ResponseDataObject<ReportDTO>> searchByCriteria(@RequestParam(name = "search") Optional<String> search, @RequestParam(name = "sort") Optional<String> sort) {
        ResponseDataObject<ReportDTO> response = new ResponseDataObject<>();
        List<String> sorts = new ArrayList<>();
//        sort pattern: (\w+?)(,)
        if (sort.isPresent()) {
            Pattern pattern = Pattern.compile("(\\w+?)(,)", Pattern.UNICODE_CHARACTER_CLASS);
            Matcher matcher = pattern.matcher(sort.get());
            while (matcher.find()) {
                sorts.add(matcher.group(1));
            }
        }
        Pageable page = pageRequest(sorts, 0, 999);
        List<ReportDTO> listDTO = reportService.findBySearchCriteria(search, page);
        // response
        response.listData = listDTO;
        response.success();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/exportExcel")
    public ResponseEntity<InputStreamResource> exportExcell(@RequestParam(name = "projectId") String projectId) throws FileNotFoundException {
//        ResponseDataObject<String> response = new ResponseDataObject<>();
        HttpHeaders httpHeaders = new HttpHeaders();
        File file = taskService.exportReport(Long.parseLong(projectId));
        httpHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());
        httpHeaders.setCacheControl("must-revalidate, post-check=0, pre-check=0");
//        response.initData("Successfull");

        InputStreamResource inputStreamResource = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .contentLength(file.length())
                .body(inputStreamResource);
    }


}
