package com.ansv.taskmanagement.service.impl;

import com.ansv.taskmanagement.dto.criteria.SearchCriteria;
import com.ansv.taskmanagement.dto.response.ProjectSectionDTO;
import com.ansv.taskmanagement.dto.specification.GenericSpecificationBuilder;
import com.ansv.taskmanagement.mapper.BaseMapper;
import com.ansv.taskmanagement.model.ProjectSection;
import com.ansv.taskmanagement.repository.ProjectSectionRepository;
import com.ansv.taskmanagement.service.ProjectSectionService;
import com.ansv.taskmanagement.util.DataUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class ProjectSectionServiceImpl implements ProjectSectionService {

    private static final Logger logger = LoggerFactory.getLogger(ProjectSectionServiceImpl.class);

    private static final BaseMapper<ProjectSection, ProjectSectionDTO> mapper = new BaseMapper<>(ProjectSection.class, ProjectSectionDTO.class);

    @Autowired
    private ProjectSectionRepository repository;


    @Override
    public void saveProjectSection(List<ProjectSectionDTO> items) {
        List<ProjectSection> list = new ArrayList<>();
        list = mapper.toPersistenceBean(items);
        repository.saveAll(list);
    }





    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteBySectionId(Long id) {
        repository.deleteBySectionId(id);
    }

    @Override
    public Integer deleteByListId(List<Long> listId) {
        return repository.deleteByListId(listId);
    }


}
