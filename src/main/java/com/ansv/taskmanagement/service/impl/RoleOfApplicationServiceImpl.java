package com.ansv.taskmanagement.service.impl;

import com.ansv.taskmanagement.dto.criteria.SearchCriteria;
import com.ansv.taskmanagement.dto.response.PermissionDTO;
import com.ansv.taskmanagement.dto.response.RoleOfApplicationDTO;
import com.ansv.taskmanagement.dto.response.RolePermissionDTO;
import com.ansv.taskmanagement.dto.specification.GenericSpecificationBuilder;
import com.ansv.taskmanagement.mapper.BaseMapper;
import com.ansv.taskmanagement.model.*;
import com.ansv.taskmanagement.repository.PermissionRepository;
import com.ansv.taskmanagement.repository.RoleOfApplicationRepository;
import com.ansv.taskmanagement.repository.RolePermissionRepository;
import com.ansv.taskmanagement.service.RoleOfApplicationService;
import com.ansv.taskmanagement.util.DataUtils;
import com.ansv.taskmanagement.util.TreeComponent;
import lombok.extern.slf4j.Slf4j;
import one.util.streamex.StreamEx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class RoleOfApplicationServiceImpl implements RoleOfApplicationService {

    private static final Logger logger = LoggerFactory.getLogger(RoleOfApplicationServiceImpl.class);

    private static final BaseMapper<RoleOfApplication, RoleOfApplicationDTO> mapper = new BaseMapper<>(RoleOfApplication.class, RoleOfApplicationDTO.class);

    private static final BaseMapper<Permission, PermissionDTO> mapperPer = new BaseMapper<>(Permission.class, PermissionDTO.class);

    @Autowired
    private RoleOfApplicationRepository repository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private RolePermissionRepository rolePermissionRepository;

    @Override
    public RoleOfApplicationDTO findById(Long id) {
        if (DataUtils.notNull(id)) {
            Optional<RoleOfApplication> entity = repository.findById(id);
            if (entity.isPresent()) {
                return mapper.toDtoBean(entity.get());
            }
        }
        return null;
    }

    @Override
    public RoleOfApplicationDTO save(RoleOfApplicationDTO item) {
//        try {
        RoleOfApplication entity = null;

        RoleOfApplicationDTO dto = findById(item.getId());
        if (DataUtils.notNull(dto)) {
            item.setLastModifiedDate(LocalDateTime.now());
        }
        entity = mapper.toPersistenceBean(item);
        return mapper.toDtoBean(repository.save(entity));
//
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//            return null;
//        }
    }

    @Override
    public List<RoleOfApplicationDTO> findAll() {
        List<RoleOfApplication> entities = repository.findAll();
        return mapper.toDtoBean(entities);
    }

    @Override
    public List<RoleOfApplicationDTO> search(Map<String, Object> mapParam) {
        return null;
    }

    @Override
    public Page<RoleOfApplicationDTO> findBySearchCriteria(Optional<String> search, Pageable page) {
//        try {
        GenericSpecificationBuilder<RoleOfApplication> builder = new GenericSpecificationBuilder<>();
        // check chuỗi để tách các param search
        if (DataUtils.notNull(search)) {
            Pattern pattern = Pattern.compile("(\\w+?)(\\.)(:|<|>|(\\w+?))(\\.)(\\w+?),", Pattern.UNICODE_CHARACTER_CLASS);
            Matcher matcher = pattern.matcher(search + ",");
            while (matcher.find()) {
                builder.with(new SearchCriteria(matcher.group(1), matcher.group(3), matcher.group(6)));
            }
        }
        // specification
        builder.setClazz(RoleOfApplication.class);
        Specification<RoleOfApplication> spec = builder.build();
        Page<RoleOfApplicationDTO> listDTO = repository.findAll(spec, page).map(entity -> {
            RoleOfApplicationDTO dto = mapper.toDtoBean(entity);
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

    @Override
    public List<TreeComponent> getRolePermission(Optional<Long> id) {
        List<Long> permissionsId = new ArrayList<>();
        if (id.isPresent()) {
            permissionsId = rolePermissionRepository.getPermissionIdByRoleId(id.get());
        }
        List<Tree> list = permissionRepository.getPermissionRecursive();
        if (permissionsId.size() > 0) {
            List<Long> finalPermissionsId = permissionsId;
            list = list.stream().filter(value -> finalPermissionsId.contains(value.getId())).collect(Collectors.toList());
        }

        List<TreeComponent> result = new ArrayList<>();

        List<TreeComponent> root = new ArrayList<>();

        if (id.isPresent()) {
            for (Tree per : list) {
                Optional<Permission> rootPermission = permissionRepository.findByCode(per.getParentCode());
                if(rootPermission.isPresent()) {
                    TreeComponent item = new TreeComponent();
                    item.setCode(rootPermission.get().getCode());
//                    item.setParentCode(per.getParentCode());
                    item.setName(rootPermission.get().getName());
                    item.setType(Integer.valueOf(rootPermission.get().getType()));
                    item.setDescription(rootPermission.get().getDescription());
                    item.setId(rootPermission.get().getId());
                    item.setDescription(rootPermission.get().getDescription());
                    root.add(item);
                }
            }
            root = StreamEx.of(root).distinct(TreeComponent::getCode).toList();
        } else {
            for (Tree per : list) {
                if (DataUtils.isNullOrEmpty(per.getParentCode())) {
                    TreeComponent item = new TreeComponent();
                    item.setCode(per.getCode());
                    item.setParentCode(per.getParentCode());
                    item.setName(per.getName());
                    item.setDescription(per.getDescription());
                    item.setId(per.getId());
                    item.setDescription(per.getDescription());
                    item.setType(Integer.valueOf(per.getType()));
                    root.add(item);
                }
            }
        }



        List<TreeComponent> tree = list.stream().filter(value -> !DataUtils.isNullOrEmpty(value.getParentCode())).map(value -> {
            TreeComponent item = new TreeComponent();
            item.setCode(value.getCode());
            item.setName(value.getName());
            item.setParentCode(value.getParentCode());
            item.setDescription(value.getDescription());
            item.setId(value.getId());
            item.setDescription(value.getDescription());
            return item;
        }).collect(Collectors.toList());
        result = DataUtils.recursiveObjectList(tree, root);
        return result;
    }


}
