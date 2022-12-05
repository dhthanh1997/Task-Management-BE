package com.ansv.taskmanagement.service;

import com.ansv.taskmanagement.dto.response.RoleOfProjectDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RoleOfProjectService {

    RoleOfProjectDTO findById(Long id);

    RoleOfProjectDTO save(RoleOfProjectDTO item);

    List<RoleOfProjectDTO> findAll();

    List<RoleOfProjectDTO> search(Map<String, Object> mapParam);

    Page<RoleOfProjectDTO> findBySearchCriteria(Optional<String> search, Pageable page);

    void deleteById(Long id);

    Integer deleteByListId(List<Long> id);

}