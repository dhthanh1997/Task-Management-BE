package com.ansv.taskmanagement.service;

import com.ansv.taskmanagement.dto.response.CommentDTO;
import com.ansv.taskmanagement.dto.response.TaskDTO;
import com.ansv.taskmanagement.model.Comment;
import com.ansv.taskmanagement.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CommentService {

    CommentDTO findById(Long id);

    CommentDTO save(CommentDTO item);

    List<CommentDTO> findAll();

    List<CommentDTO> search(Map<String, Object> mapParam);

    Page<CommentDTO> findBySearchCriteria(Optional<String> search, Pageable page);

    void deleteById(Long id);

    Integer deleteByListId(List<Long> id);

}