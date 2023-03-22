package com.ansv.taskmanagement;

//import com.redis.om.spring.annotations.EnableRedisDocumentRepositories;
import com.ansv.taskmanagement.repository.RedisTokenRepository;
import com.redis.om.spring.annotations.EnableRedisDocumentRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@SpringBootApplication
@EnableEurekaClient
@EnableRedisDocumentRepositories(basePackageClasses = {RedisTokenRepository.class})
public class TaskManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskManagementApplication.class, args);
    }


}
