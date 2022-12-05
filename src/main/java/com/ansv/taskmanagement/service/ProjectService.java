package com.ansv.taskmanagement.service;

import com.ansv.taskmanagement.dto.response.ProjectDTO;
import com.ansv.taskmanagement.model.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ProjectService {

    ProjectDTO findById(Long id);

    ProjectDTO save(ProjectDTO item);

    List<ProjectDTO> findAll();

    List<ProjectDTO> search(Map<String, Object> mapParam);

    Page<ProjectDTO> findBySearchCriteria(Optional<String> search, Pageable page);

    void deleteById(Long id);

    Integer deleteByListId(List<Long> id);

}