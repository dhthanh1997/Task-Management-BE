package com.ansv.taskmanagement.service;

import com.ansv.taskmanagement.dto.response.TagDTO;
import com.ansv.taskmanagement.dto.response.TaskDTO;
import com.ansv.taskmanagement.model.Tag;
import com.ansv.taskmanagement.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TagService {

    TagDTO findById(Long id);

    TagDTO save(TagDTO item);

    List<TagDTO> findAll();

    List<TagDTO> search(Map<String, Object> mapParam);

    Page<TagDTO> findBySearchCriteria(Optional<String> search, Pageable page);

    void deleteById(Long id);

    Integer deleteByListId(List<Long> id);

}