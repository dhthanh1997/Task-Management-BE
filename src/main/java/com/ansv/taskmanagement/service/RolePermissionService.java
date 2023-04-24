package com.ansv.taskmanagement.service;

import com.ansv.taskmanagement.dto.response.PermissionDTO;
import com.ansv.taskmanagement.dto.response.RolePermissionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RolePermissionService {

    RolePermissionDTO findById(Long id);

    RolePermissionDTO save(RolePermissionDTO item);

    List<RolePermissionDTO> saveRolePermission(List<RolePermissionDTO> listData);

    List<RolePermissionDTO> findAll();

    List<RolePermissionDTO> search(Map<String, Object> mapParam);

    Page<RolePermissionDTO> findBySearchCriteria(Optional<String> search, Pageable page);

    void deleteById(Long id);

    void deleteByRoleId(Long id);

    Integer deleteByListId(List<Long> id);

}