package com.ansv.taskmanagement.service;

import com.ansv.taskmanagement.dto.response.WorkLogDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface WorkLogService {

    WorkLogDTO findById(Long id);

    WorkLogDTO save(WorkLogDTO item);

    List<WorkLogDTO> findAll();

    List<WorkLogDTO> search(Map<String, Object> mapParam);

    Page<WorkLogDTO> findBySearchCriteria(Optional<String> search, Pageable page);

    void deleteById(Long id);

    Integer deleteByListId(List<Long> id);

}