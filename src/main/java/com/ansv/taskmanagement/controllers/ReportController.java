package com.ansv.taskmanagement.controllers;


import com.ansv.taskmanagement.dto.response.ReportDTO;
import com.ansv.taskmanagement.dto.response.ResponseDataObject;
import com.ansv.taskmanagement.dto.response.TaskDTO;
import com.ansv.taskmanagement.dto.response.UserInfoDTO;
import com.ansv.taskmanagement.service.ReportService;
import com.ansv.taskmanagement.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        Page<ReportDTO> listDTO = reportService.findBySearchCriteria(search, page);
        // response
        response.pagingData = listDTO;
        response.success();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
