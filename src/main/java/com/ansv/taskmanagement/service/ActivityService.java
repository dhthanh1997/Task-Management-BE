package com.ansv.taskmanagement.service;

import com.ansv.taskmanagement.dto.response.ActivityDTO;
import com.ansv.taskmanagement.model.Activity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ActivityService {

    ActivityDTO findById(Long id);

    ActivityDTO save(ActivityDTO item);

    List<ActivityDTO> findAll();

    List<ActivityDTO> search(Map<String, Object> mapParam);

    Page<ActivityDTO> findBySearchCriteria(Optional<String> search, Pageable page);

    void deleteById(Long id);

    Integer deleteByListId(List<Long> id);

}