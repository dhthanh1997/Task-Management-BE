package com.ansv.taskmanagement.service;

import com.ansv.taskmanagement.dto.criteria.SearchCriteria;
import com.ansv.taskmanagement.dto.criteria.SearchOperation;
import com.ansv.taskmanagement.dto.response.ProjectDTO;
import com.ansv.taskmanagement.dto.specification.GenericSpecificationBuilder;
import com.ansv.taskmanagement.mapper.BaseMapper;
import com.ansv.taskmanagement.model.Project;
import com.ansv.taskmanagement.repository.ProjectRepository;
import com.ansv.taskmanagement.repository.ProjectRepositoryTest;
import com.ansv.taskmanagement.service.impl.ProjectServiceImpl;
import com.ansv.taskmanagement.util.DataUtils;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;


import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssumptions.given;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {

    private Logger logger = LoggerFactory.getLogger(ProjectServiceTest.class);

//    @Mock
//    private BaseMapper<Project, ProjectDTO> mapper = new BaseMapper<>(Project.class, ProjectDTO.class);

    @Mock
    private DataSource dataSource;

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectServiceImpl projectServiceImpl;

    private Project project;

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


    }

    @DisplayName("JUnit for findById")
    @Nested
    class WhenFindById {

        @Test
        void findById() {
            given(projectRepository.findById(1L));
            logger.info("test - project find by id done");

        }
    }

    @Nested
    class WhenSaving {
        @Mock
        private ProjectDTO item;

        @BeforeEach
        void setup() {
            this.item = ProjectDTO.builder().id(null)
                    .name("project 1")
                    .startDate(LocalDateTime.now())
                    .endDate(LocalDateTime.now().plusDays(6))
                    .revenue(BigDecimal.valueOf(11111.000))
                    .build();
            ;

        }

        @Test
        void save() {
            given(projectRepository.findById(project.getId()));
            projectRepository.save(project);
//            assertThrows(ResourceNotFoundException.class, () -> {
//
//            });
//            ProjectDTO dto = projectServiceImpl.save(this.item);
//            assertThat(dto).isNotNull();
            logger.info("test - project done");
        }
    }

    @DisplayName("Junit for delete by id")
    @Nested
    class WhenDeleteById {

        @Test
        void deleteById() {
            given(projectRepository.findById(project.getId()));
            projectRepository.deleteById(project.getId());
            logger.info("test - project service delete by id");
        }


    }

    @DisplayName("JUnit for search")
    @Nested
    class WhenSearchByCriteria {

        @InjectMocks
        private GenericSpecificationBuilder<Project> builder = new GenericSpecificationBuilder<>();

        private Optional<String> search;

        @Mock
        private Pageable pageable;


        @BeforeEach
        void setUp() {
            String field = "name";
            String operation = "cn";
            String value = "p";
            String sort = "name_asc";
            List<String> sorts = new ArrayList<>();
            sorts.add(sort);
            this.search = Optional.of(field + "." + operation + "." + value + ",");

            this.pageable = PageRequest.of(0, 10, DataUtils.sort(sorts));

            logger.info("before test - project service");
            logger.info(this.search.toString());
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
            Page<Project> listDTO = projectRepository.findAll(spec, this.pageable);
//            assertThat(listDTO).isNotNull();
            logger.info("test - project done");
        }


    }

}
