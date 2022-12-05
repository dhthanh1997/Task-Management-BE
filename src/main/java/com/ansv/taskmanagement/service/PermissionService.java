package com.ansv.taskmanagement.service;

import com.ansv.taskmanagement.dto.response.PermissionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PermissionService {

    PermissionDTO findById(Long id);

    PermissionDTO save(PermissionDTO item);

    List<PermissionDTO> findAll();

    List<PermissionDTO> search(Map<String, Object> mapParam);

    Page<PermissionDTO> findBySearchCriteria(Optional<String> search, Pageable page);

    void deleteById(Long id);

    Integer deleteByListId(List<Long> id);

}