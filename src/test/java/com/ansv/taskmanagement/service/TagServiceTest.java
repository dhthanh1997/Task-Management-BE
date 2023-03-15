package com.ansv.taskmanagement.service;

import com.ansv.taskmanagement.dto.criteria.SearchCriteria;
import com.ansv.taskmanagement.dto.response.TagDTO;
import com.ansv.taskmanagement.dto.specification.GenericSpecificationBuilder;
import com.ansv.taskmanagement.mapper.BaseMapper;
import com.ansv.taskmanagement.model.Project;
import com.ansv.taskmanagement.model.Tag;
import com.ansv.taskmanagement.repository.TagRepository;
import com.ansv.taskmanagement.service.impl.TagServiceImpl;
import com.ansv.taskmanagement.util.DataUtils;
import org.checkerframework.checker.nullness.Opt;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {

    private Logger logger = LoggerFactory.getLogger(ProjectServiceTest.class);

    @Mock
    private BaseMapper<Tag, TagDTO> mapper = new BaseMapper<>(Tag.class, TagDTO.class);

    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private TagServiceImpl tagService;

    @Mock
    private Tag tag;

    @Mock
    private List<Tag> tags;

    @Mock
    private List<TagDTO> tagDTOList;

    private void dataSourceTag() {
        for (int i = 1; i <= 10; i++) {
            Tag entity = Tag.builder()
                    .id(DataUtils.parseToLong(i))
                    .name("tag " + i)
                    .color("color" + i)
                    .icon("icon" + i)
                    .slug("slug" + i)
                    .build();
            this.tags.add(entity);
        }

    }

    private void dataSourceTagDTO() {
        for (int i = 1; i <= 10; i++) {
            TagDTO entity = TagDTO.builder()
                    .id(DataUtils.parseToLong(i))
                    .name("tag " + i)
                    .color("color" + i)
                    .icon("icon" + i)
                    .slug("slug" + i)
                    .build();
            this.tagDTOList.add(entity);
        }

    }

    @BeforeEach
    public void setUp() {
        this.tag = Tag.builder()
                .id(1L)
                .name("tag test")
                .color("color test")
                .icon("icon test")
                .slug("slug test")
                .build();

        this.dataSourceTag();
        this.dataSourceTagDTO();

    }

    @AfterEach
    public void tearDown() {
    }

    @DisplayName("Junit for find by id - tag")
    @Nested
    class WhenFindingById {

        private Tag item;

        @BeforeEach
        void setup() {


        }

        @Test
        public void whenFindingById() {
            given(tagRepository.findById(1L)).willReturn(Optional.of(tag));
            item = tagRepository.findById(1L).get();
            assertThat(item).isEqualTo(tag);
            logger.info("test - tag find by id done");
            logger.info("item=" + item.getId() + " - " + "project=" + tag.getId());
        }
    }

    @Nested
    class WhenSaving {

        @Mock
        private TagDTO item;

        private Tag entity;

        private Tag entity2;

        @BeforeEach
        void setup() {
            this.item = TagDTO.builder()
                    .id(1L)
                    .color("test")
                    .name("test tag")
                    .icon("icon test")
                    .slug("slug test")
                    .build();
        }

        @Test
        public void whenSaving() {

            entity = mapper.toPersistenceBean(item);
            when(tagRepository.save(entity)).thenReturn(entity);
            entity2 = tagRepository.save(entity);
            assertThat(entity).isEqualTo(entity2);
            logger.info(item.getId().toString());
        }
    }

    @Nested
    class WhenFindingAll {
        @BeforeEach
        void setup() {
        }
    }

    @Nested
    class WhenSearching {

        @Mock
        private Map<String, Object> mapParam;

        @InjectMocks
        private GenericSpecificationBuilder<Tag> builder = new GenericSpecificationBuilder<>();

        private Pageable pageable;

        private Page<TagDTO> tagPageDTO;

        private Page<Tag> tagPage;

        private Optional<String> search;


        @BeforeEach
        void setup() {
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
            int upperBound = Math.min(lowerBound + pageable.getPageSize() - 1, tags.size());
            List<Tag> subList = tags.subList(lowerBound, upperBound);
            this.tagPage = new PageImpl<>(subList, pageable, subList.size());
        }

        @Test
        public void whenSearching() {

            builder.setClazz(Tag.class);
            if (search.isPresent()) {
                Pattern pattern = Pattern.compile("(\\w+?)(\\.)(:|<|>|(\\w+?))(\\.)(\\w+?),", Pattern.UNICODE_CHARACTER_CLASS);
                Matcher matcher = pattern.matcher(search.get());
                while (matcher.find()) {
                    builder.with(new SearchCriteria(matcher.group(1), matcher.group(3), matcher.group(6)));
                }
            }

            Specification<Tag> spec = builder.build();
//
//            when(tagRepository.findAll(spec, this.pageable).map(entity -> {
//                TagDTO dto = mapper.toDtoBean(entity);
//                return dto;
//            })).thenReturn(tagPageDTO);

            when(tagRepository.findAll(spec, this.pageable)).thenReturn(tagPage);

//            Page<TagDTO> listTag = tagRepository.findAll(spec, this.pageable).map(entity -> {
//                TagDTO dto = mapper.toDtoBean(entity);
//                return dto;
//            });
            Page<Tag> listTag = tagRepository.findAll(spec, this.pageable);

            assertThat(tagPage).isEqualTo(listTag);

            logger.info("size of list tag: " + Integer.valueOf(listTag.getSize()));

        }
    }

    @Nested
    class WhenFindingBySearchCriteria {
        @Mock
        private Optional<String> search;
        @Mock
        private Pageable page;

        @BeforeEach
        void setup() {
        }
    }

    @DisplayName("Junit for delete by id")
    @Nested
    class WhenDeletingById {
        @BeforeEach
        void setup() {
        }

        @Test
        public void deleteById() {
            willDoNothing().given(tagRepository).deleteById(tag.getId());

            tagRepository.deleteById(tag.getId());

            verify(tagRepository, times(1)).deleteById(tag.getId());

        }
    }

    @DisplayName("Junit for delete by list id")
    @Nested
    class WhenDeletingByListId {

        private List<Long> listId;

        @BeforeEach
        void setup() {
            listId = Arrays.asList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L);

        }

        @Test
        public void deleteByListId() {
            when(tagRepository.deleteByListId(listId)).thenReturn(1);

            int deleteTest = tagRepository.deleteByListId(listId);

            assertThat(deleteTest).isGreaterThan(0);
        }
    }
}