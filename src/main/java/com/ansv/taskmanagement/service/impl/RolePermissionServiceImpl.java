package com.ansv.taskmanagement.service.impl;

import com.ansv.taskmanagement.dto.criteria.SearchCriteria;
import com.ansv.taskmanagement.dto.response.PermissionDTO;
import com.ansv.taskmanagement.dto.response.RolePermissionDTO;
import com.ansv.taskmanagement.dto.response.TaskDTO;
import com.ansv.taskmanagement.dto.specification.GenericSpecificationBuilder;
import com.ansv.taskmanagement.mapper.BaseMapper;
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
    public RolePermissionDTO findById(Long id) {
        if (DataUtils.notNull(id)) {
            Optional<RolePermission> entity = repository.findById(id);
            if (entity.isPresent()) {
                return mapper.toDtoBean(entity.get());
            }
        }
        return null;
    }

    @Override
    public RolePermissionDTO save(RolePermissionDTO item) {
//        try {
        RolePermission entity = null;

        RolePermissionDTO dto = findById(item.getId());
        if (DataUtils.notNull(dto)) {
//            item.setLastModifiedDate(LocalDateTime.now());
        }
//        entity.setRoleOfApplication();
        entity = mapper.toPersistenceBean(item);
        return mapper.toDtoBean(repository.save(entity));
//
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//            return null;
//        }
    }

    @Override
    public List<RolePermissionDTO> saveRolePermission(List<RolePermissionDTO> listData) {
        List<RolePermissionDTO> listDTO = new ArrayList<>();
        for(RolePermissionDTO item: listData) {
            RolePermissionDTO dto = save(item);
            listDTO.add(dto);
        }
        return listDTO;
    }

    @Override
    public List<RolePermissionDTO> findAll() {
        List<RolePermission> entities = repository.findAll();
        return mapper.toDtoBean(entities);
    }

    @Override
    public List<RolePermissionDTO> search(Map<String, Object> mapParam) {
        return null;
    }

    @Override
    public Page<RolePermissionDTO> findBySearchCriteria(Optional<String> search, Pageable page) {
//        try {
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
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//            return null;
//        }
    }

    @Override
    public void deleteById(Long id) {
//        try {
        repository.deleteById(id);
//            return 1;
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//            return -1;
//        }
    }

    @Override
    public Integer deleteByListId(List<Long> listId) {
//        try {
        return repository.deleteByListId(listId);
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//            return -1;
//        }
    }
}
