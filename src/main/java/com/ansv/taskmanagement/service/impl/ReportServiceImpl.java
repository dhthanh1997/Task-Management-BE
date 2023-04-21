package com.ansv.taskmanagement.service.impl;

import com.ansv.taskmanagement.constants.StateEnum;
import com.ansv.taskmanagement.dto.criteria.SearchCriteria;
import com.ansv.taskmanagement.dto.request.TaskImportDTO;
import com.ansv.taskmanagement.dto.response.MemberDTO;
import com.ansv.taskmanagement.dto.response.ReportDTO;
import com.ansv.taskmanagement.dto.response.TaskDTO;
import com.ansv.taskmanagement.dto.specification.GenericSpecificationBuilder;
import com.ansv.taskmanagement.mapper.BaseMapper;
import com.ansv.taskmanagement.model.Member;
import com.ansv.taskmanagement.model.ReportView;
import com.ansv.taskmanagement.model.Task;
import com.ansv.taskmanagement.repository.TaskRepository;
import com.ansv.taskmanagement.repository.impl.ReportViewRepositoryImpl;
import com.ansv.taskmanagement.repository.view.ReportViewRepository;
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

    private static final BaseMapper<ReportView, ReportDTO> mapper = new BaseMapper<>(ReportView.class, ReportDTO.class);

    @Autowired
    private ReportViewRepositoryImpl reportViewRepository;

    @Override
    public List<ReportDTO> findAll() {
        return null;
    }

    @Override
    public List<ReportDTO> search(Map<String, Object> mapParam) {
        return null;
    }

    @Override
    public List<ReportDTO> findBySearchCriteria(Optional<String> search, Pageable page) {
        Map<String, Object> params = new HashMap<>();
        GenericSpecificationBuilder<ReportView> builder = new GenericSpecificationBuilder<>();
        // check chuỗi để tách các param search
        if (DataUtils.notNull(search)) {
            Pattern pattern = Pattern.compile("(\\w+?)(\\.)(:|<|>|(\\w+?))(\\.)(\\w+?),", Pattern.UNICODE_CHARACTER_CLASS);
            Matcher matcher = pattern.matcher(search + ",");
            while (matcher.find()) {
                builder.with(new SearchCriteria(matcher.group(1), matcher.group(3), matcher.group(6)));
                params.put(matcher.group(1), matcher.group(6));
            }
        }
        // specification
//        builder.setClazz(ReportView.class);
//        Specification<ReportView> spec = builder.build();

        List<ReportDTO> listDTO = mapper.toDtoBean(reportViewRepository.findDataWithParams(params));
        return listDTO;

    }

   
}
