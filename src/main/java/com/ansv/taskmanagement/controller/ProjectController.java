package com.ansv.taskmanagement.controller;


import com.ansv.taskmanagement.dto.response.ProjectDTO;
import com.ansv.taskmanagement.dto.response.ResponseDataObject;
import com.ansv.taskmanagement.service.ProjectService;
import com.ansv.taskmanagement.util.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping("")
    public ProjectDTO createProject(@RequestBody ProjectDTO item) {
        ProjectDTO dto =  projectService.save(item);
        return dto;
    }

}
