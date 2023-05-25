package com.ansv.taskmanagement.service;

import com.ansv.taskmanagement.dto.response.RoleOfApplicationDTO;
import com.ansv.taskmanagement.dto.response.RolePermissionDTO;
import com.ansv.taskmanagement.util.TreeComponent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RoleOfApplicationService {

    RoleOfApplicationDTO findById(Long id);

    RoleOfApplicationDTO save(RoleOfApplicationDTO item);

    List<RoleOfApplicationDTO> findAll();

    List<RoleOfApplicationDTO> search(Map<String, Object> mapParam);

    Page<RoleOfApplicationDTO> findBySearchCriteria(Optional<String> search, Pageable page);

    void deleteById(Long id);

    Integer deleteByListId(List<Long> id);

    List<TreeComponent>  getRolePermission(Optional<Long> id);

    List<TreeComponent>  getRolePermission();

}