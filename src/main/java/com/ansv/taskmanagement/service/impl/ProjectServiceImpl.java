package com.ansv.taskmanagement.service.impl;

import com.ansv.taskmanagement.dto.response.ProjectDTO;
import com.ansv.taskmanagement.mapper.BaseMapper;
import com.ansv.taskmanagement.model.Project;
import com.ansv.taskmanagement.repository.ProjectRepository;
import com.ansv.taskmanagement.service.ProjectService;
import com.ansv.taskmanagement.util.DataUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.maven.model.profile.ProfileSelector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class ProjectServiceImpl implements ProjectService {

    private static final Logger logger = LoggerFactory.getLogger(ProjectServiceImpl.class);

    private static final BaseMapper<Project, ProjectDTO> mapper = new BaseMapper<>(Project.class, ProjectDTO.class);

    @Autowired
    private ProjectRepository repository;

    @Override
    public ProjectDTO findById(Long id) {
        Optional<Project> entity = repository.findById(id);
        if(entity.isPresent()) {
            return mapper.toDtoBean(entity.get());
        }
        return null;
    }

    @Override
    public ProjectDTO save(ProjectDTO item) {
        try {
            Project entity = null;
            ProjectDTO dto = findById(item.getId());
            if(DataUtils.notNull(dto)) {
                entity.setLastModifiedDate(LocalDateTime.now());
            }
            entity = mapper.toPersistenceBean(item);
            return mapper.toDtoBean(repository.save(entity));

        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public List<ProjectDTO> findAll() {
        return null;
    }

    @Override
    public List<ProjectDTO> search(Map<String, Object> mapParam) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void deleteByListId(List<Long> id) {

    }
}
