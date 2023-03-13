package com.ansv.taskmanagement.service;

import com.ansv.taskmanagement.dto.response.TagDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {

    @InjectMocks
    private TagService underTest;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Nested
    class WhenFindingById {
        @BeforeEach
        void setup() {
        }
    }

    @Nested
    class WhenSaving {
        @Mock
        private TagDTO item;

        @BeforeEach
        void setup() {
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

        @BeforeEach
        void setup() {
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

    @Nested
    class WhenDeletingById {
        @BeforeEach
        void setup() {
        }
    }

    @Nested
    class WhenDeletingByListId {
        @BeforeEach
        void setup() {
        }
    }
}