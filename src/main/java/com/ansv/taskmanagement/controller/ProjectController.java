package com.ansv.taskmanagement.controller;


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

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/project")
public class ProjectController extends BaseController {

    @Autowired
    private ProjectService projectService;

    @GetMapping("")
    public ResponseEntity<ResponseDataObject<ProjectDTO>> get(@RequestParam(name = "pageNumber") int pageNumber, @RequestParam(name = "pageSize") int pageSize, @RequestParam(name = "search") String search) {
        ProjectSpecificationBuilder builder = new ProjectSpecificationBuilder();
        ResponseDataObject<ProjectDTO> response = new ResponseDataObject<ProjectDTO>();
        // check chuỗi để tách các param search
        if (DataUtils.notNull(search)) {
            Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),", Pattern.UNICODE_CHARACTER_CLASS);
            Matcher matcher = pattern.matcher(search + ",");
            while (matcher.find()) {
                builder.with(new SearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3)));
            }
        }
        // specification
        Specification<Project> spec = builder.build();
        Pageable page = pageRequest(new ArrayList<>(), pageNumber, pageSize);
        Page<ProjectDTO> listDTO = projectService.findBySearchCriteria(spec, page);
        // response
        response.pagingData = listDTO;
        response.stattus = HttpStatus.OK;
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("")
    public ProjectDTO create(@RequestBody ProjectDTO item) {
        ProjectDTO dto = projectService.save(item);
        return dto;
    }

    @PutMapping("/{id}")
    public ProjectDTO update(@RequestBody ProjectDTO item) {
        ProjectDTO dto = projectService.save(item);
        return dto;
    }

    @DeleteMapping("/{id}")
    public Integer deleteById(@PathVariable(value = "id") Long id) {
        Integer delete = projectService.deleteById(id);
        return delete;
    }

    @PostMapping("/deleteByListId")
    public Integer deleteByListId(@RequestBody List<Long> listId) {
        Integer delete = projectService.deleteByListId(listId);
        return delete;
    }

}
