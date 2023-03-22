package com.ansv.taskmanagement.service;

import com.ansv.taskmanagement.constants.StateEnum;
import com.ansv.taskmanagement.dto.criteria.SearchCriteria;
import com.ansv.taskmanagement.dto.response.TaskDTO;
import com.ansv.taskmanagement.dto.specification.GenericSpecificationBuilder;
import com.ansv.taskmanagement.mapper.BaseMapper;
import com.ansv.taskmanagement.model.Task;
import com.ansv.taskmanagement.repository.TaskRepository;
import com.ansv.taskmanagement.service.impl.TaskServiceImpl;
import com.ansv.taskmanagement.util.DataUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private Task task;

    @Mock
    private TaskDTO taskDTO;

    @Mock
    private List<Task> tasks;

    @Mock
    private List<TaskDTO> taskDTOList;

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    private Logger logger = LoggerFactory.getLogger(ProjectServiceTest.class);

    @Mock
    private BaseMapper<Task, TaskDTO> mapper = new BaseMapper<>(Task.class, TaskDTO.class);

    private void dataSourceTag() {
        for (int i = 1; i <= 10; i++) {
            Task entity = Task.builder()
                    .id(DataUtils.parseToLong(i))
                    .name("task test " + i)
                    .description("task test")
                    .startDate(LocalDateTime.now())
                    .endDate(LocalDateTime.now().plusDays(5))
                    .revenue(BigDecimal.valueOf(11111111.000 + i * 10))
                    .state((byte) Arrays.asList(StateEnum.values()).indexOf(StateEnum.NOT_DONE))
                    .parentId(1L)
                    .solution("test")
                    .build();
            this.tasks.add(entity);
        }

    }

    private void dataSourceTagDTO() {
        for (int i = 1; i <= 10; i++) {
            TaskDTO entity = TaskDTO.builder()
                    .id(DataUtils.parseToLong(i))
                    .name("task test " + i)
                    .description("task test")
                    .startDate(LocalDateTime.now())
                    .endDate(LocalDateTime.now().plusDays(5))
                    .revenue(BigDecimal.valueOf(11111111.000 + i * 10))
                    .state((byte) Arrays.asList(StateEnum.values()).indexOf(StateEnum.NOT_DONE))
                    .parentId(1L)
                    .solution("test")
                    .build();
            this.taskDTOList.add(entity);
        }

    }

    @BeforeEach
    void setUp() {
        this.task = Task.builder()
                .id(DataUtils.parseToLong(1L))
                .name("task test ")
                .description("task test")
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(5))
                .revenue(BigDecimal.valueOf(11111111.000))
                .state((byte) Arrays.asList(StateEnum.values()).indexOf(StateEnum.NOT_DONE))
                .solution("test")
                .build();

        this.dataSourceTag();
        this.dataSourceTagDTO();
    }

    @AfterEach
    void tearDown() {
    }

    @DisplayName("Junit for find by id")
    @Nested
    class whenFindById {

        @BeforeEach
        void setUp() {

        }

        @Test
        void findById() {
            when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));

            Task item = taskRepository.findById(task.getId()).get();

            assertThat(item).isNotNull();

            verify(taskRepository, times(1)).findById(task.getId());

        }

    }


    @DisplayName("Junit for saving")
    @Nested
    class WhenSaving {

        @Mock
        private TaskDTO item;

        private Task entity;

        private List<TaskDTO> subTask;

        @BeforeEach()
        void setUp() {
            this.item = TaskDTO.builder()
                    .id(DataUtils.parseToLong(1L))
                    .name("task test ")
                    .description("task test")
                    .startDate(LocalDateTime.now())
                    .endDate(LocalDateTime.now().plusDays(5))
                    .revenue(BigDecimal.valueOf(11111111.000))
                    .state((byte) Arrays.asList(StateEnum.values()).indexOf(StateEnum.NOT_DONE))
                    .solution("test")
                    .build();
        }

        @Test
        void save() {

            entity = mapper.toPersistenceBean(item);
            when(taskRepository.save(entity)).thenReturn(entity);

            Task task = taskRepository.save(entity);

            assertEquals(task, entity);
            verify(taskRepository, times(1)).save(entity);

        }

    }


    @DisplayName("Junit for mark complete task")
    @Nested
    class WhenMarkCompleteTask {

        @Mock
        private TaskDTO item;

        @Mock
        private Task entity;

        @Mock
        private List<Task> entities;

        @Mock
        private List<TaskDTO> items;

        private void buildDataSoure() {
            for (int i = 1; i <= 10; i++) {
                TaskDTO entity = TaskDTO.builder()
                        .id(DataUtils.parseToLong(i))
                        .name("task test " + i)
                        .description("task test")
                        .startDate(LocalDateTime.now())
                        .endDate(LocalDateTime.now().plusDays(5))
                        .revenue(BigDecimal.valueOf(11111111.000 + i * 10))
                        .state((byte) Arrays.asList(StateEnum.values()).indexOf(StateEnum.NOT_DONE))
                        .parentId(1L)
                        .solution("test")
                        .build();
                this.items.add(entity);
            }
        }

        @BeforeEach
        void setUp() {
            this.item = TaskDTO.builder()
                    .id(DataUtils.parseToLong(1L))
                    .name("task test ")
                    .description("task test")
                    .startDate(LocalDateTime.now())
                    .endDate(LocalDateTime.now().plusDays(5))
                    .revenue(BigDecimal.valueOf(11111111.000))
                    .state((byte) Arrays.asList(StateEnum.values()).indexOf(StateEnum.NOT_DONE))
                    .solution("test")
                    .build();


            this.buildDataSoure();
        }

        @Test
        void markCompleteTask() {

            switch (item.getState()) {
                // Hoàn thành -> chưa hoàn thành
                case 1:
                    item.setState((byte)Arrays.asList(StateEnum.values()).indexOf(StateEnum.NOT_DONE));
                    if(DataUtils.isNullOrEmpty(taskDTOList) && taskDTOList.size() > 0) {
                        for(TaskDTO sub: taskDTOList) {
                            sub.setState((byte)Arrays.asList(StateEnum.values()).indexOf(StateEnum.NOT_DONE));
                            Task task = mapper.toPersistenceBean(sub);
                            entities.add(task);
                        }

                        entity = mapper.toPersistenceBean(item);

                        when(taskRepository.save(entity)).thenReturn(entity);
                        when(taskRepository.saveAll(entities)).thenReturn(entities);

                        assertThat(entities).isNotNull();
                        assertThat(entity.getState()).isNotEqualTo(1);

                        verify(taskRepository, times(1)).save(entity);
                        verify(taskRepository, times(1)).saveAll(entities);
                    }
                    break;
                // Chưa hoàn thành -> hoàn thành
                case 0:
                default:
                    item.setState((byte)Arrays.asList(StateEnum.values()).indexOf(StateEnum.DONE));
                    if(DataUtils.isNullOrEmpty(taskDTOList) && taskDTOList.size() > 0) {
                        for(TaskDTO sub: taskDTOList) {
                            sub.setState((byte)Arrays.asList(StateEnum.values()).indexOf(StateEnum.NOT_DONE));
                            Task task = mapper.toPersistenceBean(sub);
                            entities.add(task);
                        }

                        entity = mapper.toPersistenceBean(item);

                        when(taskRepository.save(entity)).thenReturn(entity);
                        when(taskRepository.saveAll(entities)).thenReturn(entities);

                        assertThat(entities).isNotNull();
                        assertThat(entity.getState()).isNotEqualTo(0);

                        verify(taskRepository, times(1)).save(entity);
                        verify(taskRepository, times(1)).saveAll(entities);
                    }
                    break;
            }




        }

    }



    @Test
    void saveListTask() {
    }

    @Test
    void findAll() {
    }

    @Test
    void search() {
    }

    @Test
    void findByParentId() {
    }

    @DisplayName("Junit for search with criteria")
    @Nested
    class WhenSearching {

        @Mock
        private Map<String, Object> mapParam;

        @InjectMocks
        private GenericSpecificationBuilder<Task> builder = new GenericSpecificationBuilder<>();

        private Pageable pageable;

        private Page<TaskDTO> tagPageDTO;

        private Page<Task> taskPage;

        private Optional<String> search;

        @BeforeEach
        void setUp() {
            String field = "name";
            String operation = "cn";
            String value = "p";
            String sort = "name_asc";
            List<String> sorts = new ArrayList<>();
            sorts.add(sort);
            this.search = Optional.of(field + "." + operation + "." + value + ",");

            //init context
            this.pageable = PageRequest.of(0, 10, DataUtils.sort(sorts));
            int lowerBound = pageable.getPageNumber() * pageable.getPageSize();
            int upperBound = Math.min(lowerBound + pageable.getPageSize() - 1, tasks.size());
            List<Task> subList = tasks.subList(lowerBound, upperBound);
            this.taskPage = new PageImpl<>(subList, pageable, subList.size());
        }

        @Test
        void findBySearchCriteria() {
            builder.setClazz(Task.class);
            if (search.isPresent()) {
                Pattern pattern = Pattern.compile("(\\w+?)(\\.)(:|<|>|(\\w+?))(\\.)(\\w+?),", Pattern.UNICODE_CHARACTER_CLASS);
                Matcher matcher = pattern.matcher(search.get());
                while (matcher.find()) {
                    builder.with(new SearchCriteria(matcher.group(1), matcher.group(3), matcher.group(6)));
                }
            }

            Specification<Task> spec = builder.build();

            when(taskRepository.findAll(spec, this.pageable)).thenReturn(taskPage);

            Page<Task> listTag = taskRepository.findAll(spec, this.pageable);

            assertThat(taskPage).isEqualTo(listTag);

            logger.info("size of list tag: " + Integer.valueOf(listTag.getSize()));
        }
    }



    @Test
    void deleteById() {
    }

    @Test
    void deleteByListId() {
    }

    @Test
    void importTask() {
    }
}