package com.ansv.taskmanagement.service;

import com.ansv.taskmanagement.dto.response.SectionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface SectionService {

    SectionDTO findById(Long id);

    SectionDTO save(SectionDTO item);

    List<SectionDTO> findAll();

    List<SectionDTO> search(Map<String, Object> mapParam);

    Page<SectionDTO> findBySearchCriteria(Optional<String> search, Pageable page);

    void deleteById(Long id);

    Integer deleteByListId(List<Long> id);

    List<SectionDTO> findByProjectId(Long projectId);

}