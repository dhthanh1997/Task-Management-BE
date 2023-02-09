package com.ansv.taskmanagement.service.impl;

import com.ansv.taskmanagement.constants.StateEnum;
import com.ansv.taskmanagement.dto.criteria.SearchCriteria;
import com.ansv.taskmanagement.dto.request.TaskImportDTO;
import com.ansv.taskmanagement.dto.response.TaskDTO;
import com.ansv.taskmanagement.dto.specification.GenericSpecificationBuilder;
import com.ansv.taskmanagement.mapper.BaseMapper;
import com.ansv.taskmanagement.model.Task;
import com.ansv.taskmanagement.repository.TaskRepository;
import com.ansv.taskmanagement.service.TaskService;
import com.ansv.taskmanagement.util.DataUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.plaf.nimbus.State;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class TaskServiceImpl implements TaskService {

    private static final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

    private static final BaseMapper<Task, TaskDTO> mapper = new BaseMapper<>(Task.class, TaskDTO.class);

    @Autowired
    private TaskRepository repository;

    @Override
    public TaskDTO findById(Long id) {
        if (DataUtils.notNull(id)) {
            Optional<Task> entity = repository.findById(id);
            if (entity.isPresent()) {
                return mapper.toDtoBean(entity.get());
            }
        }
        return null;
    }

    @Override
    public TaskDTO save(TaskDTO item) {
//        try {
        Task entity = null;

        TaskDTO dto = findById(item.getId());
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
    public TaskDTO markCompleteTask(Long id) {
        TaskDTO dto = findById(id);
        switch (dto.getState()) {
            // Chưa hoàn thành -> hoàn thành
            case 0:
                dto.setState((byte)Arrays.asList(StateEnum.values()).indexOf(StateEnum.DONE));
                break;
            // Hoàn thành -> chưa hoàn thành
            case 1:
                dto.setState((byte)Arrays.asList(StateEnum.values()).indexOf(StateEnum.NOT_DONE));
                break;
            default:
                dto.setState((byte)Arrays.asList(StateEnum.values()).indexOf(StateEnum.DONE));
                break;
        }
        dto = save(dto);
        return dto;
    }

    @Override
    public List<TaskDTO> saveListTask(List<TaskDTO> listData) {
        List<TaskDTO> listDTO = new ArrayList<>();
        for(TaskDTO task: listData) {
            TaskDTO dto = save(task);
            listDTO.add(dto);
        }
        return listDTO;
    }


    @Override
    public List<TaskDTO> findAll() {
        List<Task> entities = repository.findAll();
        return mapper.toDtoBean(entities);
    }

    @Override
    public List<TaskDTO> search(Map<String, Object> mapParam) {
        return null;
    }

    @Override
    public List<TaskDTO> findByParentId(Long id) {
        List<Task> listData = repository.findByParentId(id);
        List<TaskDTO> listDTOs = mapper.toDtoBean(listData);
        return listDTOs;
    }

    @Override
    public Page<TaskDTO> findBySearchCriteria(Optional<String> search, Pageable page) {
//        try {
        GenericSpecificationBuilder<Task> builder = new GenericSpecificationBuilder<>();

        // check chuỗi để tách các param search
        if (DataUtils.notNull(search)) {
            Pattern pattern = Pattern.compile("(\\w+?)(\\.)(:|<|>|(\\w+?))(\\.)(\\w+?),", Pattern.UNICODE_CHARACTER_CLASS);
            Matcher matcher = pattern.matcher(search + ",");
            while (matcher.find()) {
                builder.with(new SearchCriteria(matcher.group(1), matcher.group(3), matcher.group(6)));
            }
        }
        // specification
        builder.setClazz(Task.class);   
        Specification<Task> spec = builder.build();
        Page<TaskDTO> listDTO = repository.findAll(spec, page).map(entity -> {
            TaskDTO dto = mapper.toDtoBean(entity);
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
    public void importTask(List<TaskImportDTO> dtos) {

    }
}
