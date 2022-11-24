package com.ansv.taskmanagement.service;

import com.ansv.taskmanagement.dto.response.ProjectDTO;

import java.util.List;
import java.util.Map;

public interface ProjectService {

    ProjectDTO findById(Long id);

    ProjectDTO save(ProjectDTO item);

    List<ProjectDTO> findAll();

    List<ProjectDTO> search(Map<String, Object> mapParam);

    void deleteById(Long id);

    void deleteByListId(List<Long> id);

}