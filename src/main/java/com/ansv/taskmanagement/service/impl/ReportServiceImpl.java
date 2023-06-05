package com.ansv.taskmanagement.service.impl;

import com.ansv.taskmanagement.constants.StateEnum;
import com.ansv.taskmanagement.dto.criteria.SearchCriteria;
import com.ansv.taskmanagement.dto.request.TaskImportDTO;
import com.ansv.taskmanagement.dto.response.MemberDTO;
import com.ansv.taskmanagement.dto.response.ProjectDTO;
import com.ansv.taskmanagement.dto.response.ReportDTO;
import com.ansv.taskmanagement.dto.response.TaskDTO;
import com.ansv.taskmanagement.dto.response.report.ProjectAndTaskReportDTO;
import com.ansv.taskmanagement.dto.response.report.SectionAndTaskDTO;
import com.ansv.taskmanagement.dto.specification.GenericSpecificationBuilder;
import com.ansv.taskmanagement.mapper.BaseMapper;
import com.ansv.taskmanagement.model.*;
import com.ansv.taskmanagement.repository.ProjectRepository;
import com.ansv.taskmanagement.repository.SectionRepository;
import com.ansv.taskmanagement.repository.TaskRepository;
import com.ansv.taskmanagement.repository.impl.ReportViewRepositoryImpl;
import com.ansv.taskmanagement.repository.view.ReportViewRepository;
import com.ansv.taskmanagement.service.ProjectService;
import com.ansv.taskmanagement.service.ReportService;
import com.ansv.taskmanagement.service.SectionService;
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

    private static final BaseMapper<Task, SectionAndTaskDTO> mapperTask = new BaseMapper<>(Task.class, SectionAndTaskDTO.class);


    @Autowired
    private ReportViewRepositoryImpl reportViewRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectService projectService;

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

    @Override
    public ProjectAndTaskReportDTO previewReport(Map<String, Object> parameters) {
        ProjectAndTaskReportDTO report = new ProjectAndTaskReportDTO();
        Map<String, String> customMapFields = new HashMap<>();
        customMapFields.put("id", "id");
        customMapFields.put("name", "sectionName");
        customMapFields.put("note", "note");

        BaseMapper<Section, SectionAndTaskDTO> mapperSec = new BaseMapper<>(Section.class, SectionAndTaskDTO.class, customMapFields);
        ProjectDTO project = new ProjectDTO();
        Long projectId = 0L;
        if(!DataUtils.isNullOrEmpty(parameters.get("projectId"))) {
            projectId = Long.parseLong(parameters.get("projectId").toString());
            project = projectService.findById(projectId);
        }
        List<Section> sections = new ArrayList<>();
        if (!DataUtils.isNullOrEmpty(project)) {
            report.setProjectDTO(project);
            sections = sectionRepository.findByProjectId(projectId);
            List<SectionAndTaskDTO> root = mapperSec.toDtoBean(sections);

            List<SectionAndTaskDTO> items = recursiveSectionAndTask(root, parameters);
            report.setSections(items);
        }
        return report;
    }

    public List<SectionAndTaskDTO> recursiveSectionAndTask(List<SectionAndTaskDTO> sectionAndTasks, Map<String, Object> parameters) {
        if (!DataUtils.isNullOrEmpty(sectionAndTasks) && sectionAndTasks.size() > 0) {
            for (SectionAndTaskDTO item : sectionAndTasks) {
                parameters.put("sectionId", item.getId().toString());

                List<Task> tasks = taskRepository.previewReport(parameters);
                if(!DataUtils.isNullOrEmpty(tasks) && tasks.size() > 0) {
                    item.setChildren(mapperTask.toDtoBean(tasks));
                }
                recursiveSectionAndTask(item.getChildren(), parameters);
            }
        }
        return sectionAndTasks;
    }


}
