package com.ansv.taskmanagement.dto.redis;

import com.redis.om.spring.annotations.Document;
import com.redis.om.spring.annotations.Indexed;
import com.redis.om.spring.annotations.Searchable;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Data
@Document
@Builder
@NoArgsConstructor
//@RedisHash("accessToken")
public class AccessToken {

    @Id
    private String id;

//    @Indexed
    private String accessToken;

    @Searchable
    @NonNull
    private String username;

    @Searchable
    private String department;

    @Searchable
    private String position;

//    @Indexed
//    private Set<String> tags = new HashSet<String>();

    @NonNull
    @Searchable
    private String uuid;

    @TimeToLive
    @NonNull
    private Date expiredTime;

    @Searchable
    @NonNull
    private String serviceName;

}
