package com.ansv.taskmanagement.model;


import com.ansv.taskmanagement.util.formatdate.LocalDateTimeDeserializer;
import com.ansv.taskmanagement.util.formatdate.LocalDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "work_log")
public class WorkLog extends Auditable<String> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", columnDefinition = "nvarchar(500)")
    private String name;

    @Column(name = "task_id")
    private Long taskId;

    @Column(name = "activity_id")
    private Long activityId;

    @Column(name = "content", columnDefinition = "nvarchar(1000)")
    private String content;

    @Column(name = "end_date")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime endDate;



}
