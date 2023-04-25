package com.ansv.taskmanagement.service;

import com.ansv.taskmanagement.dto.criteria.SearchCriteria;
import com.ansv.taskmanagement.dto.response.PermissionDTO;
import com.ansv.taskmanagement.dto.response.RoleOfProjectDTO;
import com.ansv.taskmanagement.dto.response.RolePermissionDTO;
import com.ansv.taskmanagement.dto.specification.GenericSpecificationBuilder;
import com.ansv.taskmanagement.model.RoleOfProject;
import com.ansv.taskmanagement.util.DataUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface RolePermissionService {


    void saveRolePermission(List<RolePermissionDTO> item);

    void deleteById(Long id);

    void deleteByRoleId(Long id);

    Integer deleteByListId(List<Long> id);

    Page<RolePermissionDTO> findBySearchCriteria(Optional<String> search, Pageable page);

}