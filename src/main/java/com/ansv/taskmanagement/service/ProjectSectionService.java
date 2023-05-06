package com.ansv.taskmanagement.service;

import com.ansv.taskmanagement.dto.response.ProjectSectionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProjectSectionService {


    void saveProjectSection(List<ProjectSectionDTO> item);

    void deleteById(Long id);

    void deleteBySectionId(Long id);

    Integer deleteByListId(List<Long> id);

}