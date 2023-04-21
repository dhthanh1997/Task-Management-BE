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
//@IdClass(ReportViewId.class)
public class ReportView implements Serializable {

    @Id
    private Long id;

//    @Id
    @Column(name = "project_id")
    private Long projectId;

//    @Id
    @Column(name = "task_id")
    private Long taskId;

    @Column(name = "username")
    private String username;

    @Column(name = "team_id")
    private Long teamId;


}

//// create class for using multiple id in entity
//class ReportViewId implements Serializable{
//    Long projectId;
//    Long taskId;
//}
