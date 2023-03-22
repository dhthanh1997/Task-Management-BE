package com.ansv.taskmanagement.service;

import com.ansv.taskmanagement.dto.criteria.SearchCriteria;
import com.ansv.taskmanagement.dto.response.ProjectDTO;
import com.ansv.taskmanagement.dto.specification.GenericSpecificationBuilder;
import com.ansv.taskmanagement.mapper.BaseMapper;
import com.ansv.taskmanagement.model.Project;
import com.ansv.taskmanagement.repository.ProjectRepository;
import com.ansv.taskmanagement.service.impl.ProjectServiceImpl;
import com.ansv.taskmanagement.util.DataUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {

    private Logger logger = LoggerFactory.getLogger(ProjectServiceTest.class);

    @Mock
    private BaseMapper<Project, ProjectDTO> mapper = new BaseMapper<>(Project.class, ProjectDTO.class);

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectServiceImpl projectServiceImpl;

    @Mock
    private Project project;

    @Mock
    private List<Project> projects;

    private void dataSource() {
        for (int i = 1; i <= 10; i++) {
            Project entity = Project.builder()
                    .id(DataUtils.parseToLong(i))
                    .name("project " + i)
                    .startDate(LocalDateTime.now())
                    .endDate(LocalDateTime.now().plusDays(1 + i))
                    .revenue(BigDecimal.valueOf(11111.000 + i * 10))
                    .build();

            this.projects.add(entity);
        }

    }

    @BeforeEach
    void setUp() {
        this.projectServiceImpl = new ProjectServiceImpl();
        this.project = Project.builder()
                .id(1L)
                .name("project 1")
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(6))
                .revenue(BigDecimal.valueOf(11111.000))
                .build();

        this.dataSource();
    }


    @DisplayName("JUnit for findById")
    @Nested
    public class WhenFindById {

        private Project item;

        @Test
        public void findById() {
            given(projectRepository.findById(1L)).willReturn(Optional.of(project));

            item = projectRepository.findById(1L).get();

            assertThat(item).isEqualTo(project);


            logger.info("test - project find by id done");
            logger.info("item=" + item.getId() + " - " + "project=" + project.getId());

        }
    }

    @DisplayName("JUnit for saving")
    @Nested
    public class WhenSaving {
        @Mock
        private Project item;

        @BeforeEach
        public void setup() {

        }

        @Test
        public void save() {
            when(projectRepository.save(project)).thenReturn(project);
            Project item = projectRepository.save(project);
            assertThat(item).isNotNull();
            logger.info("test - project done");
            logger.info(Long.toString(item.getId()));
        }
    }

    @DisplayName("Junit for delete by id")
    @Nested
    public class WhenDeleteById {

        @Test
        public void deleteById() {
            willDoNothing().given(projectRepository).deleteById(project.getId());

            projectRepository.deleteById(project.getId());

            verify(projectRepository, times(1)).deleteById(project.getId());

            logger.info("test - project service delete by id");
        }


    }

    @DisplayName("Junit for delete by list id")
    @Nested
    public class WhenDeleteByListId {

        private List<Long> listId;

        private List<Project> projectList;

        @BeforeEach
        public void setup() {
            listId = Arrays.asList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L);
            // 1. create mock data
            projectList = projects;
        }

        @Test
        public void deleteByListId() {
//            given => for init context but init context in setup so that don't using it in here
//            given(projectRepository.findAll()).willReturn(projects);

            // 2. define behavior of repository for testing;
            when(projectRepository.deleteByListId(listId)).thenReturn(1);

//             3. call service method
            int deleteTest = projectRepository.deleteByListId(listId);
//            projectRepository.deleteByListId(listId);

            // 4. assert the result -> Xác nhận/khẳng định lại kết quả
            assertThat(deleteTest).isEqualTo(1);

            // 4.1. ensure repository is called -> verify the output
            verify(projectRepository).deleteByListId(listId);
        }
    }

    @DisplayName("JUnit for search")
    @Nested
    public class WhenSearchByCriteria {

        @InjectMocks
        private GenericSpecificationBuilder<Project> builder = new GenericSpecificationBuilder<>();

        private Optional<String> search;

        private Pageable pageable;

        private Page<Project> projectPage;

        @BeforeEach
        public void setUp() {
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
            int upperBound = Math.min(lowerBound + pageable.getPageSize() - 1, projects.size());
            List<Project> subList = projects.subList(lowerBound, upperBound);
            this.projectPage = new PageImpl<>(subList, pageable, subList.size());
//            logger.info("before test - project service");
//            logger.info(this.search.toString());
        }

        @Test
        public void searchBycriteria() throws Exception {

            // specification
            builder.setClazz(Project.class);
            if (search.isPresent()) {
                Pattern pattern = Pattern.compile("(\\w+?)(\\.)(:|<|>|(\\w+?))(\\.)(\\w+?),", Pattern.UNICODE_CHARACTER_CLASS);
                Matcher matcher = pattern.matcher(search.get());
                while (matcher.find()) {
                    builder.with(new SearchCriteria(matcher.group(1), matcher.group(3), matcher.group(6)));
                }
            }
            Specification<Project> spec = builder.build();

            when(projectRepository.findAll(spec, this.pageable)).thenReturn(projectPage);

            Page<Project> listDTO = projectRepository.findAll(spec, this.pageable);

//            assertThat(listDTO).isNotNull();
            assertEquals(listDTO, projectPage);
            logger.info("test - project done");
            logger.info(Integer.toString(listDTO.getSize()));
            logger.info(Integer.toString(projectPage.getSize()));
        }


    }

}
