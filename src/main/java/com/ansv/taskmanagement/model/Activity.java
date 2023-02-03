package com.ansv.taskmanagement.model;


import com.ansv.taskmanagement.util.formatdate.LocalDateTimeDeserializer;
import com.ansv.taskmanagement.util.formatdate.LocalDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "activity")
public class Activity extends Auditable<String> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

//    @Length(max = 500)
    @Column(name = "name", columnDefinition = "nvarchar(500)")
    private String name;

    @Column(name = "task_id")
    private Long taskId;

    @Column(name = "total_cost", precision = 18, scale = 2)
    private BigDecimal totalCost;

    @Column(name = "revenue", precision = 18, scale = 2)
    private BigDecimal revenue;

    @Column(name = "total_hour", precision = 2, scale = 2)
    private Float totalHour;

    @Column(name = "start_date")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime startDate;

    @Column(name = "end_date")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime endDate;

    @Column(name = "real_start_date")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime readStartDate;

    @Column(name = "real_end_date")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime readEndDate;

//    @Length(max = 500)
    @Column(name = "description", columnDefinition = "nvarchar(500)")
    private String description;

//    @Length(max = 2000)
    @Column(name = "attach_file", columnDefinition = "varchar(2000)")
    private String attachFile;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "state")
    private Byte state;


}
