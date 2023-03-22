package com.ansv.taskmanagement;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest()
@TestPropertySource(locations = "classpath:application-dev-test.properties")
class TaskManagementApplicationTests {


    @Test
    void contextLoads() {

    }

}
