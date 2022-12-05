package com.ansv.taskmanagement.controllers;


import com.ansv.taskmanagement.dto.criteria.SearchCriteria;
import com.ansv.taskmanagement.dto.response.ProjectDTO;
import com.ansv.taskmanagement.dto.response.ResponseDataObject;
import com.ansv.taskmanagement.dto.specification.ProjectSpecificationBuilder;
import com.ansv.taskmanagement.model.Project;
import com.ansv.taskmanagement.service.ProjectService;
import com.ansv.taskmanagement.util.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/project")
public class ProjectController extends BaseController {

    @Autowired
    private ProjectService projectService;

    @GetMapping("")
    public ResponseEntity<ResponseDataObject<ProjectDTO>> searchByCriteria(@RequestParam(name = "pageNumber") int pageNumber, @RequestParam(name = "pageSize") int pageSize, @RequestParam(name = "search") Optional<String> search) {
        ProjectSpecificationBuilder builder = new ProjectSpecificationBuilder();
        ResponseDataObject<ProjectDTO> response = new ResponseDataObject<ProjectDTO>();
        // check chuỗi để tách các param search
        if (DataUtils.notNull(search)) {
            Pattern pattern = Pattern.compile("(\\w+?)(\\.)(:|<|>|(\\w+?))(\\.)(\\w+?),", Pattern.UNICODE_CHARACTER_CLASS);
            Matcher matcher = pattern.matcher(search + ",");
            while (matcher.find()) {
                builder.with(new SearchCriteria(matcher.group(1), matcher.group(3), matcher.group(6)));
            }
        }
        // specification
        Specification<Project> spec = builder.build();
        Pageable page = pageRequest(new ArrayList<>(), pageNumber - 1, pageSize);
        Page<ProjectDTO> listDTO = projectService.findBySearchCriteria(spec, page);
        // response
        response.pagingData = listDTO;
        response.success();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<ResponseDataObject<ProjectDTO>> create(@RequestBody @Valid ProjectDTO item) {
        ResponseDataObject<ProjectDTO> response = new ResponseDataObject<>();
        ProjectDTO dto = projectService.save(item);
        response.initData(dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDataObject<ProjectDTO>> update(@RequestBody @Valid ProjectDTO item) {
        ResponseDataObject<ProjectDTO> response = new ResponseDataObject<>();
        ProjectDTO dto = projectService.save(item);
        response.initData(dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDataObject<ProjectDTO>> getById(@PathVariable(value = "id") Long id) {
        ResponseDataObject<ProjectDTO> response = new ResponseDataObject<>();
        ProjectDTO dto = projectService.findById(id);
        response.initData(dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDataObject<Integer>> deleteById(@PathVariable(value = "id") Long id) {
        ResponseDataObject<Integer> response = new ResponseDataObject<>();
        projectService.deleteById(id);
        response.initData(1);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/deleteByListId")
    public ResponseEntity<ResponseDataObject<Integer>> deleteByListId(@RequestBody List<Long> listId) {
        ResponseDataObject<Integer> response = new ResponseDataObject<>();
        Integer delete = projectService.deleteByListId(listId);
        response.initData(delete);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}
