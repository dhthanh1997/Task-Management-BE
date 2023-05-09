package com.ansv.taskmanagement.service.impl;

import com.ansv.taskmanagement.constants.StateEnum;
import com.ansv.taskmanagement.dto.criteria.SearchCriteria;
import com.ansv.taskmanagement.dto.excel.XlsxProjectTaskReport;
import com.ansv.taskmanagement.dto.excel.XlsxSection;
import com.ansv.taskmanagement.dto.excel.XlsxTask;
import com.ansv.taskmanagement.dto.request.TaskImportDTO;
import com.ansv.taskmanagement.dto.response.ProjectDTO;
import com.ansv.taskmanagement.dto.response.SectionDTO;
import com.ansv.taskmanagement.dto.response.TaskDTO;
import com.ansv.taskmanagement.dto.response.report.ProjectAndTaskReportDTO;
import com.ansv.taskmanagement.dto.specification.GenericSpecificationBuilder;
import com.ansv.taskmanagement.mapper.BaseMapper;
import com.ansv.taskmanagement.model.Task;
import com.ansv.taskmanagement.repository.TaskRepository;
import com.ansv.taskmanagement.service.ProjectService;
import com.ansv.taskmanagement.service.SectionService;
import com.ansv.taskmanagement.service.TaskService;
import com.ansv.taskmanagement.service.XlsxWriterService;
import com.ansv.taskmanagement.util.DataUtils;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.impl.MapperFacadeImpl;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.io.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.ansv.taskmanagement.util.DataUtils.readInputStreamResource;

@Service
@Slf4j
public class TaskServiceImpl implements TaskService {

    private static final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

    private static final BaseMapper<Task, TaskDTO> mapper = new BaseMapper<>(Task.class, TaskDTO.class);

    private static final BaseMapper<Task, XlsxTask> mapperXlsx = new BaseMapper<>(Task.class, XlsxTask.class);


    @Autowired
    private TaskRepository repository;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private SectionService sectionService;

    @Autowired
    private XlsxWriterService xlsxWriterService;


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
        Task entity = null;

        TaskDTO dto = findById(item.getId());
        if (DataUtils.notNull(dto)) {
            item.setLastModifiedDate(LocalDateTime.now());
        }
        entity = mapper.toPersistenceBean(item);
        return mapper.toDtoBean(repository.save(entity));
    }

    @Override
    public TaskDTO markCompleteTask(Long id) {
        TaskDTO dto = findById(id);
        List<TaskDTO> subTaskDTO = findByParentId(id);

        switch (dto.getState()) {
            // Hoàn thành -> chưa hoàn thành
            case 1:
                dto.setState((byte) Arrays.asList(StateEnum.values()).indexOf(StateEnum.NOT_DONE));
                if (DataUtils.isNullOrEmpty(subTaskDTO) && subTaskDTO.size() > 0) {
                    for (TaskDTO sub : subTaskDTO) {
                        sub.setState((byte) Arrays.asList(StateEnum.values()).indexOf(StateEnum.NOT_DONE));
                    }
                    saveListTask(subTaskDTO);
                }
                break;
            // Chưa hoàn thành -> hoàn thành
            case 0:
            default:
                dto.setState((byte) Arrays.asList(StateEnum.values()).indexOf(StateEnum.DONE));
                if (DataUtils.isNullOrEmpty(subTaskDTO) && subTaskDTO.size() > 0) {
                    for (TaskDTO sub : subTaskDTO) {
                        sub.setState((byte) Arrays.asList(StateEnum.values()).indexOf(StateEnum.DONE));
                    }
                    saveListTask(subTaskDTO);
                }
                break;
        }
        dto = save(dto);
        return dto;
    }

    @Override
    public List<TaskDTO> saveListTask(List<TaskDTO> listData) {
        List<TaskDTO> listDTO = new ArrayList<>();
        for (TaskDTO task : listData) {
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

        GenericSpecificationBuilder<Task> builder = new GenericSpecificationBuilder<>();

        // check chuỗi để tách các param search
        if (search.isPresent()) {
//            (\w+?)(.)(:|<|>|\w+?)(.)(\w+?)(,) = a.b.c,
//            (\w+?)(.)(:|<|>|\w+?)(,) = a.b,
            Pattern pattern = Pattern.compile("(\\w+?)(.)(:|<|>|\\w+?)(.)(\\w+?)(,)", Pattern.UNICODE_CHARACTER_CLASS);
            Matcher matcher = pattern.matcher(search.get());
            while (matcher.find()) {
                builder.with(new SearchCriteria(matcher.group(1), matcher.group(3), matcher.group(5)));
            }
        }
        // specification
        builder.setClazz(Task.class);
        Specification<Task> spec = builder.build();
        Page<TaskDTO> listDTO = repository.findAll(spec, page).map(entity -> {
            TaskDTO dto = mapper.toDtoBean(entity);
            Long count = DataUtils.parseToLong(findByParentId(entity.getId()).size());
            dto.setNumberOfSubTask(count);
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
    public void importTask(List<TaskImportDTO> dtos) {

    }

    @Override
    public File exportReport(Long projectId) {
        String folder = "C:\\BIDV_BBBG\\";
        File outputFile = new File(folder + projectId + ".xlsx");
        List<XlsxProjectTaskReport> report = new ArrayList<>();
        ProjectDTO project = projectService.findById(projectId);
        List<SectionDTO> sectionsDTO = sectionService.findByProjectId(projectId);


//        List<XlsxSection> xlsxSections = new ArrayList<>();
        if (!DataUtils.isNullOrEmpty(project)) {

            for (SectionDTO sectionDTO : sectionsDTO) {
                XlsxProjectTaskReport section = new XlsxProjectTaskReport();
                List<Task> tasks = new ArrayList<>();
                section.setNo(sectionDTO.getId());
                section.setSectionName(sectionDTO.getName());
                tasks.addAll(repository.findBySectionIdAndProjectId(sectionDTO.getId(), projectId));
                section.setTasks(mapperXlsx.toDtoBean(tasks));
                report.add(section);
            }

        }

        List<String> columns = DataUtils.getFieldNameOfObject(new XlsxTask());

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        String title = "BÁO CÁO CÔNG VIỆC";

        try (Workbook workbook= new XSSFWorkbook()) {
            xlsxWriterService.writeFile(report, byteArrayOutputStream, columns, workbook, title);
            FileOutputStream outpuStream = new FileOutputStream(outputFile);
            byteArrayOutputStream.writeTo(outpuStream);
            outpuStream.close();
            byteArrayOutputStream.close();
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return outputFile;
    }

    private File exportFileReport(ProjectAndTaskReportDTO item, String path, String tempFile, String type, String pathFolder) throws IOException {
        InputStream resource = readInputStreamResource(path);
        File file = File.createTempFile(tempFile, "." + type);
        FileUtils.copyInputStreamToFile(resource, file);
        String filename = tempFile + "_" + Timestamp.valueOf(LocalDateTime.now()).getTime() + "." + type;
        File outputFile = new File(pathFolder + filename);
        if (DataUtils.isNullOrEmpty(file)) {
            throw new IllegalArgumentException("file not found");
        } else {
            try {

            } catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
                return null;
            }


        }
        return outputFile;
    }
}
