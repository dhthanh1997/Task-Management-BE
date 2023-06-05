package com.ansv.taskmanagement.service.impl;

import com.ansv.taskmanagement.dto.criteria.SearchCriteria;
import com.ansv.taskmanagement.dto.response.SectionDTO;
import com.ansv.taskmanagement.dto.response.TaskDTO;
import com.ansv.taskmanagement.dto.specification.GenericSpecificationBuilder;
import com.ansv.taskmanagement.mapper.BaseMapper;
import com.ansv.taskmanagement.model.ProjectSection;
import com.ansv.taskmanagement.model.Section;
import com.ansv.taskmanagement.model.Task;
import com.ansv.taskmanagement.repository.ProjectSectionRepository;
import com.ansv.taskmanagement.repository.SectionRepository;
import com.ansv.taskmanagement.repository.TaskRepository;
import com.ansv.taskmanagement.service.SectionService;
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
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class SectionServiceImpl implements SectionService {

    private static final Logger logger = LoggerFactory.getLogger(SectionServiceImpl.class);

    private static final BaseMapper<Section, SectionDTO> mapper = new BaseMapper<>(Section.class, SectionDTO.class);

    @Autowired
    private SectionRepository repository;

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public SectionDTO findById(Long id) {
        if (DataUtils.notNull(id)) {
            Optional<Section> entity = repository.findById(id);
            if (entity.isPresent()) {
                return mapper.toDtoBean(entity.get());
            }
        }
        return null;
    }

    @Override
    public SectionDTO save(SectionDTO item) {

        Section entity = null;

        SectionDTO dto = findById(item.getId());
        if (DataUtils.notNull(dto)) {
            item.setLastModifiedDate(LocalDateTime.now());
        }
        entity = mapper.toPersistenceBean(item);
        return mapper.toDtoBean(repository.save(entity));

    }

    @Override
    public List<SectionDTO> findAll() {
        List<Section> entities = repository.findAll();
        return mapper.toDtoBean(entities);
    }

    @Override
    public List<SectionDTO> search(Map<String, Object> mapParam) {
        return null;
    }

    @Override
    public Page<SectionDTO> findBySearchCriteria(Optional<String> search, Pageable page) {

        GenericSpecificationBuilder<Section> builder = new GenericSpecificationBuilder<>();

        // check chuỗi để tách các param search
        if (DataUtils.notNull(search)) {
            Pattern pattern = Pattern.compile("(\\w+?)(\\.)(:|<|>|(\\w+?))(\\.)(\\w+?),", Pattern.UNICODE_CHARACTER_CLASS);
            Matcher matcher = pattern.matcher(search + ",");
            while (matcher.find()) {
                builder.with(new SearchCriteria(matcher.group(1), matcher.group(3), matcher.group(6)));
            }
        }
        // specification
        builder.setClazz(Section.class);
        Specification<Section> spec = builder.build();
        Page<SectionDTO> listDTO = repository.findAll(spec, page).map(entity -> {
            SectionDTO dto = mapper.toDtoBean(entity);
            return dto;
        });
        return listDTO;
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Integer deleteByListId(List<Long> listId) {
        return repository.deleteByListId(listId);
    }

    @Override
    public List<SectionDTO> findByProjectId(Long projectId) {
        List<Section> sections = repository.findByProjectId(projectId);
        if(!DataUtils.isNullOrEmpty(sections)) {
            List<SectionDTO> sectionsDTO = mapper.toDtoBean(sections);
            return sectionsDTO;
        }
        return null;
    }
}
