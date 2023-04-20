package com.ansv.taskmanagement.model;


import com.ansv.taskmanagement.util.formatdate.LocalDateTimeDeserializer;
import com.ansv.taskmanagement.util.formatdate.LocalDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Immutable
public class ReportView implements Serializable {


    @Id
    private Long projectId;

    private String username;

    private Long teamId;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime fromDate;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime toDate;

//    private Long numberOfTaskCompleted;
//
//    private Long numberOfTaskNotCompleted;
//
//
//    private Long numberOfTask;




}
