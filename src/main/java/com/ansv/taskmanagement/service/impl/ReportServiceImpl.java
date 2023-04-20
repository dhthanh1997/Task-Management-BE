package com.ansv.taskmanagement.service.impl;

import com.ansv.taskmanagement.constants.StateEnum;
import com.ansv.taskmanagement.dto.criteria.SearchCriteria;
import com.ansv.taskmanagement.dto.request.TaskImportDTO;
import com.ansv.taskmanagement.dto.response.ReportDTO;
import com.ansv.taskmanagement.dto.response.TaskDTO;
import com.ansv.taskmanagement.dto.specification.GenericSpecificationBuilder;
import com.ansv.taskmanagement.mapper.BaseMapper;
import com.ansv.taskmanagement.model.Task;
import com.ansv.taskmanagement.repository.TaskRepository;
import com.ansv.taskmanagement.service.ReportService;
import com.ansv.taskmanagement.service.TaskService;
import com.ansv.taskmanagement.util.DataUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class ReportServiceImpl implements ReportService {

    private static final Logger logger = LoggerFactory.getLogger(ReportServiceImpl.class);

    private static final BaseMapper<Task, TaskDTO> mapper = new BaseMapper<>(Task.class, TaskDTO.class);


    @Override
    public List<ReportDTO> findAll() {
        return null;
    }

    @Override
    public List<ReportDTO> search(Map<String, Object> mapParam) {
        return null;
    }

    @Override
    public Page<ReportDTO> findBySearchCriteria(Optional<String> search, Pageable page) {
        Page<ReportDTO> listDTO = null;

        return listDTO;

    }

   
}
