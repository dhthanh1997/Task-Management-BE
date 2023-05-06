package com.ansv.taskmanagement.model;


import lombok.*;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Immutable
public class ProjectAndTaskReportView implements Serializable {

    @Id
    private Long id;

    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "task_id")
    private Long taskId;

    @Column(name = "username")
    private String username;

    @Column(name = "state")
    private Byte state;

    @Column(name = "team_id")
    private Long teamId;


}

