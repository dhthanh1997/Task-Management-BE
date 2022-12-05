package com.ansv.taskmanagement.service;

import com.ansv.taskmanagement.dto.response.TaskTagDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TaskTagService {

    TaskTagDTO findById(Long id);

    TaskTagDTO save(TaskTagDTO item);

    List<TaskTagDTO> findAll();

    List<TaskTagDTO> search(Map<String, Object> mapParam);

    Page<TaskTagDTO> findBySearchCriteria(Optional<String> search, Pageable page);

    void deleteById(Long id);

    Integer deleteByListId(List<Long> id);

}