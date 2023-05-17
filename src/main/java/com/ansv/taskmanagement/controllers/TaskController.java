package com.ansv.taskmanagement.controllers;


import com.ansv.taskmanagement.dto.response.TaskDTO;
import com.ansv.taskmanagement.dto.response.ResponseDataObject;
import com.ansv.taskmanagement.service.TaskService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/task")
public class TaskController extends BaseController {

    @Autowired
    private TaskService taskService;



    @GetMapping("")
    public ResponseEntity<ResponseDataObject<TaskDTO>> searchByCriteria(@RequestParam(name = "pageNumber") int pageNumber, @RequestParam(name = "pageSize") int pageSize, @RequestParam(name = "search") Optional<String> search, @RequestParam(name = "sort") Optional<String> sort) {
        ResponseDataObject<TaskDTO> response = new ResponseDataObject<>();
        List<String> sorts = new ArrayList<>();
//        sort pattern: (\w+?)(,)
        if (sort.isPresent()) {
            Pattern pattern = Pattern.compile("(\\w+?)(,)", Pattern.UNICODE_CHARACTER_CLASS);
            Matcher matcher = pattern.matcher(sort.get());
            while (matcher.find()) {
                sorts.add(matcher.group(1));
            }
        }
        Pageable page = pageRequest(sorts, pageNumber - 1, pageSize);
        Page<TaskDTO> listDTO = taskService.findBySearchCriteria(search, page);
        // response
        response.pagingData = listDTO;
        response.success();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<ResponseDataObject<TaskDTO>> create(@RequestBody @Valid TaskDTO item) {
        ResponseDataObject<TaskDTO> response = new ResponseDataObject<>();
        TaskDTO dto = taskService.save(item);
        response.initData(dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/listTask")
    public ResponseEntity<ResponseDataObject<List<TaskDTO>>> addListTask(@RequestBody @Valid List<TaskDTO> item) {
        ResponseDataObject<List<TaskDTO>> response = new ResponseDataObject<>();
        List<TaskDTO> listDTO = taskService.saveListTask(item);
        response.initData(listDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDataObject<TaskDTO>> update(@PathVariable(value = "id") Long id, @RequestBody @Valid TaskDTO item) {
        ResponseDataObject<TaskDTO> response = new ResponseDataObject<>();
        item.setId(id);
        TaskDTO dto = taskService.save(item);
        response.initData(dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/updateListTask")
    public ResponseEntity<ResponseDataObject<List<TaskDTO>>> updateListTask(@RequestBody @Valid List<TaskDTO> item) {
        ResponseDataObject<List<TaskDTO>> response = new ResponseDataObject<>();
        List<TaskDTO> listDTO = taskService.saveListTask(item);
        response.initData(listDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/markCompleteTask/{id}")
    public ResponseEntity<ResponseDataObject<TaskDTO>> markCompleteTask(@PathVariable(value = "id") Long id) {
        ResponseDataObject<TaskDTO> response = new ResponseDataObject<>();
        TaskDTO dto = taskService.markCompleteTask(id);
        response.initData(dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDataObject<TaskDTO>> getById(@PathVariable(value = "id") Long id) {
        ResponseDataObject<TaskDTO> response = new ResponseDataObject<>();
        TaskDTO dto = taskService.getById(id);
        response.initData(dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/withParent/{id}")
    public ResponseEntity<ResponseDataObject<List<TaskDTO>>> getByParentId(@PathVariable(value = "id") Long id) {
        ResponseDataObject<List<TaskDTO>> response = new ResponseDataObject<>();
        List<TaskDTO> listData = taskService.findByParentId(id);
        response.initData(listData);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDataObject<Integer>> deleteById(@PathVariable(value = "id") Long id) {
        ResponseDataObject<Integer> response = new ResponseDataObject<>();
        taskService.deleteById(id);
        response.initData(1);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/deleteByListId")
    public ResponseEntity<ResponseDataObject<Integer>> deleteByListId(@RequestBody List<Long> listId) {
        ResponseDataObject<Integer> response = new ResponseDataObject<>();
        Integer delete = taskService.deleteByListId(listId);
        response.initData(delete);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping("/uploadFileExcel")
    public ResponseEntity<ResponseDataObject<String>> uploadFileExcel(@RequestParam(name = "file") MultipartFile file) throws Exception {
        ResponseDataObject<String> response = new ResponseDataObject<>();

        response.initData("Successfull");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



}
