package com.ansv.taskmanagement.service.impl;

import com.ansv.taskmanagement.dto.criteria.SearchCriteria;
import com.ansv.taskmanagement.dto.response.PermissionDTO;
import com.ansv.taskmanagement.dto.response.RoleOfProjectDTO;
import com.ansv.taskmanagement.dto.response.RolePermissionDTO;
import com.ansv.taskmanagement.dto.response.TaskDTO;
import com.ansv.taskmanagement.dto.specification.GenericSpecificationBuilder;
import com.ansv.taskmanagement.mapper.BaseMapper;
import com.ansv.taskmanagement.model.Permission;
import com.ansv.taskmanagement.model.RoleOfProject;
import com.ansv.taskmanagement.model.RolePermission;
import com.ansv.taskmanagement.model.TaskTag;
import com.ansv.taskmanagement.repository.RolePermissionRepository;
import com.ansv.taskmanagement.service.RolePermissionService;
import com.ansv.taskmanagement.util.DataUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class RolePermissionServiceImpl implements RolePermissionService {

    private static final Logger logger = LoggerFactory.getLogger(RolePermissionServiceImpl.class);

    private static final BaseMapper<RolePermission, RolePermissionDTO> mapper = new BaseMapper<>(RolePermission.class, RolePermissionDTO.class);

    @Autowired
    private RolePermissionRepository repository;


    @Override
    public void saveRolePermission(List<RolePermissionDTO> items) {
        List<RolePermission> list = new ArrayList<>();
        list = mapper.toPersistenceBean(items);
        repository.saveAll(list);
    }





    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteByRoleId(Long id) {
        repository.deleteByRoleId(id);
    }

    @Override
    public Integer deleteByListId(List<Long> listId) {
        return repository.deleteByListId(listId);
    }

    @Override
    public Page<RolePermissionDTO> findBySearchCriteria(Optional<String> search, Pageable page) {
        GenericSpecificationBuilder<RolePermission> builder = new GenericSpecificationBuilder<>();
        // check chuỗi để tách các param search
        if (DataUtils.notNull(search)) {
            Pattern pattern = Pattern.compile("(\\w+?)(\\.)(:|<|>|(\\w+?))(\\.)(\\w+?),", Pattern.UNICODE_CHARACTER_CLASS);
            Matcher matcher = pattern.matcher(search + ",");
            while (matcher.find()) {
                builder.with(new SearchCriteria(matcher.group(1), matcher.group(3), matcher.group(6)));
            }
        }
        // specification
        builder.setClazz(RolePermission.class);
        Specification<RolePermission> spec = builder.build();
        Page<RolePermissionDTO> listDTO = repository.findAll(spec, page).map(entity -> {
            RolePermissionDTO dto = mapper.toDtoBean(entity);
            return dto;
        });
        return listDTO;

    }
}
